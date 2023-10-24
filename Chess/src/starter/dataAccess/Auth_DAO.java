package dataAccess;

import java.util.HashMap;

/**
 * Is a database for all the user authentication tokens.
 */
public class Auth_DAO {
    /**
     * Private variable that links each authToken to each username
     */
    private HashMap<String, String> Auth;

    /**
     * Adds a user to the database
     *
     * @param user                  the users information to be added
     * @throws DataAccessException  the error thrown if a user is in the database, or information is invalid
     */
    public void addAuth(Auth_Record user) throws DataAccessException {
        if(Auth.containsKey(user.username()))
            throw new DataAccessException("User already exists");

        Auth.put(user.username(), user.authToken());
    }

    /**
     * Removes a user from the database
     *
     * @param userName              Username of the user to be removed
     * @throws DataAccessException  the error thrown if a user is in the database, or information is invalid
     */
    public void removeAuth(String userName) throws DataAccessException {
        if(Auth.containsKey(userName))
            throw new DataAccessException("User does not exists");

        Auth.remove(userName);
    }

    /**
     * Completely removes all Auth tokens
     */
    public void clearAllAuth(){
        Auth.clear();
    }

    public Auth_Record getAuthData(String userName) throws DataAccessException {
        if(Auth.containsKey(userName))
            throw new DataAccessException("User does not exists");

        return new Auth_Record(userName, Auth.get(userName));
    }
}
