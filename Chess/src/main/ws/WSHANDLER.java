package ws;

import model.*;
import chess.*;
import dataAccess.*;
import com.google.gson.*;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import webSocketMessages.serverMessages.ErrorMessage;
import webSocketMessages.serverMessages.LoadMessage;
import webSocketMessages.serverMessages.NotificationMessage;
import webSocketMessages.userCommands.*;
import webSockets.UserGameCommand;

import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.util.Collection;
import java.util.Objects;

@WebSocket
public class WSHANDLER {
    Database db = new Database();
    Auth_DAO authDAO;
    Game_DAO gameDAO;
    Connection connection;
    ChessGame gameSave;
    public final ws.ConnectionManager connectionManager = new ConnectionManager();

    public WSHANDLER(Connection connection){
        this.connection = connection;
    }

    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws Exception {
        // Get connection to database
        connection = db.getConnection();
        authDAO = new Auth_DAO(connection);
        gameDAO = new Game_DAO(connection);

        // Pull out the command
        UserGameCommand command = new Gson().fromJson(message, UserGameCommand.class);

        // Adapt for position and move classes
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(ChessPosition.class, new PosAdapter());
        gsonBuilder.registerTypeAdapter(ChessMove.class, new MoveAdapter());
        Gson gson = gsonBuilder.create();

        // Send command to the correct function
        switch (command.getCommandType()) {
            case JOIN_PLAYER -> join(session, new Gson().fromJson(message, JoinPlayer.class));
            case JOIN_OBSERVER -> observe(session, new Gson().fromJson(message, JoinObserver.class));
            case MAKE_MOVE -> move(session, gson.fromJson(message, MakeMove.class));
            case LEAVE -> leave(session, new Gson().fromJson(message, Leave.class));
            case RESIGN -> resign(session, new Gson().fromJson(message, Resign.class));
        }
    }

    public void join(Session session, JoinPlayer command) throws IOException {
        // Open databases and get game and auth of user
        Game_Record game = gameDAO.findGame(Integer.parseInt(command.getGameID()));
        Auth_Record tokenModel= authDAO.findAuth(command.getAuthString());

        // Set the username from the authKey
        String username = null;
        if (tokenModel != null)
            username = tokenModel.username();

        // Check authorization
        if (authDAO.findAuth(command.getAuthString()) == null){
            ErrorMessage errorMessage = new ErrorMessage("Error: Unauthorized");
            session.getRemote().sendString(new Gson().toJson(errorMessage));
        }
        // Check the game exists
        else if (game == null){
            ErrorMessage errorMessage = new ErrorMessage("Error: Game does not exist");
            session.getRemote().sendString(new Gson().toJson(errorMessage));
        }
        // Check the color is valid
        else if (command.getPlayerColor() == null){
            ErrorMessage errorMessage = new ErrorMessage("Error: No player color");
            session.getRemote().sendString(new Gson().toJson(errorMessage));
        }
        // Check to make sure it is white/black
        else if (command.getPlayerColor() == ChessGame.TeamColor.WHITE && !game.whiteUsername().equals(username)){
            ErrorMessage errorMessage = new ErrorMessage("Error: Tried to join invalid white");
            session.getRemote().sendString(new Gson().toJson(errorMessage));
        }
        else if (command.getPlayerColor() == ChessGame.TeamColor.BLACK && !game.blackUsername().equals(username)){
            ErrorMessage errorMessage = new ErrorMessage("Error: Tried to join invalid black");
            session.getRemote().sendString(new Gson().toJson(errorMessage));
        }
        // Now do actual join logic
        else {
            if (command.getPlayerColor() == ChessGame.TeamColor.WHITE){
                game.setWhiteUsername(username);
                gameDAO.updateWhite(username, command.getGameID());
            } else if (command.getPlayerColor() == ChessGame.TeamColor.BLACK){
                game.setBlackUsername(username);
                gameDAO.updateBlack(username,command.getGameID());
            }

            // Send message to others in database
            LoadMessage loadMessage = new LoadMessage(game.game());
            session.getRemote().sendString(new Gson().toJson(loadMessage));

            // Add them to the list of connections and broadcast it
            String message = (command.getUsername() + " joined as " + command.getPlayerColor());
            NotificationMessage notificationMessage = new NotificationMessage(message);
            connectionManager.add(username, session);
            connectionManager.broadcast(username, notificationMessage);
        }
    }

