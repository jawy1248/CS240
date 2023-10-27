package dataAccess;

import chess.ChessGame;
import java.util.HashSet;
import java.util.UUID;
import java.util.Set;

/**
 * Acts as an in-memory database before running SQL
 */
public class Database {

    /**
     * Private variables include the databases for the user, auth, and game
     */
    private static User_DAO userDB = new User_DAO();
    private static Auth_DAO authDB = new Auth_DAO();
    private static Game_DAO gameDB = new Game_DAO();

    /**
     * Clears all the databases...complete clear
     */
    public void clearAll(){
        userDB.clearAllUsers();
        authDB.clearAllAuth();
        gameDB.clearAllGames();
    }

    // -------------------- USERS --------------------
    /**
     * Finds a User in the userDB. Returns the whole record of the user
     * @param username String of the username to look for
     * @return User_Record object of the found user (or null if none are found)
     */
    public User_Record findUser(String username){
        return userDB.getUserData(username);
    }

    /**
     * Adds a user to the userDB
     * @param user User_Record object to be added to the DB
     */
    public void addUser(User_Record user){
        userDB.addUser(user);
    }

    // -------------------- AUTHS --------------------
    /**
     * Creates a random AuthToken
     * @return String of AuthToken
     */
    public String createAuthToken(){
        return UUID.randomUUID().toString();
    }

    /**
     * Adds an AuthToken to the DB
     * @param auth Auth_Record object to be added
     */
    public void addAuthToken(Auth_Record auth) { authDB.addAuth(auth); }

    /**
     * Removes an AuthToken from the DB
     * @param username String of the username to be removed
     */
    public void removeAuthToken(String username) { authDB.removeAuth(username); }

    /**
     * Finds a username for a specific AuthToken
     * @param authToken String of the AuthToken
     * @return String of the associated username
     */
    public String getUsername(String authToken) { return authDB.getUsername(authToken); }

    /**
     * Searches to see if an AuthToken exists
     * @param authToken String of AuthToken to be found
     * @return true if found, false otherwise
     */
    public boolean findAuthToken(String authToken) { return authDB.findAuthToken(authToken); }

    // -------------------- GAMES --------------------
    /**
     * Randomly creates a gameID
     * @return int of gameID
     */
    public int createGameID(){ return (int)(Math.random() * 10000);}

    /**
     * Adds a game to the DB
     * @param game Game_Record object to be added
     */
    public void createGame(Game_Record game) { gameDB.addGame(game); }

    /**
     * Searches if a gameID is a valid current ID
     * @param gameID int of gameID to be found
     * @return true if found, false otherwise
     */
    public boolean findGameID(int gameID){ return gameDB.findGameID(gameID); }

    /**
     * Adds a user to a game already created as either white or black
     * @param color BLACK/WHITE to say request color
     * @param gameID int of gameID to be added to
     * @param username String of username to be added
     */
    public void joinGame(ChessGame.TeamColor color, int gameID, String username){ gameDB.joinGame(color, gameID, username); }

    /**
     * Adds a user to a game already created as an observer
     * @param gameID int of gameID to be added to
     * @param username String of username to be added
     */
    public void observeGame(int gameID, String username){ gameDB.addObserver(gameID, username);}

    /**
     * Lists all the games as a HashSet of Game_Records
     * @return HashSet of Game_Records currently in use
     */
    public HashSet<Game_Record> listGames(){
        HashSet<Game_Record> listOfGames = new HashSet<>();
        // Iterate through each of the gameIDs and add each game to the set
        Set<Integer> keys = gameDB.getKeys();
        for (Integer key : keys) {
            // Temps of each of the objects
            int temp = key;
            String tempWhite = gameDB.getWhiteUsername(temp);
            String tempBlack = gameDB.getBlackUsername(temp);
            String tempName = gameDB.getGameName(temp);

            // Create and add temp Game_Record
            Game_Record tempRecord = new Game_Record(temp, tempWhite, tempBlack, tempName, null);
            listOfGames.add(tempRecord);
        }
        // Return the list
        return listOfGames;
    }
}