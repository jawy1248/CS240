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
    public Response clearApp(Connection connection){

        User_DAO userDB = new User_DAO(connection);
        Auth_DAO authDB = new Auth_DAO(connection);
        Game_DAO gameDB = new Game_DAO(connection);

        // Logic for clearing the database
        try {
            authDB.clearAuthDB();
            userDB.clearUserDB();
            gameDB.clearGameDB();
        } catch(DataAccessException e){
            return new Failure_Resp();
        }

        // Success Response
        return new Success_Resp();
    }
}
