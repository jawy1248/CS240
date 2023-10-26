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
     * @param requested   Request information for the user to create a profile
     * @return              User information if success, error message otherwise
     */
    public Response register(Register_Req requested, User_DAO db){
        String user = requested.getUsername();
        String pass = requested.getPassword();
        String email = requested.getEmail();
        User_Record userRecord = new User_Record(user, pass, email);

        try{
            db.addUser(userRecord);
        }catch(DataAccessException e){
//            return e;
        }


        return null;
    }
}
