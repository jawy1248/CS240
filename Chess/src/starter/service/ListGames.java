package service;

import dataAccess.*;
import response.*;
import java.util.HashSet;
import java.sql.Connection;

public class ListGames {
    public Response listGames(String authToken, Connection connection) {
        ListGames_Resp response = new ListGames_Resp();
        response.setSuccess(false);

        try{
            // Getting the game and auth DB
            Game_DAO gameDB = new Game_DAO(connection);
            Auth_DAO authDB = new Auth_DAO(connection);

            Auth_Record auth = authDB.findAuth(authToken);

            if(auth == null) {
                response.setCode(401);
                response.setMessage("Error: unauthorized");
                return response;
            }

            response.setGames(gameDB.findAllGames());
            response.setCode(200);
            response.setSuccess(true);
            return response;

        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }
}
