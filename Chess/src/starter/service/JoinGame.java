package service;

import chess.ChessGame;
import static chess.ChessGame.TeamColor.*;
import dataAccess.*;
import request.*;
import response.*;
import java.sql.Connection;

public class JoinGame {
    public Response joinGame(JoinGame_Req request, Connection connection){
        Failure_Resp responseBad = new Failure_Resp();
        Success_Resp response = new Success_Resp();

        try{
            // Getting the game and auth DB
            Game_DAO gameDB = new Game_DAO(connection);
            Auth_DAO authDB = new Auth_DAO(connection);

            // Getting values from request
            int gameID = request.getGameId();
            ChessGame.TeamColor color = request.getPlayerColor();
            String authToken = request.getAuthToken();

            Game_Record game = gameDB.findGame(gameID);
            Auth_Record auth = authDB.findAuth(authToken);

            // Checking if any necessary values are null
            if(game == null || authToken == null) {
                responseBad.setCode(400);
                responseBad.setMessage("Error: bad request");
                return responseBad;
            }

            // Checking the user is authorized
            if(auth == null) {
                responseBad.setCode(401);
                responseBad.setMessage("Error: unauthorized");
                return responseBad;
            }

            // Sorting to color or observer and checking if someone is in it
            if(color != null) {
                 String temp = switch (color) {
                    case WHITE -> game.whiteUsername();
                    case BLACK -> game.whiteUsername();
                };
                if (temp != null) {
                    responseBad.setCode(403);
                    responseBad.setMessage("Error: already taken");
                    return response;
                }
            } else {
                // If it is an observer, send success response
                response.setCode(200);
                response.setSuccess(true);
                return response;
            }

            // Join game and send success response
            gameDB.joinGame(color, gameID, auth.username());
            response.setCode(200);
            response.setSuccess(true);
            return response;

        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }
}
