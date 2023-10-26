package dataAccess;

import chess.ChessGame;

import java.util.HashMap;

public class Database {
    /**
     * Private variables include the databases for the user, auth, and game
     */
    private User_DAO userDB = new User_DAO();
    private Auth_DAO authDB = new Auth_DAO();
    private Game_DAO gameDB = new Game_DAO();

    public void clearAll(){
        userDB.clearAllUsers();
        authDB.clearAllAuth();
        gameDB.clearAllGames();
    }

}
