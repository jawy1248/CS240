package Handlers;

import com.google.gson.Gson;
import response.Response;
import service.ClearApplication;
import dataAccess.Database;

/**
 * The handler for the clear application
 */
public class ClearApplicationHand {
    public static String handle(spark.Request request, spark.Response response){
        System.out.println("Clear Handler");

        Gson gson = new Gson();
        Database db = new Database();
        ClearApplication service = new ClearApplication();
        Response resp = service.clearApp(db);
        response.status(resp.getCode());

        return gson.toJson(resp);
    }
}
