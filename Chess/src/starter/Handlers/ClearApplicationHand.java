package Handlers;

import com.google.gson.Gson;
import response.Response;
import service.ClearApplication;

import dataAccess.*;

public class ClearApplicationHand {
    public static String handle(spark.Request request, spark.Response response){
        System.out.println("Clear Handler");

        Gson gson = new Gson();
        Database db = new Database();
        ClearApplication service = new ClearApplication();
        Response resp = service.clearApp(db);

        return gson.toJson(resp);
    }
}
