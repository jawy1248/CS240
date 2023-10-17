package response;

/**
 * Successful response of the Login Request or the Register Request
 */
public class RegisterLogin_Resp implements Response{
    /**
     * Private strings representing the username and authToken of the user
     */
    private String username;
    private String authToken;

    /**
     * Constructor - sets the username and authToken
     *
     * @param username  the username of the user playing
     * @param authToken the authToken of the user playing
     */
    public RegisterLogin_Resp(String username, String authToken){
        this.username = username;
        this.authToken = authToken;
    }
}
