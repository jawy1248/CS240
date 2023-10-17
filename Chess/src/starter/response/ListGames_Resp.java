package response;

/**
 * Successful response of the List Games request
 */
public class ListGames_Resp implements Response{
    /**
     * Games object which hold all the games
     * should hold gameID, whiteUsername, blackUsername, and gameName
     */
    private Object[] games;

    /**
     * Constructor - sets the games array
     *
     * @param gamesArray    the games array which includes
     *                      gameID, whiteUsername, blackUsername, and gameName
     */
    public ListGames_Resp(Object[] gamesArray){
        games = gamesArray;
    }
}
