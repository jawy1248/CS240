package dataAccess;

import java.util.HashMap;

/**
 * Is a database for all the user authentication tokens.
 */
public class Auth_DAO {
    private static HashMap<String, String> Auth = new HashMap<>();

    public void addAuth(Auth_Record user){
        Auth.put(user.username(), user.authToken());
    }
    public void removeAuth(String username){
        Auth.remove(username);
    }
    public String getAuthToken(String userName){
        return Auth.get(userName);
    }
    public boolean findAuthToken(String authToken) {return Auth.containsValue(authToken); }
    public void clearAllAuth(){
        Auth.clear();
    }

}
