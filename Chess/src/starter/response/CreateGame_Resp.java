package response;

/**
 * Successful response of the Create Game request
 */
public class CreateGame_Resp implements Response{
    /**
     * Private variable of the gameID. Will be an int of
     * the id of the game
     */
    private int gameID;
    private int code;
    private String message;
    private boolean success;

    // Setters
    public void setGameID(int gameID){ this.gameID = gameID; }
    public void setCode(int code) { this.code = code; }
    public void setMessage(String message) { this.message = message; }
    public void setSuccess(boolean success) { this.success = success; }

    // Getters
    public int getGameID(){ return this.gameID; }
    public int getCode() { return this.code; }
    public String getMessage() { return this.message; }
    public boolean getSuccess() { return this.success; }


}
