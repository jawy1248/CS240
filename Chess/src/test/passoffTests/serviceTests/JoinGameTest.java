package passoffTests.serviceTests;

import dataAccess.Game_DAO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dataAccess.Database;
import request.*;
import response.*;
import service.*;
import java.sql.Connection;

import static chess.ChessGame.TeamColor.*;

public class JoinGameTest {
    private Database db;
    private Connection connection;
    private final String username = "jawy1248";
    private final String password = "pi_3.14";
    private final String email = "something@email.com";
    private final String gameName = "crazyCooks";

    @Test
    @DisplayName("Join Game +")
    public void joinSuccess() {
        // Create and clear database
        db = new Database();
        try {
            connection = db.getConnection();

            ClearApplication clearService = new ClearApplication();
            clearService.clearApp(connection);

            // Register a user
            Register_Req registerReq = new Register_Req(username, password, email);
            Register registerService = new Register();
            RegisterLogin_Resp registerResp = (RegisterLogin_Resp) registerService.register(registerReq, connection);
            Assertions.assertEquals(registerResp.getCode(), 200, "Register was not successful");

            // Create the game
            CreateGame_Req createReq = new CreateGame_Req(gameName, registerResp.getAuthToken());
            CreateGame createService = new CreateGame();
            CreateGame_Resp createResp = (CreateGame_Resp) createService.createGame(createReq, connection);
            Assertions.assertEquals(createResp.getCode(), 200, "Create game was not successful");

            // Join the game
            JoinGame_Req joinReq = new JoinGame_Req(WHITE, createResp.getGameID());
            joinReq.setAuthToken(registerResp.getAuthToken());
            JoinGame joinService = new JoinGame();
            joinService.joinGame(joinReq, connection);

            Assertions.assertEquals((new Game_DAO(connection)).findGame(createResp.getGameID()).whiteUsername(), username, "White username did not match");
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("Join Game -")
    public void joinFailure() {
        // Create and clear database
        db = new Database();
        try {
            connection = db.getConnection();
            ClearApplication clearService = new ClearApplication();
            clearService.clearApp(connection);

            // Register a user
            Register_Req registerReq = new Register_Req(username, password, email);
            Register registerService = new Register();
            RegisterLogin_Resp registerResp = (RegisterLogin_Resp) registerService.register(registerReq, connection);
            Assertions.assertEquals(registerResp.getCode(), 200, "Register was not successful");

            // Create the game
            CreateGame_Req createReq = new CreateGame_Req(gameName, registerResp.getAuthToken());
            CreateGame createService = new CreateGame();
            CreateGame_Resp createResp = (CreateGame_Resp) createService.createGame(createReq, connection);
            Assertions.assertEquals(createResp.getCode(), 200, "Create game was not successful");

            // Join the game
            JoinGame_Req joinReq = new JoinGame_Req(WHITE, createResp.getGameID());
            joinReq.setAuthToken(registerResp.getAuthToken());
            JoinGame joinService = new JoinGame();
            Response temp = joinService.joinGame(joinReq, connection);
            Assertions.assertEquals(temp.getCode(), 200, "First join was not successful");

            // Try to join the same spot for the second time
            Response joinResp = joinService.joinGame(joinReq, connection);

            Assertions.assertNotNull(joinResp, "Response was null");
            Assertions.assertEquals(joinResp.getCode(), 403, "Code was not 403");
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
