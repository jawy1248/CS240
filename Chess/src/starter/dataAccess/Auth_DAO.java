package dataAccess;

import java.util.Map;

/**
 * Is a database for all the user authentication tokens.
 */
public class Auth_DAO {
    /**
     * Private variable that links each authToken to each username
     */
    private Map<String, String> Auth;

    /**
     * Adds a user to the database
     *
     * @param user                  the users information to be added
     * @throws DataAccessException  the error thrown if a user is in the database, or information is invalid
     */
    public void addAuth(Auth_Model user) throws DataAccessException {
    }

    /**
     * Removes a user from the database
     *
     * @param user                  the users information to be removed
     * @throws DataAccessException  the error thrown if a user is in the database, or information is invalid
     */
    public void removeAuth(Auth_Model user) throws DataAccessException {
    }

    public Map<String, String> getAuth(Auth_Model user) throws DataAccessException {
        return null;
    }
}
