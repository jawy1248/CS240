package dataAccess;

import chess.ChessGame;

import java.sql.*;
import java.util.HashSet;


import com.google.gson.Gson;

/**
 * Database for all the current live games
 */
public class Game_DAO {
    private final Connection connection;

    public Game_DAO(Connection connection) { this.connection = connection; }

    private void makeGameDB() throws SQLException {
        String sqlReq =
                """
                    CREATE TABLE  IF NOT EXISTS game (
                        gameID INT NOT NULL AUTO_INCREMENT,
                        whiteUsername VARCHAR(255) DEFAULT NULL,
                        blackUsername VARCHAR(255) DEFAULT NULL,
                        gameName VARCHAR(255) NOT NULL,
                        game TEXT NOT NULL,
                        PRIMARY KEY (gameID),
                        INDEX (whiteUsername),
                        INDEX (blackUsername)
                    )
                """;
        try (PreparedStatement req = connection.prepareStatement(sqlReq)) {
            req.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    public void clearGameDB() throws DataAccessException{
        try{
            makeGameDB();
        } catch(SQLException e){
            throw new DataAccessException(e.toString());
        }

        String sqlReq = "DELETE from game;";
        try (PreparedStatement req = connection.prepareStatement(sqlReq)) {
            req.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error occurred clearing GameDB");
        }
    }

    public int addGame(Game_Record game) throws DataAccessException{
        try{
            makeGameDB();
        } catch(SQLException e){
            throw new DataAccessException(e.toString());
        }

        Gson gson = new Gson();
        String sqlReq = "INSERT INTO game (whiteUsername, blackUsername, gameName, game) VALUES(?,?,?,?);";
        try(PreparedStatement req = connection.prepareStatement(sqlReq, Statement.RETURN_GENERATED_KEYS)){
            req.setString(1, game.whiteUsername());
            req.setString(2, game.blackUsername());
            req.setString(3, game.gameName());
            req.setString(4, gson.toJson(game.game()));
            req.executeUpdate();
            try(ResultSet generatedKeys = req.getGeneratedKeys()){
                generatedKeys.next();
                return generatedKeys.getInt(1);
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error occurred adding game");
        }
    }

    public Game_Record findGame(int gameID){
        try{
            makeGameDB();
        } catch(SQLException e){
            throw new RuntimeException(e);
        }

        Game_Record temp;
        ResultSet results;
        Gson gson = new Gson();

        String sqlReq = "SELECT * FROM game WHERE gameID = ?;";
        try(PreparedStatement req = connection.prepareStatement(sqlReq)){
            req.setInt(1, gameID);
            results = req.executeQuery();
            if (results.next()){
                temp = new Game_Record(results.getInt("gameID"), results.getString("whiteUsername"),
                        results.getString("blackUsername"), results.getString("gameName"),
                        gson.fromJson(results.getString("game"), ChessGame.class) );
                return temp;
            } else
                return null;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public HashSet<Game_Record> findAllGames() throws DataAccessException {
        try{
            makeGameDB();
        } catch(SQLException e){
            throw new DataAccessException(e.toString());
        }

        Game_Record temp;
        ResultSet results;
        Gson gson = new Gson();
        HashSet<Game_Record> tempHash = new HashSet<>();

        String sqlReq = "SELECT * FROM game;";
        try (PreparedStatement req = connection.prepareStatement(sqlReq)) {
            results = req.executeQuery();
            while (results.next()) {
                    temp = new Game_Record(results.getInt("gameID"), results.getString("whiteUsername"),
                            results.getString("blackUsername"), results.getString("gameName"),
                            gson.fromJson(results.getString("game"), ChessGame.class) );
                    tempHash.add(temp);
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.toString());
        }
        return tempHash;
    }

    public int getGameSize() {
        try{
            makeGameDB();
        } catch(SQLException e){
            throw new RuntimeException(e);
        }

        ResultSet results;
        String sqlReq = "SELECT COUNT(*) AS row_count FROM game;";
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

    public void joinGame(ChessGame.TeamColor color, Integer gameID, String username) {
        try{
            makeGameDB();
        } catch(SQLException e){
            throw new RuntimeException(e);
        }

        String team = switch (color) {
            case BLACK -> "blackUsername";
            case WHITE -> "whiteUsername";
        };
        String sqlReq = "UPDATE game SET " + team + " = ? WHERE gameID = ?;";
        try(PreparedStatement req = connection.prepareStatement(sqlReq)){
            req.setString(1,username);
            req.setInt(2,gameID);
            req.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
