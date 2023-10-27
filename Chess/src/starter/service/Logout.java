package service;

import dataAccess.*;
import request.*;
import response.*;

/**
 * Logouts the user from the server and from the game
 */
public class Logout {
    /**
     * Logs out the user from the server/game
     *
     * @return  No return, always null
     */
    public Response logout(String authToken, Database db){
        Failure_Resp responseBad = new Failure_Resp();
        Success_Resp response = new Success_Resp();

        // If the authToken is not in the db, it is unauthorized
        if(!db.findAuthToken(authToken)){
            responseBad.setCode(401);
            responseBad.setMessage("Error: unauthorized");
            return responseBad;
        }

        // Logic for logging out
        String username = db.getUsername(authToken);
        db.removeAuthToken(username);

        // Success Response
        response.setCode(200);
        response.setSuccess(true);
        return response;
    }
}
