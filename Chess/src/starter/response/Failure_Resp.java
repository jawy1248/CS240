package response;

/**
 * Failure response of any request
 */
public class Failure_Resp implements Response{
    private int code = 500;
    private String message;
    private boolean success;

    public int getCode(){return code;}
    public String getMessage(){return message;}
    public boolean getSuccess(){return success;}
    public void setMessage(String message) {this.message = message;}
    public void setCode(int code) {this.code = code;}
    public void setSuccess(boolean success){ this.success = success;}
}
