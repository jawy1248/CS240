package request;

import chess.ChessGame;

/**
 * Is a request to join a game. This includes the game ID
 * and the player color of the user joining
 */
public class JoinGame_Req {
    /**
     * Two private variables, playerColor and gameID
     * these two variables store the color of the user joining
     * and the ID of the game being joined
     */
    private ChessGame.TeamColor playerColor;
    private int gameID;

    /**
     * Constructor - sets player color and game ID
     *
     * @param playerColor   Color of the user
     * @param gameID        ID of the game being played
     */
    public JoinGame_Req(ChessGame.TeamColor playerColor, int gameID){
        this.playerColor = playerColor;
        this.gameID = gameID;
    }

    public ChessGame.TeamColor getPlayerColor(){
        return playerColor;
    }

    public int getGameId(){
        return gameID;
    }
}
