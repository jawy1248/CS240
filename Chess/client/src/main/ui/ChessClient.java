package ui;

import chess.*;
import com.google.gson.Gson;
import response.*;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static ui.EscapeSequences.*;


public class ChessClient {
    public ServerFacade serverFacade;
    public boolean loggedIN = false;
    public boolean joined = false;
    public boolean playing = false;
    public String authToken;
    public String username;
    public String gameID;
    public ChessBoard chessBoard = new Board();
    public ChessGame chessGame;
    public String color;

    public ChessClient() throws Exception {
        serverFacade = new ServerFacade("http://localhost:8080");
    }

    // -------------------- Main Method --------------------
    public String command(String commandIN) throws Exception{
        commandIN = commandIN.toLowerCase();

        String[] length = commandIN.split(" ");
        if(length.length == 0)
            return " ";
        if(!loggedIN){
            // not logged in
            if(commandIN.contains("help")){
                return  "help -> List possible commands\n" +
                        "quit -> Exits the chess program\n" +
                        "login <USERNAME> <PASSWORD> -> Login to play\n" +
                        "register <USERNAME> <PASSWORD> <EMAIL> -> Register an account\n";
            }
            if (commandIN.contains("quit")) {
                return "Exiting the program";
            }
            if (commandIN.contains("register")) {
                String temp = register(length);
                System.out.println(temp);
            }
            if (commandIN.contains("login")) {
                String temp = login(length);
                System.out.println(temp);
            }
        }else{
            // logged in
            if(!joined) {
                // not in a game
                if (commandIN.contains("help")) {
                    return "help -> List possible commands\n" +
                            "quit -> Exits the chess program\n" +
                            "logout -> Log out of the game\n" +
                            "create <NAME> -> Creates a chess game\n" +
                            "list -> Lists all ongoing chess games\n" +
                            "join <gameID> <WHITE | BLACK> -> Joins a game as either white or black\n" +
                            "watch <gameID> -> Joins a game as an observer\n";
                }
                if (commandIN.contains("quit")) {
                    return "Exiting the program";
                }
                if (commandIN.contains("logout")) {
                    String temp = logout(length);
                    System.out.println(temp);
                }
                if (commandIN.contains("create")) {
                    String temp = create(length);
                    System.out.println(temp);
                }
                if (commandIN.contains("list")) {
                    String temp = list(length);
                    System.out.println(temp);
                }
                if (commandIN.contains("join")) {
                    String temp = join(length);
                    System.out.println(temp);
                }
                if (commandIN.contains("watch")) {
                    String temp = watch(length);
                    System.out.println(temp);
                }
            } else {
                // in a game
                if(!playing){
                    // not a player of the game

                } else {
                    // a player of the game

                }
            }
        }
        return "";
    }

    // -------------------- Helper Methods --------------------
    // Register
    public String register(String[] com){
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
    public String login(String[] com){
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
    public String logout(String[] com){
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
            return "Successfully logged out";
        }

        return "Failed to log out";
    }

    // Create Game
    public String create(String[] com){
        if(com.length != 2)
            return "ERROR - To create game, provide only game name";

        CreateGame_Resp resp = serverFacade.create(com[1]);
        if(resp.getCode() == 200)
            return "Successfully created game with gameID: " + resp.getGameID();

        return "Failed to create game";
    }

    // List Games
    public String list(String[] com){
        if(com.length != 1)
            return "ERROR - To list games, do NOT provide any additional arguments";

        ListGames_Resp resp = serverFacade.list();
        if(resp.getCode() == 200)
            return "List of game:\n" + new Gson().toJson(resp);

        return null;
    }

    // Join Game
    public String join(String[] com){
        if(com.length != 3)
            return "ERROR - To join game, provide only gameID and team color";

        Response resp = serverFacade.join(com[1], com[2]);
        if(resp.getCode() == 200) {
            joined = true;
            playing = true;
            gameID = com[1];
            color = com[2];
            return "Successfully joined game " + com[1] + " as player: " + com[2];
        }

        return "Failed to join requested game";
    }

    // Join Observer
    public String watch(String[] com){
        if(com.length != 2)
            return "ERROR - To watch game, provide only gameID";

        Response resp = serverFacade.watch(com[1]);
        if(resp.getCode() == 200) {
            joined = true;
            playing = false;
            gameID = com[1];
            return "Successfully joined game " + com[1] + " as an observer";
        }

        return "Failed to watch requested game";
    }

    public String showBoard(){
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        out.print(ERASE_SCREEN);

        return null;
    }

}
