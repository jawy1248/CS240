package passoffTests.serviceTests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dataAccess.Database;
import request.*;
import response.*;
import service.*;

public class LoginTest {
    private Database db;
    private final String username = "jawy1248";
    private final String password = "pi_3.14";
    private final String email = "something@email.com";

    @Test
    @DisplayName("Login +")
    public void loginSuccess() {
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
        Assertions.assertEquals(logoutResp.getCode(), 200, "Logout was not successful");

        // Login user
        Login_Req loginReq = new Login_Req(username, password);
        Login loginService = new Login();
        RegisterLogin_Resp loginResp = (RegisterLogin_Resp) loginService.login(loginReq, db);

        Assertions.assertNotNull(loginResp, "Response was null");
        Assertions.assertEquals(loginResp.getCode(), 200, "Code was not 200");
    }

    @Test
    @DisplayName("Login -")
    public void loginFailure() {
        String badPass = "WHATEVEN";

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
        Assertions.assertEquals(logoutResp.getCode(), 200, "Logout was not successful");

        // Login user
        Login_Req loginReq = new Login_Req(username, badPass);
        Login loginService = new Login();
        RegisterLogin_Resp loginResp = (RegisterLogin_Resp) loginService.login(loginReq, db);

        Assertions.assertNotNull(loginResp, "Response was null");
        Assertions.assertEquals(loginResp.getCode(), 401, "Code was not 401");
    }
}
