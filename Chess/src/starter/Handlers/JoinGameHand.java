package Handlers;

import com.google.gson.Gson;
import dataAccess.Database;
import request.JoinGame_Req;
import response.Response;
import service.JoinGame;

import java.sql.Connection;

public class JoinGameHand {
    public static String handle(spark.Request request, spark.Response response) {
        System.out.println("Join Game Handler");

        Gson gson = new Gson();
        String temp = request.body();
        JoinGame_Req join = gson.fromJson(temp, JoinGame_Req.class);
        String authToken = request.headers("Authorization");
        join.setAuthToken(authToken);

        Database db = new Database();
        try {
            Connection connection = db.getConnection();
            JoinGame service = new JoinGame();
            Response resp = service.joinGame(join, connection);
            response.status(resp.getCode());

            return gson.toJson(resp);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
