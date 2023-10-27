package service;

import dataAccess.*;
import request.*;
import response.*;

import java.util.Collection;

/**
 * Lists the games on the server(memory)
 */
public class ListGames {
    /**
     * Lists the games in the Game DAO
     *
     * @return  A list of games in the database if successful, or a failure message otherwise
     */
    public Response listGames(String authToken, Database db) {
        ListGames_Resp response = new ListGames_Resp();

        // If any fields are null, it is a bad request
        if (authToken == null) {
            response.setCode(500);
            response.setMessage("Error: bad request");
            return response;
        }

        // If the user is unauthorized (not logged in)
        if (!db.findAuthToken(authToken)) {
            response.setCode(401);
            response.setMessage("Error: unauthorized");
            return response;
        }

        // Logic for listing games


        // Success Response
        response.setCode(200);
        response.setSuccess(true);
        return response;
    }
}
