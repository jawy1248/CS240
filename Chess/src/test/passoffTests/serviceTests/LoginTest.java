package passoffTests.serviceTests;

import dataAccess.User_DAO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dataAccess.Database;
import request.*;
import response.*;
import service.*;

import java.sql.Connection;

public class LoginTest {
    private Database db;
    private Connection connection;
    private final String username = "jawy1248";
    private final String password = "pi_3.14";
    private final String email = "something@email.com";

    @Test
    @DisplayName("Login +")
    public void loginSuccess() {
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
            Assertions.assertEquals(registerResp.getCode(), 200, "Registration was not successful");

            // Logout from registered account
            Logout logoutService = new Logout();
            Response logoutResp = logoutService.logout(registerResp.getAuthToken(), connection);
            Assertions.assertEquals(logoutResp.getCode(), 200, "Logout was not successful");

            // Login user
            Login_Req loginReq = new Login_Req(username, password);
            Login loginService = new Login();
            loginService.login(loginReq, connection);

            Assertions.assertNotNull((new User_DAO(connection)).findUser(username), "User is not logged in");
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("Login -")
    public void loginFailure() {
        String badPass = "WHATEVEN";

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
            Assertions.assertEquals(registerResp.getCode(), 200, "Registration was not successful");

            // Logout from registered account
            Logout logoutService = new Logout();
            Response logoutResp = logoutService.logout(registerResp.getAuthToken(), connection);
            Assertions.assertEquals(logoutResp.getCode(), 200, "Logout was not successful");

            // Login user
            Login_Req loginReq = new Login_Req(username, badPass);
            Login loginService = new Login();
            RegisterLogin_Resp loginResp = (RegisterLogin_Resp) loginService.login(loginReq, connection);

            Assertions.assertNotNull(loginResp, "Response was null");
            Assertions.assertEquals(loginResp.getCode(), 401, "Code was not 401");
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
