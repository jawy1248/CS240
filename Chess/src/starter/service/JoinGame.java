package service;

import chess.ChessGame;
import static chess.ChessGame.TeamColor.*;
import dataAccess.*;
import request.*;
import response.*;

public class JoinGame {
    public Response joinGame(JoinGame_Req request, Database db){
        Failure_Resp responseBad = new Failure_Resp();
        Success_Resp response = new Success_Resp();

        int gameID = request.getGameId();
        ChessGame.TeamColor color = request.getPlayerColor();
        String authToken = request.getAuthToken();

        // Checking if the gameID is invalid
        if( !db.findGameID(gameID) || !(color == null || color == WHITE || color == BLACK) ){
            responseBad.setCode(400);
            responseBad.setMessage("Error: bad request");
            return responseBad;
        }

        // Checking if authToken is invalid
        if(authToken == null || !db.findAuthToken(authToken)) {
            responseBad.setCode(401);
            responseBad.setMessage("Error: unauthorized");
            return responseBad;
        }

        // Logic to join a game
        String username = db.getUsername(authToken);
        if(color != null) {
            // Checking if color was already taken
            boolean temp = db.findGameColor(color, gameID);
            if(temp){
                responseBad.setCode(403);
                responseBad.setMessage("Error: already taken");
                return responseBad;
            }else
                db.joinGame(color, gameID, username);
        }
        else
            db.observeGame(gameID, username);

        // Response
        response.setCode(200);
        response.setSuccess(true);
        return response;
    }
}
