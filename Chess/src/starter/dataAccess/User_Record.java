package dataAccess;

/**
 * Creates a User object
 * @param username String of unique username of the user
 * @param password String password of the user
 * @param email String of email of the user
 */
public record User_Record(String username, String password, String email) {}