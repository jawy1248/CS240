package model;

import chess.ChessGame;

/**
 * Creates a single Game object
 * @param gameID int of unique gameID of the game being played
 * @param whiteUsername String of username of the player that is playing white
 * @param blackUsername String of username of the player that is playing black
 * @param gameName String of the name of the game being played
 * @param game ChessGame object of the game that is being played
 */
public class Game_Record {
    private Integer gameID;
    private String whiteUsername;
    private String blackUsername;
    private String gameName;
    private ChessGame game;
    public Game_Record(Integer gameID, String whiteUsername, String blackUsername, String gameName, ChessGame game){
        this.gameID = gameID;
        this.whiteUsername = whiteUsername;
        this.blackUsername = blackUsername;
        this.gameName = gameName;
        this.game = game;
    }
    // Setters
    public void setGameID(int gameID){this.gameID = gameID;}
    public void setWhiteUsername(String whiteUsername){this.whiteUsername = whiteUsername;}
    public void setBlackUsername(String blackUsername){this.blackUsername = blackUsername;}
    public void setGameName(String gameName){this.gameName = gameName;}
    public void setGame(ChessGame game){this.game = game;}

    // Getters
    public Integer gameID(){return this.gameID;}
    public String whiteUsername(){return this.whiteUsername;}
    public String blackUsername(){return this.blackUsername;}
    public String gameName(){return this.gameName;}
    public ChessGame game(){return this.game;}
}