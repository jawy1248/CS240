package dataAccess;

import chess.ChessGame;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Holds the information of all the current live games
 */
public class Game_DAO {
    /**
     * Private variables include the whiteUsername, blackUsername,
     * gameName, and game object where gameID is the unique ID
     */
    private HashMap<Integer, Integer> gameID = new HashMap<>();
    private HashMap<Integer, String> whiteUsername = new HashMap<>();
    private HashMap<Integer, String> blackUsername = new HashMap<>();
    private HashMap<Integer, String> gameName = new HashMap<>();
    private HashMap<Integer, ChessGame> gameObjects = new HashMap<>();
    private HashMap<Integer, HashSet<String>> gameObservers = new HashMap<>();
    private HashSet<String> observers = new HashSet<>();

    /**
     * adds a single Game_Record object to the list of all games
     *
     * @param game                  the Game_Record object that hold all the info
     * @throws DataAccessException  for invalid addition of game
     */
    public void addGame(Game_Record game){
        gameID.put(game.gameID(), game.gameID());
        whiteUsername.put(game.gameID(), game.whiteUser());
        blackUsername.put(game.gameID(), game.blackUser());
        gameName.put(game.gameID(), game.gameName());
        gameObjects.put(game.gameID(), game.game());
    }

    /**
     * removes a single Game_Record object from the list of all game
     *
     * @param gameID                gameID fo the game object to be removed
     * @throws DataAccessException  if the object does not exist
     */
    public void removeGame(int gameID) throws DataAccessException{
        if(!whiteUsername.containsKey(gameID))
            throw new DataAccessException("Game does not exist");

        this.gameID.remove(gameID);
        whiteUsername.remove(gameID);
        blackUsername.remove(gameID);
        gameName.remove(gameID);
        gameObjects.remove(gameID);
        gameObservers.remove(gameID);
    }

    /**
     * Completely removes all Games
     */
    public void clearAllGames(){
        gameID.clear();
        whiteUsername.clear();
        blackUsername.clear();
        gameName.clear();
        gameObjects.clear();
        gameObservers.clear();
    }

    public boolean findGameID(int gameID){ return whiteUsername.containsKey(gameID); }

    public void setWhiteUsername(int gameID, String username){
        whiteUsername.replace(gameID, username);
    }
    public void setBlackUsername(int gameID, String username){
        blackUsername.replace(gameID, username);
    }
    public void addObserver(int gameID, String username){
        observers.add(username);
        gameObservers.replace(gameID, observers);
    }
    public void joinGame(ChessGame.TeamColor color, int gameID, String username){
        switch (color) {
            case WHITE -> setWhiteUsername(gameID, username);
            case BLACK -> setBlackUsername(gameID, username);
        }
    }

    public Set<Integer> getKeys(){ return gameObjects.keySet(); }
    public String getWhiteUsername(int gameID){ return whiteUsername.get(gameID); }
    public String getBlackUsername(int gameID){ return blackUsername.get(gameID); }
    public String getGameName(int gameID){ return gameName.get(gameID); }

}
