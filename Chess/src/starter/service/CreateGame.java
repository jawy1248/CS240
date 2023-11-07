package service;

import chess.Board;
import chess.ChessBoard;
import chess.ChessGame;
import chess.Game;
import dataAccess.*;
import request.*;
import response.*;
import java.sql.Connection;

public class CreateGame {
    /**
     * Creates a createGame response creating a game
     * @param request createGame request
     * @param connection SQL connection
     * @return Response for HTTP
     */
    public Response createGame(CreateGame_Req request, Connection connection){
        // Initializing the response and gameID
        CreateGame_Resp response = new CreateGame_Resp();
        response.setSuccess(false);
        String gameName = request.getGameName();
        String authToken = request.getAuthToken();

        try{
            // Getting the game and auth DB
            Game_DAO gameDB = new Game_DAO(connection);
            Auth_DAO authDB = new Auth_DAO(connection);

            // Checking if authToken is invalid (unauthorized)
            if (authDB.findAuth(authToken) == null){
                response.setCode(401);
                response.setMessage("Error: unauthorized");
                return response;
            }

            // Checking if the name is nothing (bad request)
            if(gameName == null){
                response.setCode(400);
                response.setMessage("Error: bad request");
                return response;
            }

            // Creating the board
            ChessBoard board = new Board();
            board.resetBoard();
            ChessGame game = new Game();
            game.setBoard(board);

            // Adding the board
            Game_Record gameRecord = new Game_Record(null, null, null, gameName, game);
            int gameID = gameDB.addGame(gameRecord);

            // Response
            response.setGameID(gameID);
            response.setCode(200);
            response.setSuccess(true);
            return response;

        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }
}
