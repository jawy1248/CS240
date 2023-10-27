package dataAccess;

import java.util.HashMap;
import java.util.Iterator;

/**
 * Is a database for all the user authentication tokens.
 */
public class Auth_DAO {
    private HashMap<String, String> Auth = new HashMap<>();

    public void addAuth(Auth_Record user){
        Auth.put(user.username(), user.authToken());
    }
    public void removeAuth(String username){
        Auth.remove(username);
    }
    public String getAuthToken(String username){
        return Auth.get(username);
    }
    public String getUsername(String authToken) {
        Iterator<String> usernames = Auth.keySet().iterator();
        String username = null;
        String auth;
        while(usernames.hasNext()){
            username = usernames.next();
            auth = Auth.get(username);
            if(auth.equals(authToken))
                break;
        }
        return username;
    }
    public boolean findAuthToken(String authToken) {return Auth.containsValue(authToken); }
    public boolean findUsername(String username) {return Auth.containsKey(username); }
    public void clearAllAuth(){
        Auth.clear();
    }

}
