package Handlers;

import com.google.gson.Gson;
import dataAccess.Database;
import request.Login_Req;
import response.Response;
import service.Login;

public class LoginHand {
    public static String handle(spark.Request request, spark.Response response) {
        System.out.println("Login Handler");

        Gson gson = new Gson();
        String temp = request.body();
        Login_Req requested = gson.fromJson(temp, Login_Req.class);

        Database db = new Database();

        Login service = new Login();
        Response resp = service.login(requested, db);
        response.status(resp.getCode());

        return gson.toJson(resp);
    }
}
