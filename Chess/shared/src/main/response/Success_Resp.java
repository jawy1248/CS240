package response;

public class Success_Resp implements Response{
    private int code = 200;
    private String message;
    private boolean success = true;

    public int getCode(){return code;}
    public String getMessage(){return message;}
    public boolean getSuccess(){return success;}
    public void setMessage(String message) {this.message = message;}

}
