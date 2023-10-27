package service;

import chess.ChessGame;
import chess.Game;
import dataAccess.*;
import request.*;
import response.*;

/**
 * Creates a game if successful, otherwise throws an error message
 */
public class CreateGame {
    /**
     * Takes in a Create Game Request and returns a response based on success/failure
     *
     * @param request   object to request a game be created
     * @return          returns a success or error message response
     */
    public Response createGame(CreateGame_Req request, Database db){
        CreateGame_Resp response = new CreateGame_Resp();
        String gameName = request.getGameName();
        String authToken = request.getAuthToken();

        // Checking if the name is nothing (bad request)
        if(gameName == null){
            response.setCode(400);
            response.setMessage("Error: bad request");
            return response;
        }

        // Checking if authToken is invalid
        if(authToken == null || !db.findAuthToken(authToken)) {
            response.setCode(401);
            response.setMessage("Error: unauthorized");
            return response;
        }

        // Logic to create a game
        int gameID = db.createGameID();
        ChessGame game = new Game();
        Game_Record gameRecord = new Game_Record(gameID, null, null, gameName, game);
        db.createGame(gameRecord);

        // Response
        response.setGameID(gameID);
        response.setCode(200);
        response.setSuccess(true);
        return response;
    }
}
