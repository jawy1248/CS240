package dataAccess;

import chess.ChessGame;
import chess.Game;

import java.util.HashMap;
import java.util.HashSet;

/**
 * Holds the information of all the current live games
 */
public class Game_DAO {
    /**
     * Private variables include the whiteUsername, blackUsername,
     * gameName, and game object where gameID is the unique ID
     */
    private HashMap<Integer, String> whiteUsernames = new HashMap<>();
    private HashMap<Integer, String> blackUsernames = new HashMap<>();
    private HashMap<Integer, String> gameNames = new HashMap<>();
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
        whiteUsernames.put(game.gameID(), game.whiteUser());
        blackUsernames.put(game.gameID(), game.blackUser());
        gameNames.put(game.gameID(), game.gameName());
        gameObjects.put(game.gameID(), game.game());
    }

    /**
     * removes a single Game_Record object from the list of all game
     *
     * @param gameID                gameID fo the game object to be removed
     * @throws DataAccessException  if the object does not exist
     */
    public void removeGame(int gameID) throws DataAccessException{
        if(!whiteUsernames.containsKey(gameID))
            throw new DataAccessException("Game does not exist");

        whiteUsernames.remove(gameID);
        blackUsernames.remove(gameID);
        gameNames.remove(gameID);
        gameObjects.remove(gameID);
    }

    /**
     * Completely removes all Games
     */
    public void clearAllGames(){
        whiteUsernames.clear();
        blackUsernames.clear();
        gameNames.clear();
        gameObjects.clear();
    }

    public boolean findGameID(int gameID){ return whiteUsernames.containsKey(gameID); }

    public void setWhiteUsername(int gameID, String username){
        whiteUsernames.replace(gameID, username);
    }
    public void setBlackUsername(int gameID, String username){
        blackUsernames.replace(gameID, username);
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
    public HashSet<Game> listGames(){
        return null;
    }
}
