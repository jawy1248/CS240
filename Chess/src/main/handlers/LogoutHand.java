package handlers;

import com.google.gson.Gson;
import dataAccess.Database;
import response.Response;
import service.Logout;

import java.sql.Connection;

/**
 * Handler for the Logout applications
 */
public class LogoutHand {
    public static String handle(spark.Request request, spark.Response response) {
        Gson gson = new Gson();
        String authToken = request.headers("Authorization");

        // Get database and connection to SQL
        Database db = new Database();
        try {
            Connection connection = db.getConnection();
            Logout service = new Logout();
            Response resp = service.logout(authToken, connection);
            response.status(resp.getCode());

            db.returnConnection(connection);
            return gson.toJson(resp);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
