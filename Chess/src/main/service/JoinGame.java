package service;

import chess.ChessGame;
import dataAccess.*;
import model.Auth_Record;
import model.Game_Record;
import request.*;
import response.*;
import java.sql.Connection;

public class JoinGame {
    /**
     * Creates a join game response putting a player in a game
     * @param request joinGame request
     * @param connection SQL connection
     * @return Response for HTTP
     */
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
                    case BLACK -> game.blackUsername();
                };
                if (temp != null) {
                    responseBad.setCode(403);
                    responseBad.setMessage("Error: already taken");
                    return responseBad;
                }
            } else {
                // If it is an observer, send success response
                return response;
            }

            // Join game and send success response
            gameDB.joinGame(color, gameID, auth.username());
            return response;

        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }
}
