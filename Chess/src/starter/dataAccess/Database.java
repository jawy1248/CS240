package dataAccess;

import chess.ChessGame;
import chess.Game;

import java.util.Collection;
import java.util.HashSet;
import java.util.UUID;

public class Database {
    /**
     * Private variables include the databases for the user, auth, and game
     */
    private static User_DAO userDB = new User_DAO();
    private static Auth_DAO authDB = new Auth_DAO();
    private static Game_DAO gameDB = new Game_DAO();

    public void clearAll(){
        userDB.clearAllUsers();
        authDB.clearAllAuth();
        gameDB.clearAllGames();
    }

    // ----- USERS -----
    public User_Record findUser(String username){
        return userDB.getUserData(username);
    }
    public void addUser(User_Record user){
        userDB.addUser(user);
    }

    // ----- AUTHS -----
    public String createAuthToken(){
        return UUID.randomUUID().toString();
    }
    public void addAuthToken(Auth_Record auth) { authDB.addAuth(auth); }
    public void removeAuthToken(String username) { authDB.removeAuth(username); }
    public String getAuthToken(String username) { return authDB.getAuthToken(username); }
    public boolean findAuthToken(String authToken) { return authDB.findAuthToken(authToken); }
    public boolean findUserAuth(String username) { return authDB.findUsername(username); }
    public String getUsername(String authToken) { return authDB.getUsername(authToken); }

    // ----- GAMES -----
    public int createGameID(){ return (int)(Math.random() * 10000);}
    public void createGame(Game_Record game) { gameDB.addGame(game); }
    public boolean findGameID(int gameID){ return gameDB.findGameID(gameID); }
    public void joinGame(ChessGame.TeamColor color, int gameID, String username){ gameDB.joinGame(color, gameID, username); }
    public void observeGame(int gameID, String username){ gameDB.addObserver(gameID, username);}
    public HashSet<Game> listGames(){ return gameDB.listGames(); }
}
