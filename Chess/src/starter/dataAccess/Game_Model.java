package dataAccess;

import chess.ChessGame;

/**
 * Is one object of a game, a single game in the database
 */
public class Game_Model {
    /**
     * Private variables include the gameID, white username, black username, game name, and game data
     */
    private int gameID;
    private String whiteUser;
    private String blackUser;
    private String gameName;
    private ChessGame game;

    /**
     * Constructor -  sets the private variables when called
     *
     * @param gameID        int representing the gameID used to find a game
     * @param whiteUser     String of the username of the player playing as white
     * @param blackUser     String of the username of the player playing as black
     * @param gameName      String of the textual name of the game set by players
     * @param game          ChessGame representation of the game being played and accessed
     */
    public Game_Model(int gameID, String whiteUser, String blackUser, String gameName, ChessGame game){
        this.gameID = gameID;
        this.whiteUser = whiteUser;
        this.blackUser = blackUser;
        this.gameName = gameName;
        this.game = game;
    }

    public int getGameID(){
        return gameID;
    }

    public String getWhiteUser(){
        return whiteUser;
    }

    public String getBlackUser(){
        return blackUser;
    }

    public String getGameName(){
        return gameName;
    }

    public ChessGame getGameModel(){
        return game;
    }
}
