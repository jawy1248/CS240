package passoffTests.serviceTests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dataAccess.Database;
import request.*;
import response.*;
import service.*;

public class RegisterTest {
    private Database db;
    private final String username = "jawy1248";
    private final String password = "pi_3.14";
    private final String email = "something@email.com";

    @Test
    @DisplayName("Register +")
    public void registerSuccess() {
        // Create and clear database
        db = new Database();
        ClearApplication clearService = new ClearApplication();
        Response clearResp = clearService.clearApp(db);
        Assertions.assertEquals(clearResp.getCode(), 200, "Clear application was not successful");

        // Register a user
        Register_Req registerReq = new Register_Req(username, password, email);
        Register registerService = new Register();
        RegisterLogin_Resp registerResp = (RegisterLogin_Resp) registerService.register(registerReq, db);

        Assertions.assertNotNull(registerResp, "Response was null");
        Assertions.assertEquals(registerResp.getCode(), 200, "Response code was not 200");
    }

    @Test
    @DisplayName("Register -")
    public void registerFailure() {
        db = new Database();
        ClearApplication clearService = new ClearApplication();
        Response clearResp = clearService.clearApp(db);
        Assertions.assertEquals(clearResp.getCode(), 200, "Clear application was not successful");

        // Register a user
        Register_Req registerReq = new Register_Req(username, password, email);
        Register registerService = new Register();
        Response temp = registerService.register(registerReq, db);
        Assertions.assertEquals(temp.getCode(), 200, "First register was not success");

        // Try to register second time, same username
        RegisterLogin_Resp registerResp = (RegisterLogin_Resp) registerService.register(registerReq, db);

        Assertions.assertNotNull(registerResp, "Response was null");
        Assertions.assertEquals(registerResp.getCode(), 403, "Response code was not 403");
    }
}
