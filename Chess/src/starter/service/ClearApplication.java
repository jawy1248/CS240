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
     * @return  has no return. Always returns null
     */
    public Response clearApp(Database db){
        db.clearAll();

        return new Success_Resp();
    }
}
