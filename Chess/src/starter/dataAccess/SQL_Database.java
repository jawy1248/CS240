package dataAccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQL_Database {
    public Connection connection;
    public Connection openConnection() throws DataAccessException {
        try{
            final String connURL = "jdbc:sqlite:Chess.sqlite";
            connection = DriverManager.getConnection(connURL);
            connection.setAutoCommit(false);
            System.out.println("Database open");
        } catch(SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Unable to connect to database");
        }
        return connection;
    }

    public Connection getConnection() throws DataAccessException {
        if(connection == null)
            return openConnection();
        return connection;
    }

    public void closeConnection(boolean commit) {
        try {
            if(commit)
                connection.commit();
            else
                connection.rollback();

            connection.close();
            connection = null;
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
