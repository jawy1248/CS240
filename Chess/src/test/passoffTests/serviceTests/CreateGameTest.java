package passoffTests.serviceTests;

import dataAccess.Auth_DAO;
import dataAccess.Game_DAO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dataAccess.Database;
import request.*;
import response.*;
import service.*;

import java.sql.Connection;
import java.util.UUID;

public class CreateGameTest {
    private Database db;
    private Connection connection;
    private final String username = "jawy1248";
    private final String password = "pi_3.14";
    private final String email = "something@email.com";
    private final String gameName = "crazyCooks";

    @Test
    @DisplayName("Create Game +")
    public void createSuccess() {
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
            createService.createGame(createReq, connection);

            Assertions.assertEquals((new Game_DAO(connection)).getGameSize(), 1, "Game did not get created");

        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("Create Game -")
    public void createFailure() {
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

            // Create the game with wrong AuthToken
            CreateGame_Req createReq = new CreateGame_Req(gameName, UUID.randomUUID().toString());
            CreateGame createService = new CreateGame();
            CreateGame_Resp createResp = (CreateGame_Resp) createService.createGame(createReq, connection);

            Assertions.assertNotNull(createResp, "Response was null");
            Assertions.assertEquals(createResp.getCode(), 401, "Code was not 401");

        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
