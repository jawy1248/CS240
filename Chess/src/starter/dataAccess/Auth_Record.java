package dataAccess;

/**
 * Creates a single authentication token object. Holds the following variables
 *
 * @param username      unique username of the user
 * @param authToken     authentication token of the user
 */
public record Auth_Record(String username, String authToken) {}