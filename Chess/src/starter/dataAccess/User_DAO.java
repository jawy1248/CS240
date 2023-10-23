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
     * @throws DataAccessException      if the user info is invalid or already exists
     */
    public void addUser(User_Record user) throws DataAccessException{
        if(passwords.containsKey(user.username()))
            throw new DataAccessException("User already exists");

        passwords.put(user.username(), user.password());
        emails.put(user.username(), user.email());
    }

    /**
     * removes a user from the database
     *
     * @param userName                  username of the user to be removed
     * @throws DataAccessException      if the user info is invalid or already exists
     */
    public void removeUser(String userName) throws DataAccessException {
        if(!passwords.containsKey(userName))
            throw new DataAccessException("User does not exist");

        passwords.remove(userName);
        emails.remove(userName);
    }

    public User_Record getUserData(String userName) throws DataAccessException {
        if(!passwords.containsKey(userName))
            throw new DataAccessException("User does not exist");

        return new User_Record(userName, passwords.get(userName), emails.get(userName));
    }
}
