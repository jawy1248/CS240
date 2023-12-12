package ui;

import chess.*;
import model.Game_Record;
import response.*;
import webSocketMessages.userCommands.*;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Scanner;

import static ui.EscapeSequences.*;

public class ChessClient implements NotificationHandler {
    public ServerFacade serverFacade;
    public webSocketClient ws;
    public boolean loggedIN = false;
    public boolean joined = false;
    public boolean playing = false;
    public String authToken;
    public String username;
    public String gameID;
    public PrintBoard board = new PrintBoard();
    public ChessGame chessGame;
    public ChessBoard chessBoard;
    public String color;
    public ChessGame.TeamColor teamColor;

    public ChessClient() throws Exception {
        serverFacade = new ServerFacade("http://localhost:8080");
        ws = new webSocketClient("ws://localhost:8080/connect", this);
    }

    // -------------------- Main Method --------------------
    public String command(String commandIN) throws Exception{
        // Set the command to lowercase to be safe
        commandIN = commandIN.toLowerCase();

        // Split the command into words and make sure that it has a command
        String[] length = commandIN.split(" ");
        if(length.length == 0)
            return " ";

        // Start command tree...depending on variables determines commands available
        if(!loggedIN){
            // not logged in
            if(commandIN.contains("help")){
                System.out.print(SET_TEXT_COLOR_GREEN + "HELP" + SET_TEXT_COLOR_WHITE + " - List possible commands\n" +
                        SET_TEXT_COLOR_GREEN + "QUIT" + SET_TEXT_COLOR_WHITE + " - Exits the chess program\n" +
                        SET_TEXT_COLOR_GREEN + "LOGIN" + SET_TEXT_COLOR_BLUE + " <USERNAME> <PASSWORD>" + SET_TEXT_COLOR_WHITE + " - Login to play\n" +
                        SET_TEXT_COLOR_GREEN + "REGISTER" + SET_TEXT_COLOR_BLUE + " <USERNAME> <PASSWORD> <EMAIL>" + SET_TEXT_COLOR_WHITE + " - Register an account\n");
                return "[LOGGED_OUT]";
            }
            if (commandIN.contains("quit")) {
                System.out.println("Exiting the program");
                return "";
            }
            if (commandIN.contains("clear")) {
                String temp = clear(length);
                if(temp.equals("Failed to clear"))
                    System.out.println("System did not clear");
                else
                    System.out.println("Clearing the program");

                return "[LOGGED_OUT]";
            }
            if (commandIN.contains("register")) {
                String temp = register(length);
                System.out.println(temp);

                if(temp.equals("Failed to register"))
                    return "[LOGGED_OUT]";

                return "[LOGGED_IN]";
            }
            if (commandIN.contains("login")) {
                String temp = login(length);
                System.out.println(temp);

                if(temp.equals("Failed to login"))
                    return "[LOGGED_OUT]";

                return "[LOGGED_IN]";
            }
        }else{
            // logged in
            if(!joined) {
                // not in a game
                if (commandIN.contains("help")) {
                    System.out.print(SET_TEXT_COLOR_GREEN + "HELP" + SET_TEXT_COLOR_WHITE + " - List possible commands\n" +
                            SET_TEXT_COLOR_GREEN + "QUIT" + SET_TEXT_COLOR_WHITE + " - Exits the chess program\n" +
                            SET_TEXT_COLOR_GREEN + "LOGOUT" + SET_TEXT_COLOR_WHITE + " - Log out of the game\n" +
                            SET_TEXT_COLOR_GREEN + "CREATE" + SET_TEXT_COLOR_BLUE + " <NAME>" + SET_TEXT_COLOR_WHITE + " Creates a chess game\n" +
                            SET_TEXT_COLOR_GREEN + "LIST" + SET_TEXT_COLOR_WHITE + " - Lists all ongoing chess games\n" +
                            SET_TEXT_COLOR_GREEN + "JOIN" + SET_TEXT_COLOR_BLUE + " <gameID> <WHITE | BLACK>" + SET_TEXT_COLOR_WHITE + " - Joins a game as either white or black\n" +
                            SET_TEXT_COLOR_GREEN + "WATCH" + SET_TEXT_COLOR_BLUE + " <gameID>" + SET_TEXT_COLOR_WHITE + " - Joins a game as an observer\n");
                    return "[LOGGED_IN]";
                }
                if (commandIN.contains("quit"))
                    return "Exiting the program";

                if (commandIN.contains("logout")) {
                    String temp = logout(length);
                    System.out.println(temp);

                    if(temp.equals("Failed to log out"))
                        return "[LOGGED_IN]";

                    return "[LOGGED_OUT]";
                }
                if (commandIN.contains("create")) {
                    String temp = create(length);
                    System.out.println(temp);
                    return "[LOGGED_IN]";
                }
                if (commandIN.contains("list")) {
                    String temp = list(length);
                    System.out.println(temp);
                    return "[LOGGED_IN]";
                }
                if (commandIN.contains("join")) {
                    String temp = join(length);
                    System.out.println(temp);

                    if(temp.equals("Failed to join"))
                        return "[LOGGED_IN]";

                    return "[IN_GAME]";
                }
                if (commandIN.contains("watch")) {
                    String temp = watch(length);
                    System.out.println(temp);

                    if(temp.equals("Failed to watch"))
                        return "[LOGGED_IN]";

                    return "[IN_GAME]";
                }
            } else {
                // in a game
                if(!playing){
                    // not a player of the game
                    if(commandIN.contains("help")) {
                        System.out.print(SET_TEXT_COLOR_GREEN + "HELP" + SET_TEXT_COLOR_WHITE + " - List possible commands\n" +
                                SET_TEXT_COLOR_GREEN + "QUIT" + SET_TEXT_COLOR_WHITE + " - Exits the chess program\n" +
                                SET_TEXT_COLOR_GREEN + "REDRAW" + SET_TEXT_COLOR_WHITE + " - Redraws the chess board\n" +
                                SET_TEXT_COLOR_GREEN + "LEAVE" + SET_TEXT_COLOR_WHITE + " - Leave the current game\n");
                        return "[IN_GAME]";
                    }
                    if(commandIN.contains("redraw")) {
                        updateBoard(chessGame);
                        return "[IN_GAME]";
                    }
                    if(commandIN.contains("leave")) {
                        leave();
                        return "[LOGGED-IN]";
                    }
                } else {
                    // a player of the game
                    if(commandIN.contains("help")) {
                        System.out.print(SET_TEXT_COLOR_GREEN + "HELP" + SET_TEXT_COLOR_WHITE + " - List possible commands\n" +
                                SET_TEXT_COLOR_GREEN + "QUIT" + SET_TEXT_COLOR_WHITE + " - Exits the chess program\n" +
                                SET_TEXT_COLOR_GREEN + "REDRAW" + SET_TEXT_COLOR_WHITE + " - Redraws the chess board\n" +
                                SET_TEXT_COLOR_GREEN + "LIST" + SET_TEXT_COLOR_BLUE + " <POS>" + SET_TEXT_COLOR_WHITE + " - Lists valid moves for the piece in POS\n" +
                                SET_TEXT_COLOR_GREEN + "MOVE" + SET_TEXT_COLOR_BLUE + " <START POS><END POS>" + SET_TEXT_COLOR_WHITE + " - Make a move\n" +
                                SET_TEXT_COLOR_GREEN + "LEAVE" + SET_TEXT_COLOR_WHITE + " - Leave the current game\n" +
                                SET_TEXT_COLOR_GREEN + "RESIGN" + SET_TEXT_COLOR_WHITE + " - Resign from the game\n");
                        return "[IN_GAME]";
                    }
                    if(commandIN.contains("quit")) {
                        return "Exiting the program";
                    }
                    if(commandIN.contains("redraw")) {
                        updateBoard(chessGame);
                        return "[IN_GAME]";
                    }
                    if(commandIN.contains("list")) {
                        String temp = listMoves(length);
                        System.out.println(temp);
                        return "[IN_GAME]";
                    }
                    if(commandIN.contains("move")) {
                        String temp = move(length);
                        System.out.println(temp);
                        return "[IN_GAME]";
                    }
                    if(commandIN.contains("leave")) {
                        leave();
                        return "[LOGGED-IN]";
                    }
                    if(commandIN.contains("resign")) {
                        System.out.print("Are you sure you want to resign? (y/n): ");
                        // Set scanner to read in text from user command line
                        Scanner scanner = new Scanner(System.in);
                        String ans = scanner.nextLine();

                        if(ans.equalsIgnoreCase("y")) {
                            System.out.println(SET_TEXT_COLOR_RED + "Resigned from game" + SET_TEXT_COLOR_WHITE);
                            resign();
                        }

                        return "[LOGGED_IN]";
                    }
                }
            }
        }
        return "[ERROR - COMMAND NOT FOUND]";
    }

