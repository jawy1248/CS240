package response;

/**
 * Successful response of the Login Request or the Register Request
 */
public class RegisterLogin_Resp implements Response{
    /**
     * Private strings representing the username and authToken of the user
     */
    private String username;
    private String authToken;
    private int code;
    private String message;
    private boolean success;

    // ----- Setters -----
    public void setUsername(String username) {
        this.username = username;
    }
    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
    public void setCode(int code) {
        this.code = code;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public void setSuccess(boolean success) {
        this.success = success;
    }

    // ----- Getters -----
    public String getUsername() {
        return this.username;
    }
    public String getAuthToken() {
        return this.authToken;
    }
    public int getCode() {
        return this.code;
    }
    public String getMessage() {
        return this.message;
    }
    public boolean getSuccess() {
        return this.success;
    }
}
