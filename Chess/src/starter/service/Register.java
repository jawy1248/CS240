package service;

import dataAccess.*;
import request.*;
import response.*;
import java.sql.Connection;

/**
 * Registers and creates a user in the database
 */
public class Register {
    /**
     * Creates and saves a user into the database
     *
     * @param request Request information for the user to create a profile
     * @param connection Connection to the database
     * @return User information if success, error message otherwise
     * @throws DataAccessException error accessing the data
     */
    public Response register(Register_Req request, Connection connection) throws DataAccessException{

        User_DAO userDAO = new User_DAO(connection);
        Auth_DAO authDAO = new Auth_DAO(connection);

        RegisterLogin_Resp response = new RegisterLogin_Resp();
        response.setSuccess(false);

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
        if(userDAO.findUser(username) != null){
            response.setCode(403);
            response.setMessage("Error: already taken");
            return response;
        }
        try{
            User_Record user = new User_Record(username, password, email);
            userDAO.addUser(user);
        } catch(DataAccessException e){
            throw new RuntimeException(e);
        }

        String auth = authDAO.makeAuth(username);

        // Success Response
        response.setCode(200);
        response.setUsername(username);
        response.setAuthToken(auth);
        response.setSuccess(true);
        return response;
    }
}
