package passoffTests.serviceTests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dataAccess.Database;
import request.*;
import response.*;
import service.*;

import static chess.ChessGame.TeamColor.*;

public class JoinGameTest {
    private Database db;
    private final String username = "jawy1248";
    private final String password = "pi_3.14";
    private final String email = "something@email.com";
    private final String gameName = "crazyCooks";

    @Test
    @DisplayName("Join Game +")
    public void joinSuccess() {
        // Create and clear database
        db = new Database();
        ClearApplication clearService = new ClearApplication();
        Response clearResp = clearService.clearApp(db);
        Assertions.assertEquals(clearResp.getCode(), 200, "Database did not clear");

        // Register a user
        Register_Req registerReq = new Register_Req(username, password, email);
        Register registerService = new Register();
        RegisterLogin_Resp registerResp = (RegisterLogin_Resp) registerService.register(registerReq, db);
        Assertions.assertEquals(registerResp.getCode(), 200, "Register was not successful");

        // Create the game
        CreateGame_Req createReq = new CreateGame_Req(gameName, registerResp.getAuthToken());
        CreateGame createService = new CreateGame();
        CreateGame_Resp createResp = (CreateGame_Resp) createService.createGame(createReq, db);
        Assertions.assertEquals(createResp.getCode(), 200, "Create game was not successful");

        // Join the game
        JoinGame_Req joinReq = new JoinGame_Req(WHITE, createResp.getGameID());
        joinReq.setAuthToken(registerResp.getAuthToken());
        JoinGame joinService = new JoinGame();
        Response joinResp = joinService.joinGame(joinReq, db);

        Assertions.assertNotNull(joinResp, "Response was null");
        Assertions.assertEquals(joinResp.getCode(), 200, "Code was not 200");
    }

    @Test
    @DisplayName("Join Game -")
    public void joinFailure() {
        // Create and clear database
        db = new Database();
        ClearApplication clearService = new ClearApplication();
        Response clearResp = clearService.clearApp(db);
        Assertions.assertEquals(clearResp.getCode(), 200, "Database did not clear");

        // Register a user
        Register_Req registerReq = new Register_Req(username, password, email);
        Register registerService = new Register();
        RegisterLogin_Resp registerResp = (RegisterLogin_Resp) registerService.register(registerReq, db);
        Assertions.assertEquals(registerResp.getCode(), 200, "Register was not successful");

        // Create the game
        CreateGame_Req createReq = new CreateGame_Req(gameName, registerResp.getAuthToken());
        CreateGame createService = new CreateGame();
        CreateGame_Resp createResp = (CreateGame_Resp) createService.createGame(createReq, db);
        Assertions.assertEquals(createResp.getCode(), 200, "Create game was not successful");

        // Join the game
        JoinGame_Req joinReq = new JoinGame_Req(WHITE, createResp.getGameID());
        joinReq.setAuthToken(registerResp.getAuthToken());
        JoinGame joinService = new JoinGame();
        Response temp = joinService.joinGame(joinReq, db);
        Assertions.assertEquals(temp.getCode(), 200, "First join was not successful");

        // Try to join the same spot for the second time
        Response joinResp = joinService.joinGame(joinReq, db);

        Assertions.assertNotNull(joinResp, "Response was null");
        Assertions.assertEquals(joinResp.getCode(), 403, "Code was not 403");
    }
}
