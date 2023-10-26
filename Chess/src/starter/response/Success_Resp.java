package response;

public class Success_Resp implements Response{
    private int code = 200;
    private String message = null;

    public int getCode(){return code;}
    public String getMessage(){return message;}
}
