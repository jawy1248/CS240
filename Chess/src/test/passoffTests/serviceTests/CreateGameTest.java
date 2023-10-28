package passoffTests.serviceTests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dataAccess.Database;
import request.*;
import response.*;
import service.*;

public class CreateGameTest {
    private Database db;
    private final String username = "jawy1248";
    private final String password = "pi_3.14";
    private final String email = "something@email.com";
    private final String gameName = "crazyCooks";

    @Test
    @DisplayName("Create Game +")
    public void createSuccess() {
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

        Assertions.assertNotNull(createResp, "Response was null");
        Assertions.assertEquals(createResp.getCode(), 200, "Code was not 200");
    }

    @Test
    @DisplayName("Create Game -")
    public void createFailure() {
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

        // Create the game with wrong AuthToken
        CreateGame_Req createReq = new CreateGame_Req(gameName, db.createAuthToken());
        CreateGame createService = new CreateGame();
        CreateGame_Resp createResp = (CreateGame_Resp) createService.createGame(createReq, db);

        Assertions.assertNotNull(createResp, "Response was null");
        Assertions.assertEquals(createResp.getCode(), 401, "Code was not 401");
    }
}
