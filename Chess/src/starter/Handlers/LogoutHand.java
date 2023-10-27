package Handlers;

import com.google.gson.Gson;
import dataAccess.Database;
import response.Response;
import service.Logout;

public class LogoutHand {
    public static String handle(spark.Request request, spark.Response response) {
        System.out.println("Logout Handler");

        Gson gson = new Gson();
        String authToken = request.headers("Authorization");

        Database db = new Database();

        Logout service = new Logout();
        Response resp = service.logout(authToken, db);
        response.status(resp.getCode());

        return gson.toJson(resp);
    }
}
