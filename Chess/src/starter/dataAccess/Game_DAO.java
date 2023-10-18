package dataAccess;

import javax.xml.crypto.Data;
import java.util.Map;

/**
 * Hold the information of all the current live games
 */
public class Game_DAO {
    /**
     * Variables include a map of the games to an array of game objects
     * including gameID, white username, black username, game name, and game object
     */
    private Map<String, Object[]> game;

    /**
     * adds a single game_model object to the list of all games
     *
     * @param game                  the Game_Model object that hold all the info
     * @throws DataAccessException  for invalid addition of game
     */
    public void addGame(Game_Model game) throws DataAccessException{
    }

    /**
     * removes a single game_model object from the list of all game
     *
     * @param game                  game object to be removed
     * @throws DataAccessException  if the object does not exist
     */
    public void removeGame(Game_Model game) throws DataAccessException{
    }

    public Map<String, Object[]> getGameDAO(Game_Model game){
        return null;
    }
}
