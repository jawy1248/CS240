package Handlers;

import com.google.gson.Gson;
import dataAccess.Database;
import response.Response;
import service.ListGames;

import java.sql.Connection;

public class ListGamesHand {
    public static String handle(spark.Request request, spark.Response response) {
        System.out.println("List Games Handler");

        Gson gson = new Gson();
        String authToken = request.headers("Authorization");

        Database db = new Database();
        try {
            Connection connection = db.getConnection();
            ListGames service = new ListGames();
            Response resp = service.listGames(authToken, connection);
            response.status(resp.getCode());

            db.returnConnection(connection);
            return gson.toJson(resp);
        } catch (Exception e){
            throw new RuntimeException(e);
        }

    }
}
