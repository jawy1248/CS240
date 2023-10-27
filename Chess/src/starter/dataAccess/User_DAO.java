package dataAccess;

import java.util.HashMap;

/**
 * The database for storing all the users' information. Stores all data
 */
public class User_DAO {
    /**
     * Private variables include the passwords and emails of the users
     * where the username is the unique ID used to identify
     */
    private HashMap<String, String> passwords = new HashMap<String, String>();
    private HashMap<String, String> emails = new HashMap<String, String>();

    /**
     * adds a user to the database
     *
     * @param user                      user data to be added
     */
    public void addUser(User_Record user){
        passwords.put(user.username(), user.password());
        emails.put(user.username(), user.email());
    }

    /**
     * removes a user from the database
     *
     * @param userName                  username of the user to be removed
     * @throws DataAccessException      if the user info is invalid or already exists
     */
    public void removeUser(String userName) throws DataAccessException{
        passwords.remove(userName);
        emails.remove(userName);
    }

    /**
     * Completely removes all Users
     */
    public void clearAllUsers(){
        passwords.clear();
        emails.clear();
    }

    public User_Record getUserData(String userName){
        if(!passwords.containsKey(userName))
            return null;

        return new User_Record(userName, passwords.get(userName), emails.get(userName));
    }
}
