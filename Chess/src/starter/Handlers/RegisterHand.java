package Handlers;

import com.google.gson.Gson;
import dataAccess.DataAccessException;
import request.Register_Req;
import response.*;
import service.Register;
import dataAccess.*;

public class RegisterHand {
    public static String handle(spark.Request request, spark.Response response) {
        System.out.println("Register Handler");

        Gson gson = new Gson();
        String temp = request.body();
        Register_Req requested = gson.fromJson(temp, Register_Req.class);
        User_DAO db = new User_DAO();
        Register service = new Register();
        Response resp = service.register(requested, db);
        temp = gson.toJson(resp);

        return temp;
    }
}
