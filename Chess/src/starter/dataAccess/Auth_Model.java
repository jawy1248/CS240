package dataAccess;

/**
 * An individual authentication information object
 */
public class Auth_Model {
    /**
     * Private variabels include the authentication token and the username of the user being accessed
     */
    private String username;
    private String authToken;

    /**
     * Constructor - sets the username and authToken for this user
     *
     * @param username  String object that is the username of the player
     * @param authToken String object that is the authToken of the player
     */
    public Auth_Model(String username, String authToken){
        this.username = username;
        this.authToken = authToken;
    }

    public String getUsername(){
        return username;
    }

    public String getAuthToken(){
        return authToken;
    }
}
