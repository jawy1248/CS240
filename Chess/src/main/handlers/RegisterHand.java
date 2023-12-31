package handlers;

import com.google.gson.Gson;
import request.Register_Req;
import response.Response;
import service.Register;
import dataAccess.*;

import java.sql.Connection;

/**
 * Handler for the Register application
 */
public class RegisterHand {
    public static String handle(spark.Request request, spark.Response response) {
        Gson gson = new Gson();
        String temp = request.body();
        Register_Req requested = gson.fromJson(temp, Register_Req.class);

        // Get database and connection to SQL
        Database db = new Database();
        try {
            Connection connection = db.getConnection();
            Register service = new Register();
            Response resp = service.register(requested, connection);
            response.status(resp.getCode());

            db.returnConnection(connection);
            return gson.toJson(resp);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
