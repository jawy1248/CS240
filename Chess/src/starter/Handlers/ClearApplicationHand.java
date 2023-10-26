package Handlers;

import com.google.gson.Gson;
import request.Register_Req;
import response.Response;
import service.ClearApplication;
import service.Register;

import dataAccess.*;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.sql. Connection;

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
