package dataAccess;

import java.util.Collection;
import java.util.UUID;

public class Database {
    /**
     * Private variables include the databases for the user, auth, and game
     */
    private static User_DAO userDB = new User_DAO();
    private static Auth_DAO authDB = new Auth_DAO();
    private static Game_DAO gameDB = new Game_DAO();

    public void clearAll(){
        userDB.clearAllUsers();
        authDB.clearAllAuth();
        gameDB.clearAllGames();
    }

    public User_Record findUser(String username){
        return userDB.getUserData(username);
    }

    public void addUser(User_Record user){
        userDB.addUser(user);
    }

    public String createAuthToken(){
        return UUID.randomUUID().toString();
    }
    public void addAuthToken(Auth_Record auth) { authDB.addAuth(auth); }
    public void removeAuthToken(String auth) { authDB.removeAuth(auth); }
    public String getAuthToken(String username) { return authDB.getAuthToken(username); }

    public boolean findAuthToken(String authToken) { return authDB.findAuthToken(authToken); }


}
