package dataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Database that holds all users
 */
public class User_DAO {
    private final Connection connection;
    public User_DAO(Connection connection){ this.connection = connection; }

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

    public void clearUserDB() throws DataAccessException{
        try{
            makeUserDB();
        } catch(SQLException e){
            throw new DataAccessException(e.toString());
        }

        String sqlReq = "DELETE from user;";
        try (PreparedStatement req = connection.prepareStatement(sqlReq)) {
            req.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error occurred clearing UserDB");
        }
    }

    public void addUser(User_Record user) throws DataAccessException{
        try{
            makeUserDB();
        } catch(SQLException e){
            throw new DataAccessException(e.toString());
        }

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

    public User_Record findUser(String username) throws DataAccessException {
        try{
            makeUserDB();
        } catch(SQLException e){
            throw new DataAccessException(e.toString());
        }

        User_Record temp;
        ResultSet results;

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

    public int getUserSize() {
        try{
            makeUserDB();
        } catch(SQLException e){
            throw new RuntimeException(e);
        }

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
