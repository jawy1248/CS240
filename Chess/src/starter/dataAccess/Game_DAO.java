package dataAccess;

import chess.ChessGame;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.UUID;

import static chess.ChessGame.TeamColor.*;


import com.google.gson.Gson;

/**
 * Database for all the current live games
 */
public class Game_DAO {
    private final Connection connection;

    public Game_DAO(Connection connection) { this.connection = connection; }

    public void clearGameDB() throws DataAccessException{
        String sqlReq = "DELETE from game;";
        try (PreparedStatement req = connection.prepareStatement(sqlReq)) {
            req.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error occurred clearing GameDB");
        }
    }

    public void addGame(Game_Record game) throws DataAccessException{
        Gson gson = new Gson();
        String sqlReq = "INSERT INTO game (gameID, whiteUsername, blackUsername, gameName, game) VALUES(?,?,?,?,?);";
        try(PreparedStatement req = connection.prepareStatement(sqlReq)){
            req.setInt(1, game.gameID());
            req.setString(2, game.whiteUsername());
            req.setString(3, game.blackUsername());
            req.setString(4, game.gameName());
            req.setString(5, gson.toJson(game.game()));
            req.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error occurred adding game");
        }
    }

    public Game_Record findGame(int gameID){
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
        Game_Record temp;
        ResultSet results;
        Gson gson = new Gson();
        HashSet<Game_Record> tempHash = new HashSet<>();

        String sqlReq = "SELECT * FROM game;";
        try (PreparedStatement req = connection.prepareStatement(sqlReq)) {
            results = req.executeQuery();
            while (results.next()) {
                    temp = new Game_Record(results.getInt("results"), results.getString("whiteUsername"),
                            results.getString("blackUsername"), results.getString("gameName"),
                            gson.fromJson(results.getString("game"), ChessGame.class) );
                    tempHash.add(temp);
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error occurred accessing all games");
        }
        return tempHash;
    }

    public int getGameSize() {
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
