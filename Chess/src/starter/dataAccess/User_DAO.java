package dataAccess;

import java.util.HashMap;

/**
 * Database that holds all users
 */
public class User_DAO {
    /**
     * Private variables include passwords and emails where usernames is the unique ID
     */
    private HashMap<String, String> passwords = new HashMap<String, String>();
    private HashMap<String, String> emails = new HashMap<String, String>();

    /**
     * Finds a User in the userDB. Returns the whole record of the user
     * @param username String of the username to look for
     * @return User_Record object of the found user (or null if none are found)
     */
    public User_Record getUserData(String username){
        if(!passwords.containsKey(username))
            return null;

        return new User_Record(username, passwords.get(username), emails.get(username));
    }

    /**
     * Adds a user to the userDB
     * @param user User_Record object to be added to the DB
     */
    public void addUser(User_Record user){
        passwords.put(user.username(), user.password());
        emails.put(user.username(), user.email());
    }

    /**
     * Completely clears all the users from the database
     */
    public void clearAllUsers(){
        passwords.clear();
        emails.clear();
    }
}