    // -------------------- Helper Methods --------------------
    // Clear
    public String clear(String[] com) throws IOException{
        if(com.length != 1)
            return "ERROR - To clear, do NOT provide any additional arguments";

        Response resp = serverFacade.clear();
        if(resp.getCode() == 200)
            return "Successfully cleared all data";

        return "Failed to clear";
    }

    // Register
    public String register(String[] com) throws IOException {
        if(com.length != 4)
            return "ERROR - To register, provide only username, password, and email";

        RegisterLogin_Resp resp = serverFacade.register(com[1], com[2], com[3]);
        if(resp.getCode() == 200){
            loggedIN = true;
            authToken = resp.getAuthToken();
            username = resp.getUsername();
            return "Welcome " + username + ", you were successfully registered...\n" +
                    "Your Authentication Token is: " + authToken;
        }
        return "Failed to register";
    }

    // Login
    public String login(String[] com) throws IOException{
        if(com.length != 3)
            return "ERROR - To login, provide only username and password";

        RegisterLogin_Resp resp = serverFacade.login(com[1], com[2]);
        if(resp.getCode() == 200){
            loggedIN = true;
            authToken = resp.getAuthToken();
            username = resp.getUsername();
            return "Welcome " + username + ", you successfully logged in...\n" +
                    "Your Authentication Token is: " + authToken;
        }
        return "Failed to login";
    }

