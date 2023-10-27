package Handlers;

import com.google.gson.Gson;
import request.Register_Req;
import response.Response;
import service.Register;
import dataAccess.*;

public class RegisterHand {
    public static String handle(spark.Request request, spark.Response response) {
        System.out.println("Register Handler");

        Gson gson = new Gson();
        String temp = request.body();
        Register_Req requested = gson.fromJson(temp, Register_Req.class);

        Database db = new Database();

        Register service = new Register();
        Response resp = service.register(requested, db);
        response.status(resp.getCode());

        return gson.toJson(resp);
    }
}
