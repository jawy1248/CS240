package dataAccess;

/**
 * Creates a single AuthToken object
 * @param username String of username of the user to be added
 * @param authToken String of AuthToken of the user to be added
 */
public record Auth_Record(String username, String authToken) {}