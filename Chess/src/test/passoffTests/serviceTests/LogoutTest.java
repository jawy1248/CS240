package passoffTests.serviceTests;

import dataAccess.Auth_DAO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dataAccess.Database;
import request.*;
import response.*;
import service.*;

import java.sql.Connection;

public class LogoutTest {
    private Database db;
    private Connection connection;
    private final String username = "jawy1248";
    private final String password = "pi_3.14";
    private final String email = "something@email.com";

    @Test
    @DisplayName("Logout +")
    public void logoutSuccess() {
        // Create and clear database
        db = new Database();
        try {
            connection = db.getConnection();
            ClearApplication clearService = new ClearApplication();
            Response clearResp = clearService.clearApp(connection);
            Assertions.assertEquals(clearResp.getCode(), 200, "Clear application was not successful");

            // Register a user
            Register_Req registerReq = new Register_Req(username, password, email);
            Register registerService = new Register();
            RegisterLogin_Resp registerResp = (RegisterLogin_Resp) registerService.register(registerReq, connection);
            Assertions.assertEquals(registerResp.getCode(), 200, "Registration was not successful");

            // Logout from registered account
            Logout logoutService = new Logout();
            logoutService.logout(registerResp.getAuthToken(), connection);

            Assertions.assertNull((new Auth_DAO(connection)).findAuthWithUsername(username), "Logout was unsuccessful");
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("Logout -")
    public void logoutFailure() {
        // Create and clear database
        db = new Database();
        try {
            connection = db.getConnection();
            ClearApplication clearService = new ClearApplication();
            Response clearResp = clearService.clearApp(connection);
            Assertions.assertEquals(clearResp.getCode(), 200, "Clear application was not successful");

            // Register a user
            Register_Req registerReq = new Register_Req(username, password, email);
            Register registerService = new Register();
            RegisterLogin_Resp registerResp = (RegisterLogin_Resp) registerService.register(registerReq, connection);
            Assertions.assertEquals(registerResp.getCode(), 200, "Registration was not successful");

            // Logout from registered account
            Logout logoutService = new Logout();
            Response temp = logoutService.logout(registerResp.getAuthToken(), connection);
            Assertions.assertEquals(temp.getCode(), 200, "First logout was not successful");

            // Try to logout again
            Response logoutResp = logoutService.logout(registerResp.getAuthToken(), connection);

            Assertions.assertNotNull(logoutResp, "Response was null");
            Assertions.assertEquals(logoutResp.getCode(), 401, "Code was not 401");
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
