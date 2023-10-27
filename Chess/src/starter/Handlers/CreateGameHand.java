package Handlers;

import com.google.gson.Gson;
import dataAccess.Database;
import request.CreateGame_Req;
import response.Response;
import service.CreateGame;

public class CreateGameHand {
    public static String handle(spark.Request request, spark.Response response) {
        System.out.println("Create Game Handler");

        Gson gson = new Gson();
        String temp = request.body();
        CreateGame_Req gameName = gson.fromJson(temp, CreateGame_Req.class);
        String authToken = request.headers("Authorization");
        gameName.setAuthToken(authToken);

        Database db = new Database();

        CreateGame service = new CreateGame();
        Response resp = service.createGame(gameName, db);

        return gson.toJson(resp);
    }
}
