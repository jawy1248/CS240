package service;

import dataAccess.*;
import request.*;
import response.*;

/**
 * Registers and creates a user in the database
 */
public class Register {
    /**
     * Creates and saves a user into the database
     *
     * @param request   Request information for the user to create a profile
     * @param db        Database
     * @return          User information if success, error message otherwise
     */
    public Response register(Register_Req request, Database db){
        RegisterLogin_Resp response = new RegisterLogin_Resp();

        // If any of the fields are null, it is a bad request
        if(request.getUsername() == null | request.getPassword() == null | request.getEmail() == null){
            response.setCode(400);
            response.setMessage("Error: bad request");
            return response;
        }

        // Get data from request
        String username = request.getUsername();
        String password = request.getPassword();
        String email = request.getEmail();

        // If the user is already registered, throw an error
        if(db.findUser(username) != null){
            response.setCode(403);
            response.setMessage("Error: already taken");
            return response;
        }

        // Logic for adding a user
        User_Record user = new User_Record(username, password, email);
        db.addUser(user);
        String auth = db.createAuthToken();
        db.addAuthToken(new Auth_Record(username, auth));

        // Success Response
        response.setCode(200);
        response.setUsername(username);
        response.setAuthToken(auth);
        response.setSuccess(true);
        return response;
    }
}
