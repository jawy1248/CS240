package handlers;

import com.google.gson.Gson;
import dataAccess.Database;
import request.CreateGame_Req;
import response.Response;
import service.CreateGame;

import java.sql.Connection;

/**
 * Handler for the CreateGame application
 */
public class CreateGameHand {
    public static String handle(spark.Request request, spark.Response response) {
        Gson gson = new Gson();
        String temp = request.body();
        CreateGame_Req gameName = gson.fromJson(temp, CreateGame_Req.class);
        String authToken = request.headers("Authorization");
        gameName.setAuthToken(authToken);

        // Get database and connection to SQL
        Database db = new Database();
        try {
            Connection connection = db.getConnection();
            CreateGame service = new CreateGame();
            Response resp = service.createGame(gameName, connection);
            response.status(resp.getCode());

            db.returnConnection(connection);
            return gson.toJson(resp);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
