package service;

import dataAccess.*;
import response.*;
import java.sql.Connection;

/**
 * Logouts the user from the server and from the game
 */
public class Logout {
    /**
     * Logs out the user from the server/game
     *
     * @return  No return, always null
     */
    public Response logout(String authToken, Connection connection){
        Failure_Resp responseBad = new Failure_Resp();
        Success_Resp response = new Success_Resp();

        try{
            // Getting auth DB
            Auth_DAO authDB = new Auth_DAO(connection);
            Auth_Record auth = authDB.findAuth(authToken);

            // If there is no authToken, they are unauthorized
            if(auth == null){
                responseBad.setCode(401);
                responseBad.setMessage("Error: unauthorized");
                return responseBad;
            }

            // Logout and return success response
            authDB.removeAuth(authToken);
            response.setCode(200);
            response.setSuccess(true);
            return response;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
