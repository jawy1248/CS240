package Handlers;

import com.google.gson.Gson;
import response.Response;
import service.ClearApplication;
import dataAccess.Database;
import java.sql.Connection;

/**
 * The handler for the clear application
 */
public class ClearApplicationHand {
    public static String handle(spark.Request request, spark.Response response){
        System.out.println("Clear Handler");

        // Get database and connection to SQL
        Database db = new Database();
        try {
            Connection connection = db.getConnection();
            ClearApplication service = new ClearApplication();
            String resp = service.clearApp(connection);

            db.returnConnection(connection);
            return resp;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
