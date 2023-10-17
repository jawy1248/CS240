package request;

/**
 * Is a request to login. This request contains
 * the username and password of the user
 */
public class Login_Req {
    /**
     * Two private variables for this class include the username and the
     * password. Each of which are strings and hold the corresponding information
     */
    private String username;
    private String password;

    /**
     * Constructor - sets the username and password of the user
     *
     * @param user  username of user
     * @param pass  password of user
     */
    public Login_Req(String user, String pass){
        username = user;
        password = pass;
    }

    public String getUsername(){
        return username;
    }

    public String getPassword(){
        return password;
    }
}