    // Logout
    public String logout(String[] com) throws IOException{
        if(com.length != 1)
            return "ERROR - To logout, do NOT provide any additional arguments";

        Response resp = serverFacade.logout();
        if(resp.getCode() == 200) {
            loggedIN = false;
            joined = false;
            playing = false;
            authToken = null;
            username = null;
            gameID = null;
            color = null;
            teamColor = null;
            return "Successfully logged out";
        }

        return "Failed to log out";
    }

    // Create Game
    public String create(String[] com) throws IOException{
        if(com.length != 2)
            return "ERROR - To create game, provide only game name";

        CreateGame_Resp resp = serverFacade.create(com[1]);
        if(resp.getCode() == 200)
            return "Successfully created game with gameID: " + resp.getGameID();

        return "Failed to create";
    }

    // List Games
    public String list(String[] com) throws IOException{
        if(com.length != 1)
            return "ERROR - To list games, do NOT provide any additional arguments";

        ListGames_Resp resp = serverFacade.list();
        if(resp.getCode() == 200) {
            Iterator<Game_Record> iter = resp.getGames().iterator();
            StringBuilder builder = new StringBuilder();
            int i = 1;
            while(iter.hasNext()){
                Game_Record temp = iter.next();
                String whiteTemp = SET_TEXT_COLOR_BLUE + temp.whiteUsername();
                String blackTemp = SET_TEXT_COLOR_BLUE + temp.blackUsername();

                if(temp.whiteUsername() == null)
                    whiteTemp = SET_TEXT_COLOR_RED + "EMPTY";

                if(temp.blackUsername() == null)
                    blackTemp = SET_TEXT_COLOR_RED + "EMPTY";

                builder.append(i).append(". NAME: ").append(SET_TEXT_COLOR_BLUE).append(temp.gameName()).append(SET_TEXT_COLOR_WHITE).append(", ID: ").append(SET_TEXT_COLOR_BLUE).append(temp.gameID()).append(SET_TEXT_COLOR_WHITE).append(", WhiteUser: ").append(whiteTemp).append(SET_TEXT_COLOR_WHITE).append(", BlackUser: ").append(blackTemp).append(SET_TEXT_COLOR_WHITE).append("\n");
                i++;
            }
            return builder.toString();
        }
        return "Failed to list";
    }

