package passoffTests.serviceTests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dataAccess.Database;
import request.*;
import response.*;
import service.*;

public class ListGamesTest {
    private Database db;
    private final String username = "jawy1248";
    private final String password = "pi_3.14";
    private final String email = "something@email.com";
    private final String[] gameName = {"game1", "game2", "game3", "game4"};

    @Test
    @DisplayName("List Games +")
    public void listSuccess() {
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

        // Create the games
        CreateGame_Req createReq1 = new CreateGame_Req(gameName[0], registerResp.getAuthToken());
        CreateGame_Req createReq2 = new CreateGame_Req(gameName[1], registerResp.getAuthToken());
        CreateGame_Req createReq3 = new CreateGame_Req(gameName[2], registerResp.getAuthToken());
        CreateGame_Req createReq4 = new CreateGame_Req(gameName[3], registerResp.getAuthToken());
        CreateGame createService = new CreateGame();
        CreateGame_Resp createResp1 = (CreateGame_Resp) createService.createGame(createReq1, db);
        CreateGame_Resp createResp2 = (CreateGame_Resp) createService.createGame(createReq2, db);
        CreateGame_Resp createResp3 = (CreateGame_Resp) createService.createGame(createReq3, db);
        CreateGame_Resp createResp4 = (CreateGame_Resp) createService.createGame(createReq4, db);
        Assertions.assertEquals(createResp1.getCode(), 200, "Game 1 add not successful");
        Assertions.assertEquals(createResp2.getCode(), 200, "Game 2 add not successful");
        Assertions.assertEquals(createResp3.getCode(), 200, "Game 3 add not successful");
        Assertions.assertEquals(createResp4.getCode(), 200, "Game 4 add not successful");

        // List the games
        ListGames listService = new ListGames();
        ListGames_Resp listResp = (ListGames_Resp) listService.listGames(registerResp.getAuthToken(), db);

        Assertions.assertEquals(db.getGameDB().getGameNames().size(), 4, "Number of games does not match");
    }

    @Test
    @DisplayName("List Games -")
    public void listFailure() {
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

        // Create the games
        CreateGame_Req createReq1 = new CreateGame_Req(gameName[0], registerResp.getAuthToken());
        CreateGame_Req createReq2 = new CreateGame_Req(gameName[1], registerResp.getAuthToken());
        CreateGame_Req createReq3 = new CreateGame_Req(gameName[2], registerResp.getAuthToken());
        CreateGame_Req createReq4 = new CreateGame_Req(gameName[3], registerResp.getAuthToken());
        CreateGame createService = new CreateGame();
        CreateGame_Resp createResp1 = (CreateGame_Resp) createService.createGame(createReq1, db);
        CreateGame_Resp createResp2 = (CreateGame_Resp) createService.createGame(createReq2, db);
        CreateGame_Resp createResp3 = (CreateGame_Resp) createService.createGame(createReq3, db);
        CreateGame_Resp createResp4 = (CreateGame_Resp) createService.createGame(createReq4, db);
        Assertions.assertEquals(createResp1.getCode(), 200, "Game 1 add not successful");
        Assertions.assertEquals(createResp2.getCode(), 200, "Game 2 add not successful");
        Assertions.assertEquals(createResp3.getCode(), 200, "Game 3 add not successful");
        Assertions.assertEquals(createResp4.getCode(), 200, "Game 4 add not successful");

        // List the games
        ListGames listService = new ListGames();
        ListGames_Resp listResp = (ListGames_Resp) listService.listGames(db.createAuthToken(), db);

        Assertions.assertNotNull(listResp, "Response was null");
        Assertions.assertEquals(listResp.getCode(), 401, "Code was not 401");
    }
}
