package service;

import dataAccess.*;
import request.*;
import response.*;

/**
 * Logins the user to their account so they can access the game and make moves
 */
public class Login {
    /**
     * Attempts to login the user. Checks if the user is in the database and logins accordingly
     *
     * @param request               the request token used to attempt to login
     * @return                      returns a response of success or failure
     */
    public Response login(Login_Req request, Database db){
        RegisterLogin_Resp response = new RegisterLogin_Resp();

        // If any of the fields are null, it is a bad request
        if(request.getUsername() == null | request.getPassword() == null){
            response.setCode(500);
            response.setMessage("Error: bad request");
            return response;
        }

        // Get data from request
        String username = request.getUsername();
        String password = request.getPassword();
        User_Record temp = db.findUser(username);

        // If there is not a user registered or the passwords don't match, it is unauthorized
        if(temp == null || !(temp.password().equals(password))){
            response.setCode(401);
            response.setMessage("Error: unauthorized");
            return response;
        }

//        // If the user is already logged in, it is invalid
//        if(db.findUserAuth(username)){
//            response.setCode(500);
//            response.setMessage("Error: bad request");
//            return response;
//        }

        // Logic for logging in
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