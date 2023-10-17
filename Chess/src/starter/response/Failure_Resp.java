package response;

/**
 * Failure response of any request
 */
public class Failure_Resp implements Response{
    /**
     * Private variable message which includes the
     * general failure message
     */
    private String message;

    /**
     * Constructor - sets the message variable
     *
     * @param message   the failure message to be displayed
     */
    public Failure_Resp(String message){
        this.message = message;
    }


}
