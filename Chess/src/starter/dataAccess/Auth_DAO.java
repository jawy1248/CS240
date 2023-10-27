package dataAccess;

import java.util.HashMap;
import java.util.Iterator;

/**
 * The object that hold all the logged-in users and their corresponding AuthTokens
 */
public class Auth_DAO {

    /**
     * Hashmap that links each user to their AuthToken
     */
    private HashMap<String, String> Auth = new HashMap<>();

    /**
     * Adds a AuthToken to the database
     * @param user Auth_Record object to be added
     */
    public void addAuth(Auth_Record user){
        Auth.put(user.username(), user.authToken());
    }

    /**
     * Removes a AuthToken from the database
     * @param username Username of associated authToken to be removed
     */
    public void removeAuth(String username){
        Auth.remove(username);
    }

    /**
     * Finds a username for a specific AuthToken
     * @param authToken String of the AuthToken
     * @return String of the associated username
     */
    public String getUsername(String authToken) {
        // Create an iterator to look through each of the keys
        Iterator<String> usernames = Auth.keySet().iterator();
        String username = null;
        String auth;
        while(usernames.hasNext()){
            username = usernames.next();
            auth = Auth.get(username);
            // If the value is found, break out
            if(auth.equals(authToken))
                break;
        }
        // Return the key of the Token found, or null if not found
        return username;
    }

    /**
     * Searches to see if a AuthToken exists
     * @param authToken String of AuthToken to find
     * @return true if found, false otherwise
     */
    public boolean findAuthToken(String authToken) {return Auth.containsValue(authToken); }

    /**
     * Completely removes all AuthTokens from database
     */
    public void clearAllAuth(){
        Auth.clear();
    }

}
