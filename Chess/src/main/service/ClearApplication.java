package service;

import dataAccess.*;
import response.*;
import java.sql.Connection;

/**
 * Clears the application
 */
public class ClearApplication {
    /**
     * Clears the application
     *
     * @return returns the response
     */
    public String clearApp(Connection connection){

        try {
            // Getting all the databases
            User_DAO userDB = new User_DAO(connection);
            Auth_DAO authDB = new Auth_DAO(connection);
            Game_DAO gameDB = new Game_DAO(connection);

            // Logic for clearing the databases
            authDB.clearAuthDB();
            userDB.clearUserDB();
            gameDB.clearGameDB();

        } catch(DataAccessException e){
            throw new RuntimeException(e);
        }

        // Response
        return "{}";
    }
}
