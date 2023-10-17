package request;

/**
 * Is a request to create a new account. This request includes
 * the username, password, and email of the user signing up
 */
public class Register_Req {
    /**
     * Private variables for this class includes the username,
     * password, and email associated with the account being created
     */
    private String username;
    private String password;
    private String email;

    /**
     * Constructor - sets the username, password, and email of the user. Registers them.
     *
     * @param user  the username of the user
     * @param pass  the password of the user
     * @param email the email of the user
     */
    public Register_Req(String user, String pass, String email){
        username = user;
        password = pass;
        this.email = email;
    }

    public String getUsername(){
        return username;
    }

    public String getPassword(){
        return password;
    }

    public String getEmail(){
        return email;
    }
}
