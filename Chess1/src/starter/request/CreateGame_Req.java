package request;

/**
 * Is a request to create a game. This includes the game name
 */
public class CreateGame_Req {
    /**
     * gameName is the String referencing the name of the game being called
     */
    private String gameName;

    /**
     * Constructor - sets the name of the game
     *
     * @param name  the name of the game being played/stored
     */
    CreateGame_Req(String name){
        gameName = name;
    }

    public String getGameName(){
        return gameName;
    }
}
