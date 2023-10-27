package response;

import chess.Game;

import java.util.HashSet;

/**
 * Successful response of the List Games request
 */
public class ListGames_Resp implements Response{
    /**
     * Games object which hold all the games
     * should hold gameID, whiteUsername, blackUsername, and gameName
     */
    private HashSet<Game> games;
    private int code;
    private String message;
    private boolean success;

    // ----- Setters -----
    public void setGames(HashSet<Game> games) {
        this.games = games;
    }
    public void setCode(int code) {
        this.code = code;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public void setSuccess(boolean success) {
        this.success = success;
    }

    // ----- Getters -----
    public HashSet<Game> getGames() {
        return this.games;
    }
    public int getCode() {
        return this.code;
    }
    public String getMessage() {
        return this.message;
    }
    public boolean getSuccess() {
        return this.success;
    }
}
