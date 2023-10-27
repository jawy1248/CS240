package dataAccess;

import chess.ChessGame;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Database for all the current live games
 */
public class Game_DAO {
    /**
     * Private variables include the whiteUsername, blackUsername,
     * gameName, and gameObject where gameID is the unique ID.
     * It also includes a HashSet of game observers for each game
     */
    private HashMap<Integer, String> whiteUsername = new HashMap<>();
    private HashMap<Integer, String> blackUsername = new HashMap<>();
    private HashMap<Integer, String> gameName = new HashMap<>();
    private HashMap<Integer, ChessGame> gameObjects = new HashMap<>();
    private HashMap<Integer, HashSet<String>> gameObservers = new HashMap<>();
    private HashSet<String> observers = new HashSet<>();


    /**
     * Adds a game to the DB
     * @param game Game_Record object to be added
     */
    public void addGame(Game_Record game){
        whiteUsername.put(game.gameID(), game.whiteUser());
        blackUsername.put(game.gameID(), game.blackUser());
        gameName.put(game.gameID(), game.gameName());
        gameObjects.put(game.gameID(), game.game());
    }

    /**
     * Searches if a gameID is a valid current ID
     * @param gameID int of gameID to be found
     * @return true if found, false otherwise
     */
    public boolean findGameID(int gameID){ return whiteUsername.containsKey(gameID); }

    /**
     * Adds a user to a game already created as either white or black
     * @param color BLACK/WHITE to say request color
     * @param gameID int of gameID to be added to
     * @param username String of username to be added
     */
    public void joinGame(ChessGame.TeamColor color, int gameID, String username){
        switch (color) {
            case WHITE -> setWhiteUsername(gameID, username);
            case BLACK -> setBlackUsername(gameID, username);
        }
    }

    /**
     * Adds a user to a game already created as an observer
     * @param gameID int of gameID to be added to
     * @param username String of username to be added
     */
    public void addObserver(int gameID, String username){
        observers.add(username);
        gameObservers.replace(gameID, observers);
    }

    /**
     * Completely clears all the data in the database
     */
    public void clearAllGames(){
        whiteUsername.clear();
        blackUsername.clear();
        gameName.clear();
        gameObjects.clear();
        gameObservers.clear();
    }

    // --------------- SETTERS & GETTERS ---------------
    public void setWhiteUsername(int gameID, String username){
        whiteUsername.replace(gameID, username);
    }
    public void setBlackUsername(int gameID, String username){
        blackUsername.replace(gameID, username);
    }
    public Set<Integer> getKeys(){ return gameObjects.keySet(); }
    public String getWhiteUsername(int gameID){ return whiteUsername.get(gameID); }
    public String getBlackUsername(int gameID){ return blackUsername.get(gameID); }
    public String getGameName(int gameID){ return gameName.get(gameID); }

}
