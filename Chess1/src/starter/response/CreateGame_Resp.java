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

    /**
     * Constructor - sets the gameID variable
     *
     * @param gameID    is the id for the game being played
     */
    public CreateGame_Resp(int gameID){
        this.gameID = gameID;
    }
}
