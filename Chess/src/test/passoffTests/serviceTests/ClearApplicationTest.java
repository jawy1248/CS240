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
        Response clearResp = clearService.clearApp(db);

        Assertions.assertEquals(clearResp.getCode(), 200, "Clear application was not successful");
    }
}
