package service;

import dataAccess.*;
import response.*;

/**
 * Clears the application
 */
public class ClearApplication {
    /**
     * Clears the application
     *
     * @return has no return. Always returns null
     */
    public Response clearApp(Database db){
        // If there is no database, throw an error
        if(db == null)
            return new Failure_Resp();

        // Logic for clearing the database
        db.clearAll();

        // Success Response
        return new Success_Resp();
    }
}
