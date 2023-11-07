package dataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

/**
 * The object that hold all the logged-in users and their corresponding AuthTokens
 */
public class Auth_DAO {
    private final Connection connection;
    private Auth_Record authRecord;
    public Auth_DAO(Connection connection){ this.connection = connection; }

    public void clearAuthDB() throws DataAccessException{
        String sqlReq = "DELETE from authToken;";
        try (PreparedStatement req = connection.prepareStatement(sqlReq)) {
            req.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error occurred clearing AuthDB");
        }
    }

    public String makeAuth(String username) throws DataAccessException{
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

    public void addAuth(Auth_Record auth) throws DataAccessException{
        String sqlReq = "INSERT INTO authToken (authToken, username) VALUES(?,?);";
        try(PreparedStatement req = connection.prepareStatement(sqlReq)){
            req.setString(1, auth.authToken());
            req.setString(2, auth.username());
            req.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while inserting a person to the database");
        }
    }

    public void removeAuth(String authToken) throws DataAccessException {
        String sqlReq = "DELETE FROM authToken WHERE authToken = ?;";
        try (PreparedStatement req = connection.prepareStatement(sqlReq)) {
            req.setString(1, authToken);
            req.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error occurred deleting AuthToken");
        }
    }

    public Auth_Record findAuthWithUsername(String username) throws DataAccessException{
        Auth_Record temp;
        ResultSet results;

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

    public Auth_Record findAuth(String authToken) throws DataAccessException{
        Auth_Record temp;
        ResultSet results;

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
            throw new DataAccessException("Error accessing authToken");
        }
    }

    public int getAuthSize() {
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
