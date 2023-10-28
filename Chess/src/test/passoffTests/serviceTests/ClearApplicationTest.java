package passoffTests.serviceTests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dataAccess.Database;
import response.*;
import service.*;

public class ClearApplicationTest {
    private Database db;

    @Test
    @DisplayName("Clear Application")
    public void clearSuccess() {
        // Create and clear database
        db = new Database();
        ClearApplication clearService = new ClearApplication();
        clearService.clearApp(db);

        Assertions.assertTrue(db.getUserDB().getPasswords().isEmpty(), "Clear application (user) was not successful");
        Assertions.assertTrue(db.getAuthDB().getAllAuth().isEmpty(), "Clear application (auth) was not successful");
        Assertions.assertTrue(db.getGameDB().getGameObjects().isEmpty(), "Clear application (game) was not successful");
    }
}
