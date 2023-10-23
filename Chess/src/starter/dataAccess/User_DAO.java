package dataAccess;

import java.util.ArrayList;

/**
 * The database for storing all the users' information. Stores all data
 */
public class User_DAO {
    /**
     * Private string arrayList were each list item is an array itself
     * the first item is username, then password, the email
     */
    private ArrayList<ArrayList<String>> users = new ArrayList<ArrayList<String>>();

    /**
     * adds a user to the database
     *
     * @param user                      user to be added
     * @throws DataAccessException      if the user info is invalid or already exists
     */
    public void addUser(User_Record user) throws DataAccessException{
    }

    /**
     * removes a user from the database
     *
     * @param user                      user to be removed
     * @throws DataAccessException      if the user info is invalid or already exists
     */
    public void removeUser(User_Record user) throws DataAccessException {
    }

    public ArrayList<String> getUserDAO(User_Record user) throws DataAccessException{
        return null;
    }
}
