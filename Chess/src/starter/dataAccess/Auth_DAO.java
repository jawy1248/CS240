package dataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

/**
 * Data Access Object for the AuthToken database
 */
public class Auth_DAO {
    /**
     * Connection to the SQL database
     */
    private final Connection connection;

    /**
     * Constructor for setting the connection
     * @param connection Connection to the SQL database
     */
    public Auth_DAO(Connection connection){ this.connection = connection; }

    /**
     * Creates the database if not already made
     * @throws SQLException SQL error
     */
    private void makeAuthDB() throws SQLException {
        String sqlReq =
                """
                    CREATE TABLE  IF NOT EXISTS authToken (
                        authToken VARCHAR(255) NOT NULL,
                        username VARCHAR(255) NOT NULL,
                        PRIMARY KEY (authToken),
                        INDEX (username)
                    )
                """;
        try (PreparedStatement req = connection.prepareStatement(sqlReq)) {
            req.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error occurred making AuthDB");
        }
    }

    /**
     * Clears the authToken database
     * @throws DataAccessException Data access error
     */
    public void clearAuthDB() throws DataAccessException{
        // Make sure database exists
        try{
            makeAuthDB();
        } catch(SQLException e){
            throw new DataAccessException(e.toString());
        }

        // SQL instructions for clearing the database
        String sqlReq = "DELETE from authToken;";
        try (PreparedStatement req = connection.prepareStatement(sqlReq)) {
            req.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error occurred clearing AuthDB");
        }
    }

    /**
     * Adds an authToken and username to the data
     * @param username username of player being added
     * @return authToken created for that user
     * @throws DataAccessException data access error
     */
    public String makeAuth(String username) throws DataAccessException{
        // Make sure the database exists
        try{
            makeAuthDB();
        } catch(SQLException e){
            throw new DataAccessException(e.toString());
        }

        // Creating an authToken and the SQL instructions to add it
        String authT = UUID.randomUUID().toString();
        String sqlReq = "INSERT INTO authToken (authToken, username) VALUES(?,?);";
        try(PreparedStatement req = connection.prepareStatement(sqlReq)){
            req.setString(1, authT);
            req.setString(2, username);
            req.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error occurred adding AuthToken");
        }

        return authT;
    }

    /**
     * Removes an authToken and username from the database
     * @param authToken authToken to be removed
     * @throws DataAccessException data access error
     */
    public void removeAuth(String authToken) throws DataAccessException {
        // Make sure the database exists
        try{
            makeAuthDB();
        } catch(SQLException e){
            throw new DataAccessException(e.toString());
        }

        // SQL instructions to remove a single authToken
        String sqlReq = "DELETE FROM authToken WHERE authToken = ?;";
        try (PreparedStatement req = connection.prepareStatement(sqlReq)) {
            req.setString(1, authToken);
            req.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error occurred deleting AuthToken");
        }
    }

    /**
     * Finds an Auth_Record with the username provided
     * @param username of record to be found
     * @return Auth_Record or null if it doesn't exist
     * @throws DataAccessException Data access error
     */
    public Auth_Record findAuthWithUsername(String username) throws DataAccessException{
        // Make sure the database exists
        try{
            makeAuthDB();
        } catch(SQLException e){
            throw new DataAccessException(e.toString());
        }

        Auth_Record temp;
        ResultSet results;

        // SQL instructions for finding authTokens
        String sqlReq = "SELECT * FROM authToken WHERE username = ?;";
        try(PreparedStatement req = connection.prepareStatement(sqlReq)){
            req.setString(1, username);
            results = req.executeQuery();
            if (results.next()){
                temp = new Auth_Record(results.getString("username"), results.getString("authToken"));
                return temp;
            } else
                return null;
        } catch (SQLException e) {
            throw new DataAccessException("Error accessing authToken with username");
        }
    }

    /**
     * Finds an Auth_Record with the authToken provided
     * @param authToken of record to be found
     * @return Auth_Record or null if it doesn't exist
     */
    public Auth_Record findAuth(String authToken){
        // Make sure database exists
        try{
            makeAuthDB();
        } catch(SQLException e){
            throw new RuntimeException(e);
        }

        Auth_Record temp;
        ResultSet results;

        // SQL instructions for finding Record
        String sqlReq = "SELECT * FROM authToken WHERE authToken = ?;";
        try(PreparedStatement req = connection.prepareStatement(sqlReq)){
            req.setString(1, authToken);
            results = req.executeQuery();
            if (results.next()){
                temp = new Auth_Record(results.getString("username"), results.getString("authToken"));
                return temp;
            } else
                return null;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Gets the number of authToken records in the database
     * @return number of records in database
     */
    public int getAuthSize() {
        // Make sure the database exists
        try{
            makeAuthDB();
        } catch(SQLException e){
            throw new RuntimeException(e);
        }

        // SQL instructions for getting count
        ResultSet results;
        String sqlReq = "SELECT COUNT(*) AS row_count FROM AuthToken;";
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
