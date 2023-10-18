package dataAccess;

/**
 * Is the model of the user database. Represents one line of User_DAO
 */
public class User_Model {
    /**
     * Private variables for this class include the username, password, and email
     * of a single user
     */
    private String username;
    private String password;
    private String email;

    /**
     * Constructor - sets the username, password, and email of the user
     *
     * @param username  username of the user adding
     * @param password  password of the user adding
     * @param email     email of the user adding
     */
    public User_Model(String username, String password, String email){
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public String getUsername(){
        return username;
    }

    public String getPassword(){
        return password;
    }

    public String getEmail() {
        return email;
    }
}