    public void observe(Session session, JoinObserver command) throws IOException {
        // Open databases and get game and auth of user
        Game_Record game = gameDAO.findGame(Integer.parseInt(command.getGameID()));
        Auth_Record tokenModel= authDAO.findAuth(command.getAuthString());

        // Set the username from the authKey
        String username = null;
        if (tokenModel != null)
            username = tokenModel.username();

        // Check authorization
        if (authDAO.findAuth(command.getAuthString()) == null){
            ErrorMessage errorMessage = new ErrorMessage("Error: Unauthorized");
            session.getRemote().sendString(new Gson().toJson(errorMessage));
        }
        // Make sure the game exists
        else if (game == null){
            ErrorMessage errorMessage = new ErrorMessage("Error: Game does not exist");
            session.getRemote().sendString(new Gson().toJson(errorMessage));
        }
        // Do the actual logic
        else{
            // Send the load message to the session
            LoadMessage loadMessage = new LoadMessage(game.game());
            session.getRemote().sendString(new Gson().toJson(loadMessage));

            // Add them to the list of connections and broadcast it
            String message = (username + " joined as an observer");
            NotificationMessage notificationMessage = new NotificationMessage(message);
            connectionManager.add(username, session);
            connectionManager.broadcast(username, notificationMessage);
        }
    }

    public void move(Session session, MakeMove command) throws InvalidMoveException, IOException {
        // Open databases and get game
        Game_Record game = gameDAO.findGame(Integer.parseInt(command.getGameID()));

        // Make sure the game exists
        if (game == null){
            ErrorMessage errorMessage = new ErrorMessage("Error: Game does not exist");
            session.getRemote().sendString(new Gson().toJson(errorMessage));
        }
        // Move to the move logic
        else{
            // Open databases and get auth of user
            gameSave = game.game();
            Auth_Record tokenModel = authDAO.findAuth(command.getAuthString());

            // Set the username from the authKey
            String username = null;
            if (tokenModel != null)
                username = tokenModel.username();

            // Get valid moves
            Collection<ChessMove> moves = gameSave.validMoves(command.getMove().getStartPosition());

            // Make sure there are moves
            if (moves == null){
                ErrorMessage errorMessage = new ErrorMessage("Error: No valid moves");
                session.getRemote().sendString(new Gson().toJson(errorMessage));
            }
            // Check the user is either black or white
            else if (!game.whiteUsername().equals(username) && !game.blackUsername().equals(username)){
                ErrorMessage errorMessage = new ErrorMessage("Error: Unauthorized");
                session.getRemote().sendString(new Gson().toJson(errorMessage));
            }
            // Make sure it is the correct players turn (white)
            else if (gameSave.getTeamTurn() == ChessGame.TeamColor.WHITE && !game.whiteUsername().equals(username)) {
                ErrorMessage errorMessage = new ErrorMessage("Error: White playing out of order");
                session.getRemote().sendString(new Gson().toJson(errorMessage));
            }
            // Make sure it is the correct players turn (black)
            else if (gameSave.getTeamTurn() == ChessGame.TeamColor.BLACK && !game.blackUsername().equals(username)) {
                ErrorMessage errorMessage = new ErrorMessage("Error: Black playing out of order");
                session.getRemote().sendString(new Gson().toJson(errorMessage));
            }
            // If the requested move is in the valid move list
            else if (moves.contains(command.getMove())){
                // Make the move to the game, and update the game in the database
                gameSave.makeMove(command.getMove());
                gameDAO.update(gameSave, command.getGameID());

                // Send the Load Message
                LoadMessage loadMessage = new LoadMessage(gameSave);
                session.getRemote().sendString(new Gson().toJson(loadMessage));

                // Send notification to others in game
                NotificationMessage notificationMessage = new NotificationMessage("Move made");
                connectionManager.broadcastBoard(username, loadMessage);
                connectionManager.broadcast(username, notificationMessage);
            }
            // If the requested move is not in the valid move list
            else{
                ErrorMessage errorMessage = new ErrorMessage("Error: Invalid move");
                session.getRemote().sendString(new Gson().toJson(errorMessage));
            }
        }
    }

