package passoffTests.serviceTests;

import dataAccess.Auth_DAO;
import dataAccess.Game_DAO;
import dataAccess.User_DAO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dataAccess.Database;
import response.*;
import service.*;
import java.sql.Connection;

public class ClearApplicationTest {
    private Connection connection;
    private Database db;

    @Test
    @DisplayName("Clear Application")
    public void clearSuccess() {
        // Create and clear database
        db = new Database();
        try {
            connection = db.getConnection();
            ClearApplication clearService = new ClearApplication();
            clearService.clearApp(connection);

            Assertions.assertEquals((new User_DAO(connection)).getUserSize(), 0, "Clear application (user) was not successful");
            Assertions.assertEquals((new Auth_DAO(connection)).getAuthSize(), 0, "Clear application (auth) was not successful");
            Assertions.assertEquals((new Game_DAO(connection)).getGameSize(), 0, "Clear application (game) was not successful");
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
