package handlers;

import com.google.gson.Gson;
import dataAccess.Database;
import request.Login_Req;
import response.Response;
import service.Login;

import java.sql.Connection;

/**
 * Handler for the LoginHandler
 */
public class LoginHand {
    public static String handle(spark.Request request, spark.Response response) {
        System.out.println("Login Handler");

        Gson gson = new Gson();
        String temp = request.body();
        Login_Req requested = gson.fromJson(temp, Login_Req.class);

        // Get database and connection to SQL
        Database db = new Database();
        try {
            Connection connection = db.getConnection();
            Login service = new Login();
            Response resp = service.login(requested, connection);
            response.status(resp.getCode());

            db.returnConnection(connection);
            return gson.toJson(resp);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
