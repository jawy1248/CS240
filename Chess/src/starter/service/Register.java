package service;

import com.google.gson.Gson;
import dataAccess.*;
import request.*;
import response.*;
import spark.Spark;

/**
 * Registers and creates a user in the database
 */
public class Register {
    /**
     * Creates and saves a user into the database
     *
     * @param registerReq   Request information for the user to create a profile
     * @return              User information if success, error message otherwise
     */
    public Response register(Register_Req registerReq){

        // Make register request in Json
        String reqData = new Gson().toJson(registerReq);

        Spark.post("/user", (request, response) -> "Hello");
        return null;
    }
}
