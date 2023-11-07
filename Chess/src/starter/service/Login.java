package service;

import dataAccess.*;
import request.*;
import response.*;
import java.sql.Connection;

public class Login {
    /**
     * Creates a login response after logging someone in
     * @param request login request
     * @param connection SQL connection
     * @return Response for HTTP
     */
    public Response login(Login_Req request, Connection connection){
        RegisterLogin_Resp response = new RegisterLogin_Resp();
        response.setSuccess(false);

        try{
            // Getting the user and auth DB
            User_DAO userDB = new User_DAO(connection);
            Auth_DAO authDB = new Auth_DAO(connection);

            // Getting data from request and such
            String username = request.getUsername();
            String password = request.getPassword();

            // If any of the fields are null, it is a bad request
            if(username == null || password == null){
                response.setCode(500);
                response.setMessage("Error: bad request");
                return response;
            }

            // If no user exists, passwords don't match, or has no authToken, they're unauthorized
            User_Record user = userDB.findUser(username);
            if(user == null || !(user.password().equals(password))){
                response.setCode(401);
                response.setMessage("Error: unauthorized");
                return response;
            }

            // Success Response
            String auth = authDB.makeAuth(username);
            response.setCode(200);
            response.setUsername(username);
            response.setAuthToken(auth);
            response.setSuccess(true);
            return response;

        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}