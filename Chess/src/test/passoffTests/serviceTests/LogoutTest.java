package passoffTests.serviceTests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dataAccess.Database;
import request.*;
import response.*;
import service.*;

public class LogoutTest {
    private Database db;
    private final String username = "jawy1248";
    private final String password = "pi_3.14";
    private final String email = "something@email.com";

    @Test
    @DisplayName("Logout +")
    public void logoutSuccess() {
        // Create and clear database
        db = new Database();
        ClearApplication clearService = new ClearApplication();
        Response clearResp = clearService.clearApp(db);
        Assertions.assertEquals(clearResp.getCode(), 200, "Clear application was not successful");

        // Register a user
        Register_Req registerReq = new Register_Req(username, password, email);
        Register registerService = new Register();
        RegisterLogin_Resp registerResp = (RegisterLogin_Resp) registerService.register(registerReq, db);
        Assertions.assertEquals(registerResp.getCode(), 200, "Registration was not successful");

        // Logout from registered account
        Logout logoutService = new Logout();
        Response logoutResp = logoutService.logout(registerResp.getAuthToken(), db);

        Assertions.assertNull(db.getAuthDB().getUsername(registerResp.getAuthToken()), "Logout was unsuccessful");
    }

    @Test
    @DisplayName("Logout -")
    public void logoutFailure() {
        // Create and clear database
        db = new Database();
        ClearApplication clearService = new ClearApplication();
        Response clearResp = clearService.clearApp(db);
        Assertions.assertEquals(clearResp.getCode(), 200, "Clear application was not successful");

        // Register a user
        Register_Req registerReq = new Register_Req(username, password, email);
        Register registerService = new Register();
        RegisterLogin_Resp registerResp = (RegisterLogin_Resp) registerService.register(registerReq, db);
        Assertions.assertEquals(registerResp.getCode(), 200, "Registration was not successful");

        // Logout from registered account
        Logout logoutService = new Logout();
        Response temp = logoutService.logout(registerResp.getAuthToken(), db);
        Assertions.assertEquals(temp.getCode(), 200, "First logout was not successful");

        // Try to logout again
        Response logoutResp = logoutService.logout(registerResp.getAuthToken(), db);

        Assertions.assertNotNull(logoutResp, "Response was null");
        Assertions.assertEquals(logoutResp.getCode(), 401, "Code was not 401");
    }
}
