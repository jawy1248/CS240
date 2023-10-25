package dataAccess;

import chess.ChessGame;

import java.util.HashMap;

/**
 * Holds the information of all the current live games
 */
public class Game_DAO {
    /**
     * Private variables include the whiteUsername, blackUsername,
     * gameName, and game object where gameID is the unique ID
     */
    private static HashMap<Integer, String> whiteUsernames = new HashMap<Integer, String>();
    private static HashMap<Integer, String> blackUsernames = new HashMap<Integer, String>();
    private static HashMap<Integer, String> gameNames = new HashMap<Integer, String>();
    private static HashMap<Integer, ChessGame> gameObjects = new HashMap<Integer, ChessGame>();

    /**
     * adds a single Game_Record object to the list of all games
     *
     * @param game                  the Game_Record object that hold all the info
     * @throws DataAccessException  for invalid addition of game
     */
    public void addGame(Game_Record game) throws DataAccessException{
        if(whiteUsernames.containsKey(game.gameID()))
            throw new DataAccessException("Game already exists");
        if(whiteUsernames.containsValue(game.whiteUser()) || blackUsernames.containsValue(game.whiteUser()))
            throw new DataAccessException("Player 'White' is already in a game");
        if(whiteUsernames.containsValue(game.blackUser()) || blackUsernames.containsValue(game.blackUser()))
            throw new DataAccessException("Player 'Black' is already in a game");

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

    public Game_Record getGameData(int gameID) throws DataAccessException{
        if(!whiteUsernames.containsKey(gameID))
            throw new DataAccessException("Game does not exist");

        return new Game_Record(gameID, whiteUsernames.get(gameID), blackUsernames.get(gameID), gameNames.get(gameID), gameObjects.get(gameID));
    }
}