    // Join Game
    public String join(String[] com) throws Exception{
        if(com.length != 3)
            return "ERROR - To join game, provide only gameID and team color";

        Response resp = serverFacade.join(com[1], com[2]);
        if(resp.getCode() == 200) {
            joined = true;
            playing = true;
            gameID = com[1];
            color = com[2];

            chessGame = new Game();
            chessBoard = new Board();
            chessBoard.resetBoard();
            chessGame.setBoard(chessBoard);

            if(color.equalsIgnoreCase("WHITE")) {
                teamColor = ChessGame.TeamColor.WHITE;
                updateBoard(chessGame);
            }
            else {
                teamColor = ChessGame.TeamColor.BLACK;
                updateBoard(chessGame);
            }

            JoinPlayer comm = new JoinPlayer(com[1], teamColor, username, authToken);
            ws.send(comm);

            return "Successfully joined game " + gameID + " as player: " + teamColor;
        }

        return "Failed to join";
    }

    // Join Observer
    public String watch(String[] com) throws Exception{
        if(com.length != 2)
            return "ERROR - To watch game, provide only gameID";

        Response resp = serverFacade.watch(com[1]);
        if(resp.getCode() == 200) {
            joined = true;
            playing = false;
            gameID = com[1];

            board.printWhite();

            JoinObserver comm = new JoinObserver(UserGameCommand.CommandType.JOIN_OBSERVER, authToken, gameID, username);
            ws.send(comm);

            return "Successfully joined game " + com[1] + " as an observer";
        }

        return "Failed to watch";
    }

    // List possible moves
    public String listMoves(String[] com){
        if(com.length != 2)
            return "ERROR - To list moves, provide only start position";
        if(com[1].length() != 2)
            return "ERROR - Incorrect position format";

        ChessPosition pos = getPos(com[1]);
        Collection<ChessMove> moves = chessGame.validMoves(pos);
        if(color.equalsIgnoreCase("WHITE"))
            board.whiteMoves(moves);
        else
            board.blackMoves(moves);

        return "";
    }

    // Makes a move
    public String move(String[] com) throws Exception{
        if(com.length != 3)
            return "ERROR - To move, provide start position and end position";

        String pos1Str = com[1];
        String pos2Str = com[2];

        if(pos1Str.length() != 2 || pos2Str.length() != 2)
            return "ERROR - Incorrect position format";

        ChessPosition posStart = getPos(pos1Str);
        ChessPosition posEnd = getPos(pos2Str);
        ChessMove move = new Move(posStart, posEnd);

        if(chessGame.validMoves(posStart) == null || !chessGame.validMoves(posStart).contains(move))
            return "Invalid Move";

        if(chessGame.isInCheck(teamColor))
            System.out.println("In check");

        if(chessGame.isInCheckmate(teamColor))
            return "In checkmate";

        try {
            chessGame.makeMove(move);
            MakeMove command = new MakeMove(gameID, authToken, username, move);
            ws.send(command);
            updateBoard(chessGame);
        } catch(InvalidMoveException e){
            System.out.print("You cannot make that move");
        }
        return "";
    }

    // Leaves a game
    public void leave() throws Exception{
        playing = false;
        joined = false;

        Leave com = new Leave(UserGameCommand.CommandType.LEAVE, authToken, gameID, username);
        ws.send(com);
    }

    // Resigns from a game
    public void resign() throws Exception {
        Resign com = new Resign(UserGameCommand.CommandType.RESIGN, authToken, gameID, username);
        ws.send(com);

        playing = false;
        joined = false;
        gameID = null;
        color = null;
    }

    // Gets position from string
    public ChessPosition getPos(String pos){
        int row = (pos.charAt(0) - 'a') + 1;
        int col = Integer.parseInt(String.valueOf(pos.charAt(1)));
        return new Position(col, row);
    }

    @Override
    public void updateBoard(ChessGame game) {
        this.chessGame = game;
        board.updateUIBoard(game);
        if (color == null)
            board.printWhite();
        else if (color.equalsIgnoreCase("BLACK"))
            board.printBlack();
        else
            board.printWhite();
    }

    @Override
    public void message(String message) {
        if (message != null)
            System.out.print(SET_TEXT_COLOR_RED + message + SET_TEXT_COLOR_WHITE + "\n>>> " + SET_TEXT_COLOR_GREEN);
    }

    @Override
    public void error(String error) {
//        System.out.println(error);
    }
}