package response;

/**
 * Interface for all Responses. All logic in
 * other functions, this is a general catch, all return all
 */
public interface Response {
    int getCode();
    String getMessage();
}