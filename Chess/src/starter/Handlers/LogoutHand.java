package Handlers;

import com.google.gson.Gson;
import dataAccess.Database;
import response.Response;
import service.Logout;

import java.sql.Connection;

public class LogoutHand {
    public static String handle(spark.Request request, spark.Response response) {
        System.out.println("Logout Handler");

        Gson gson = new Gson();
        String authToken = request.headers("Authorization");

        Database db = new Database();
        try {
            Connection connection = db.getConnection();
            Logout service = new Logout();
            Response resp = service.logout(authToken, connection);
            response.status(resp.getCode());

            return gson.toJson(resp);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