    public void leave(Session session, Leave command) throws IOException {
        // Open databases and get game
        Game_Record game = gameDAO.findGame(Integer.parseInt(command.getGameID()));

        // Check if the game exists
        if (gameDAO.findGame(Integer.parseInt(command.getGameID())) == null){
            ErrorMessage errorMessage = new ErrorMessage("Error: The game does not exist");
            session.getRemote().sendString(new Gson().toJson(errorMessage));
            return;
        }

        // Open databases and get auth of user
        gameSave = game.game();
        Auth_Record tokenModel= authDAO.findAuth(command.getAuthString());

        // Set the username from the authKey
        String username = null;
        if (tokenModel!= null)
            username = tokenModel.username();

        // Removes the user from the game
        if (game.blackUsername().equals(username)){
            game.setBlackUsername(null);
            gameDAO.updateBlack(username, command.getGameID());
        }
        if (Objects.equals(game.whiteUsername(), username)){
            game.setWhiteUsername(null);
            gameDAO.updateWhite(username, command.getGameID());
        }

        // Remove them from the list of connections and broadcast it
        String message = (username + " has left the game");
        NotificationMessage notificationMessage = new NotificationMessage(message);
        connectionManager.remove(username);
        connectionManager.broadcast(username, notificationMessage);
    }
    public void resign(Session session, Resign command) throws IOException {
        // Open databases and get game
        Game_Record game = gameDAO.findGame(Integer.parseInt(command.getGameID()));

        // Make sure the game exists
        if (game == null){
            ErrorMessage errorMessage = new ErrorMessage("Error: The game does not exist");
            session.getRemote().sendString(new Gson().toJson(errorMessage));
        }
        // Start the resignation logic
        else {
            // Open databases and get auth of user
            gameSave = game.game();
            Auth_Record tokenModel= authDAO.findAuth(command.getAuthString());

            // Set the username from the authKey
            String username = null;
            if (tokenModel!= null)
                username = tokenModel.username();

            // If the user is in the game, delete the game
            if (game.whiteUsername().equals(username) || game.blackUsername().equals(username))
                gameDAO.delete(command.getGameID());

            // Check if the user is in the game
            if (!game.whiteUsername().equals(username) && !game.blackUsername().equals(username)){
                ErrorMessage errorMessage = new ErrorMessage("Error: Unauthorized");
                session.getRemote().sendString(new Gson().toJson(errorMessage));
            }
            // Send the message they left the game
            else{
                // Remove them from the list and send the notification
                String message = (username + " has left the game");
                NotificationMessage notificationMessage = new NotificationMessage(message);
                connectionManager.remove(username);
                connectionManager.broadcastALL(username, notificationMessage);
            }
        }
    }

    // Adapters for the Position and Move class
    static class PosAdapter implements JsonDeserializer<ChessPosition> {
        @Override
        public ChessPosition deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return jsonDeserializationContext.deserialize(jsonElement,Position.class);
        }
    }
    static class MoveAdapter implements JsonDeserializer<ChessMove> {
        @Override
        public ChessMove deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return jsonDeserializationContext.deserialize(jsonElement,Move.class);
        }
    }
}
