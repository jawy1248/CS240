package ws;

import model.Auth_Record;
import model.Game_Record;
import chess.*;
import com.google.gson.*;
import dataAccess.Auth_DAO;
import dataAccess.Database;
import dataAccess.Game_DAO;
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
    Auth_DAO authDAO;
    Game_DAO gameDAO;
    Connection connection;

    Database db = new Database();
    ChessGame game;


    public WSHANDLER(Connection connection){
        this.connection = connection;
    }
    public final ws.ConnectionManager connectionManager = new ConnectionManager();

    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws Exception {
        System.out.println(message);
        connection = db.getConnection();
        authDAO = new Auth_DAO(connection);
        gameDAO = new Game_DAO(connection);
        UserGameCommand command = new Gson().fromJson(message, UserGameCommand.class);
        System.out.println("COMMAND TYPE " + command.getCommandType());
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(ChessPosition.class, new ListAdapter());
        Gson gson = gsonBuilder.create();
        switch (command.getCommandType()) {
            case JOIN_PLAYER -> join(session, new Gson().fromJson(message, JoinPlayer.class));
            case JOIN_OBSERVER -> observe(session, new Gson().fromJson(message, JoinObserver.class));
            case MAKE_MOVE -> move(session, gson.fromJson(message, MakeMove.class));
            case LEAVE -> leave(session, new Gson().fromJson(message, Leave.class));
            case RESIGN -> resign(session, new Gson().fromJson(message, Resign.class));
        }
    }


    public void join(Session session, JoinPlayer command) throws IOException {
        Game_Record game = gameDAO.findGame(Integer.parseInt(command.getGameID()));
        Auth_Record tokenModel= authDAO.findAuth(command.getAuthString());
        String username = null;
        if (tokenModel != null){
            username = tokenModel.username();
        }
        if (authDAO.findAuth(command.getAuthString()) == null){
            ErrorMessage errorMessage = new ErrorMessage("error: That game doesn't exist");
            errorMessage.errorMessage ="Error that game is no good";
            session.getRemote().sendString(new Gson().toJson(errorMessage));
        }
        else if (game == null){
            ErrorMessage errorMessage = new ErrorMessage("error: That game doesn't exist");
            errorMessage.errorMessage ="Error that game is no good";
            session.getRemote().sendString(new Gson().toJson(errorMessage));
        }
        else if (command.getPlayerColor() == null){
            ErrorMessage errorMessage = new ErrorMessage("error: That game doesn't exist");
            errorMessage.errorMessage ="Error there is no color";
            session.getRemote().sendString(new Gson().toJson(errorMessage));
        }
        else if (command.getPlayerColor() == ChessGame.TeamColor.WHITE && !Objects.equals(game.whiteUsername(), username)){
            ErrorMessage errorMessage = new ErrorMessage("error: That game doesn't exist");
            errorMessage.errorMessage ="Error there is no color";
            session.getRemote().sendString(new Gson().toJson(errorMessage));
        }
        else if (command.getPlayerColor() == ChessGame.TeamColor.BLACK && !Objects.equals(game.blackUsername(), username)){
            ErrorMessage errorMessage = new ErrorMessage("error: That game doesn't exist");
            errorMessage.errorMessage ="Error there is no color";
            session.getRemote().sendString(new Gson().toJson(errorMessage));
        }
        else {
            if (command.getPlayerColor() == ChessGame.TeamColor.WHITE){
                game.setWhiteUsername(username);
                gameDAO.updateWhite(username, command.getGameID());
            }
            if (command.getPlayerColor() == ChessGame.TeamColor.BLACK){
                game.setBlackUsername(username);
                gameDAO.updateBlack(username,command.getGameID());
            }
            LoadMessage loadMessage = new LoadMessage(game.game());
            session.getRemote().sendString(new Gson().toJson(loadMessage));
            connectionManager.add(username, session);
            String message = (command.getUsername() + " has joined as " + command.getPlayerColor());
            NotificationMessage notificationMessage = new NotificationMessage(message);
            notificationMessage.message =message;
            connectionManager.broadcast(username, notificationMessage);
        }
    }
    public void observe(Session session, JoinObserver command) throws IOException {
        Game_Record game = gameDAO.findGame(Integer.parseInt(command.getGameID()));
        Auth_Record tokenModel= authDAO.findAuth(command.getAuthString());
        String username = null;
        if (tokenModel!= null){
            username = tokenModel.username();
        }
        if (authDAO.findAuth(command.getAuthString()) == null){
            ErrorMessage errorMessage = new ErrorMessage("error: That game doesn't exist");
            errorMessage.errorMessage ="Error that game is no good";
            session.getRemote().sendString(new Gson().toJson(errorMessage));
        }
        else if (game == null){
            ErrorMessage errorMessage = new ErrorMessage("error: That game doesn't exist");
            errorMessage.errorMessage ="Error that game is no good";
            session.getRemote().sendString(new Gson().toJson(errorMessage));
        }
        else{
            LoadMessage loadMessage = new LoadMessage(game.game());
            session.getRemote().sendString(new Gson().toJson(loadMessage));
            connectionManager.add(username, session);
            String message = (username + " has joined as an observer");
            NotificationMessage notificationMessage = new NotificationMessage(message);
            notificationMessage.message = message;
            connectionManager.broadcast(username, notificationMessage);
        }

    }
    public void move(Session session, MakeMove command) throws InvalidMoveException, IOException {
        Game_Record game = gameDAO.findGame(Integer.parseInt(command.getGameID()));
        if (game == null){
            System.out.println("CUNT");
            ErrorMessage errorMessage = new ErrorMessage("error: That game doesn't exist");
            errorMessage.errorMessage ="Error there is no color";
            session.getRemote().sendString(new Gson().toJson(errorMessage));
        }else{
            this.game = game.game();
            Auth_Record tokenModel= authDAO.findAuth(command.getAuthString());
            String username = null;
            if (tokenModel!= null){
                username = tokenModel.username();
            }

            Collection<ChessMove> moves = this.game.validMoves(command.getMove().getStartPosition());
            if (moves == null){
                System.out.println("Null moves");
                ErrorMessage errorMessage = new ErrorMessage("error: That game doesn't exist");
                errorMessage.errorMessage ="Error there is no color";
                session.getRemote().sendString(new Gson().toJson(errorMessage));
            }
            else if (!Objects.equals(game.whiteUsername(), username) && !Objects.equals(game.blackUsername(), username)){
                System.out.println("white usernames");
                ErrorMessage errorMessage = new ErrorMessage("error: That game doesn't exist");
                errorMessage.errorMessage ="Error there is no color";
                session.getRemote().sendString(new Gson().toJson(errorMessage));
            } else if (this.game.getTeamTurn() == ChessGame.TeamColor.WHITE && !Objects.equals(game.whiteUsername(), username)) {
                ErrorMessage errorMessage = new ErrorMessage("error: That game doesn't exist");
                errorMessage.errorMessage ="Error there is no color";
                session.getRemote().sendString(new Gson().toJson(errorMessage));
            } else if (this.game.getTeamTurn() == ChessGame.TeamColor.BLACK && !Objects.equals(game.blackUsername(), username)) {
                ErrorMessage errorMessage = new ErrorMessage("error: That game doesn't exist");
                errorMessage.errorMessage = "Error there is no color";
                session.getRemote().sendString(new Gson().toJson(errorMessage));
            }else if (moves.contains(command.getMove())){
                System.out.println("making move");
                this.game.makeMove(command.getMove());
                gameDAO.update(this.game, command.getGameID());
                LoadMessage loadMessage = new LoadMessage(this.game);
                session.getRemote().sendString(new Gson().toJson(loadMessage));
                connectionManager.broadcastBoard(username, loadMessage);
                NotificationMessage notificationMessage = new NotificationMessage("Gay");
                notificationMessage.message = "Move made";
                connectionManager.broadcast(username,notificationMessage);
            }
            else{
                ErrorMessage errorMessage = new ErrorMessage("error: That game doesn't exist");
                errorMessage.errorMessage ="Error there is no color";
                session.getRemote().sendString(new Gson().toJson(errorMessage));
            }
        }


    }
    public void leave(Session session, Leave command) throws IOException {
        Game_Record game = gameDAO.findGame(Integer.parseInt(command.getGameID()));
        if (gameDAO.findGame(Integer.parseInt(command.getGameID())) == null){
            ErrorMessage errorMessage = new ErrorMessage("error: That game doesn't exist");
            errorMessage.errorMessage = "Error there is no color";
            session.getRemote().sendString(new Gson().toJson(errorMessage));
            return;
        }
        this.game = game.game();
        Auth_Record tokenModel= authDAO.findAuth(command.getAuthString());
        String username = null;
        if (tokenModel!= null){
            username = tokenModel.username();
        }
        if (Objects.equals(game.blackUsername(), username)){
            game.setBlackUsername(null);
            gameDAO.updateBlack(username, command.getGameID());
        }
        if (Objects.equals(game.whiteUsername(), username)){
            game.setWhiteUsername(null);
            gameDAO.updateWhite(username, command.getGameID());
        }
        String message = (username + " has left the game");
        NotificationMessage notificationMessage = new NotificationMessage(message);
        notificationMessage.message = message;
        connectionManager.broadcast(username, notificationMessage);
        connectionManager.remove(username);
    }
    public void resign(Session session, Resign command) throws IOException {
        Game_Record game = gameDAO.findGame(Integer.parseInt(command.getGameID()));
        if (game == null){
            ErrorMessage errorMessage = new ErrorMessage("error: That game doesn't exist");
            errorMessage.errorMessage ="Error there is no color";
            session.getRemote().sendString(new Gson().toJson(errorMessage));
        }else {
            this.game = game.game();
            Auth_Record tokenModel= authDAO.findAuth(command.getAuthString());
            String username = null;
            if (tokenModel!= null){
                username = tokenModel.username();
            }
            if (Objects.equals(game.blackUsername(), username)){
                gameDAO.delete(command.getGameID());
            }
            if (Objects.equals(game.whiteUsername(), username)){
                gameDAO.delete(command.getGameID());
            }
            if (!Objects.equals(game.blackUsername(), username) && !Objects.equals(game.whiteUsername(), username)){
                ErrorMessage errorMessage = new ErrorMessage("error: That game doesn't exist");
                errorMessage.errorMessage ="Error there is no color";
                session.getRemote().sendString(new Gson().toJson(errorMessage));
            }else{
                String message = (username + " has left the game");
                NotificationMessage notificationMessage = new NotificationMessage(message);
                notificationMessage.message = message;
                connectionManager.broadcastALL(username, notificationMessage);
                connectionManager.remove(username);
            }
        }
    }

    static class ListAdapter implements JsonDeserializer<ChessPosition> {
        @Override
        public ChessPosition deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return jsonDeserializationContext.deserialize(jsonElement,ChessPosition.class);
        }
    }

}
