package Handlers;

import com.google.gson.Gson;
import dataAccess.Database;
import response.Response;
import service.ListGames;

public class ListGamesHand {
    public static String handle(spark.Request request, spark.Response response) {
        System.out.println("List Games Handler");

        Gson gson = new Gson();
        String authToken = request.headers("Authorization");

        Database db = new Database();

        ListGames service = new ListGames();
        Response resp = service.listGames(authToken, db);
        response.status(resp.getCode());

        return gson.toJson(resp);
    }
}
