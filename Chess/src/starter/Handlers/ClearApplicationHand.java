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

        Gson gson = new Gson();
        Database db = new Database();
        try {
            Connection connection = db.getConnection();
            ClearApplication service = new ClearApplication();
            Response resp = service.clearApp(connection);
            response.status(resp.getCode());

            db.returnConnection(connection);
            return gson.toJson(resp);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
