package dataAccess;

/**
 * Creates a single user model object. Holds the following variables
 *
 * @param username  unique username of the user
 * @param password  password of the user
 * @param email     email of the user
 */
public record User_Record(String username, String password, String email) {}