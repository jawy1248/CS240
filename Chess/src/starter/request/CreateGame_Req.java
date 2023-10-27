package request;

/**
 * Is a request to create a game. This includes the game name
 */
public class CreateGame_Req implements Request {
    /**
     * gameName is the String referencing the name of the game being called
     */
    private String gameName;
    private String authToken;

    /**
     * Constructor - sets the name of the game
     *
     * @param gameName  the name of the game being played/stored
     * @param authToken authToken of the player trying to create a game
     */
    CreateGame_Req(String gameName, String authToken){
        this.gameName = gameName;
        this.authToken = authToken;
    }

    public String getGameName(){
        return gameName;
    }
    public String getAuthToken(){
        return authToken;
    }
    public void setAuthToken(String authToken){
        this.authToken = authToken;
    }
}
