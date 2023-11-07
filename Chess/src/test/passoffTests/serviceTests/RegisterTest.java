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

public class RegisterTest {
    private Database db;
    private Connection connection;
    private final String username = "jawy1248";
    private final String password = "pi_3.14";
    private final String email = "something@email.com";

    @Test
    @DisplayName("Register +")
    public void registerSuccess() {
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

            Assertions.assertEquals((new User_DAO(connection)).findUser(username).username(), username, "User is not logged in");
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("Register -")
    public void registerFailure() {
        db = new Database();
        try {
            connection = db.getConnection();
            ClearApplication clearService = new ClearApplication();
            Response clearResp = clearService.clearApp(connection);
            Assertions.assertEquals(clearResp.getCode(), 200, "Clear application was not successful");

            // Register a user
            Register_Req registerReq = new Register_Req(username, password, email);
            Register registerService = new Register();
            Response temp = registerService.register(registerReq, connection);
            Assertions.assertEquals(temp.getCode(), 200, "First register was not success");

            // Try to register second time, same username
            RegisterLogin_Resp registerResp = (RegisterLogin_Resp) registerService.register(registerReq, connection);

            Assertions.assertNotNull(registerResp, "Response was null");
            Assertions.assertEquals(registerResp.getCode(), 403, "Response code was not 403");
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
