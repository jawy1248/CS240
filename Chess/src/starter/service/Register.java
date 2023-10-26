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
        User_DAO userDB = new User_DAO();
        Auth_DAO authDB = new Auth_DAO();
        RegisterLogin_Resp response = new RegisterLogin_Resp();

        if(request.getUsername() == null | request.getPassword() == null | request.getEmail() == null){
            response.setCode(400);
            response.setMessage("Error: bad request");
            return response;
        }

        String username = request.getUsername();
        String password = request.getPassword();
        String email = request.getEmail();

        if(userDB.getUserData(username) != null){
            response.setCode(403);
            response.setMessage("Error: already taken");
            return response;
        }
        try{
            User_Record user = new User_Record(username, password, email);
            userDB.addUser(user);
        }catch(DataAccessException e){
            throw new RuntimeException(e);
        }
        String auth = authDB.makeAuth(username);
        response.setCode(200);
        response.setUsername(username);
        response.setAuthToken(auth);
        return response;
    }
}
