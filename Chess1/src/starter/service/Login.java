package service;

import dataAccess.*;
import request.*;
import response.*;

public class Login {
    /**
     * Attempts to login the user. Checks if the user is in the database and logins accordingly
     *
     * @param loginReq              the request token used to attempt to login
     * @return                      returns a response of success or failure
     * @throws DataAccessException  if the user is not in the database
     */
    public Response login(Login_Req loginReq) throws DataAccessException {
        return null;
    }
}