package dataAccess;

import model.User_Record;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Data access object for the user database
 */
public class User_DAO {
    /**
     * Connection to the SQL database
     */
    private final Connection connection;

    /**
     * Constructor for setting the connection
     * @param connection Connection to the SQL database
     */
    public User_DAO(Connection connection){ this.connection = connection; }

    /**
     * Creates the database if not already made
     * @throws SQLException SQL error
     */
    private void makeUserDB() throws SQLException {
        String sqlReq =
                """
                    CREATE TABLE  IF NOT EXISTS user (
                        username VARCHAR(255) NOT NULL,
                        password VARCHAR(255) NOT NULL,
                        email VARCHAR(255) NOT NULL,
                        PRIMARY KEY (username)
                    )
                """;
        try (PreparedStatement req = connection.prepareStatement(sqlReq)) {
            req.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error occurred making AuthDB");
        }
    }

    /**
     * Clears all users from the database
     * @throws DataAccessException data access error
     */
    public void clearUserDB() throws DataAccessException{
        // Make sure the database exists
        try{
            makeUserDB();
        } catch(SQLException e){
            throw new DataAccessException(e.toString());
        }

        // SQL instructions for clearing all users
        String sqlReq = "DELETE from user;";
        try (PreparedStatement req = connection.prepareStatement(sqlReq)) {
            req.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error occurred clearing UserDB");
        }
    }

    /**
     * Adds a single user to the database
     * @param user User_Record of person being added
     * @throws DataAccessException data access error
     */
    public void addUser(User_Record user) throws DataAccessException{
        // Make sure the database exists
        try{
            makeUserDB();
        } catch(SQLException e){
            throw new DataAccessException(e.toString());
        }

        // SQL instructions for adding a single user
        String sqlReq = "INSERT INTO user (username, password, email) VALUES(?,?,?);";
        try(PreparedStatement req = connection.prepareStatement(sqlReq)){
            req.setString(1, user.username());
            req.setString(2, user.password());
            req.setString(3, user.email());
            req.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error occurred adding user");
        }
    }

    /**
     * Finds a user in the database from a username
     * @param username String of username to be added
     * @return User_Record object or null if it does not exist
     * @throws DataAccessException data access error
     */
    public User_Record findUser(String username) throws DataAccessException {
        // Make sure the database exists
        try{
            makeUserDB();
        } catch(SQLException e){
            throw new DataAccessException(e.toString());
        }

        User_Record temp;
        ResultSet results;

        // SQL instructions for finding user base on their username
        String sqlReq = "SELECT * FROM user WHERE username = ?;";
        try(PreparedStatement req = connection.prepareStatement(sqlReq)){
            req.setString(1, username);
            results = req.executeQuery();
            if (results.next()){
                temp = new User_Record(results.getString("username"), results.getString("password"), results.getString("email"));
                return temp;
            } else
                return null;
        } catch (SQLException e) {
            throw new DataAccessException("Error accessing user");
        }
    }

    /**
     * Counts the number of users in the database
     * @return number of users in database
     */
    public int getUserSize() {
        // Make sure the database exists
        try{
            makeUserDB();
        } catch(SQLException e){
            throw new RuntimeException(e);
        }

        // SQL instructions for counting users in database
        ResultSet results;
        String sqlReq = "SELECT COUNT(*) AS row_count FROM user;";
        int count=0;
        try (PreparedStatement req = connection.prepareStatement(sqlReq)) {
            results = req.executeQuery();
            if (results.next()) {
                count = results.getInt("row_count");
            }
            return count;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
