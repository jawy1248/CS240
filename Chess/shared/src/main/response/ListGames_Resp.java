package response;

import model.Game_Record;
import java.util.HashSet;

/**
 * Successful response of the List Games request
 */
public class ListGames_Resp implements Response{
    /**
     * Games object which hold all the games
     * should hold gameID, whiteUsername, blackUsername, and gameName
     */
    private HashSet<Game_Record> games;
    private int code;
    private String message;
    private boolean success;

    // ----- Setters -----
    public void setGames(HashSet<Game_Record> games) {
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
    public HashSet<Game_Record> getGames() {
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
