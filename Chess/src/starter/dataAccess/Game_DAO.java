package dataAccess;

import chess.*;
import com.google.gson.*;
import chess.Board;
import chess.Game;
import chess.Piece;
import model.Game_Record;

import java.sql.*;
import java.util.HashSet;
import java.lang.reflect.Type;

/**
 * Data access object for the game database
 */
public class Game_DAO {
    /**
     * Connection to the SQL database
     */
    private final Connection connection;

    /**
     * Constructor to set the connection
     * @param connection Connection to the SQL database
     */
    public Game_DAO(Connection connection) { this.connection = connection; }

    /**
     * Creates the database if not already made
     * @throws SQLException SQL error
     */
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

    /**
     * Clears the game database
     * @throws DataAccessException data access error
     */
    public void clearGameDB() throws DataAccessException{
        // Make sure database exists
        try{
            makeGameDB();
        } catch(SQLException e){
            throw new DataAccessException(e.toString());
        }

        // SQL instructions for deleting all games
        String sqlReq = "DELETE from game;";
        try (PreparedStatement req = connection.prepareStatement(sqlReq)) {
            req.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error occurred clearing GameDB");
        }
    }

    public int addGame(Game_Record game) throws DataAccessException{
        // Make sure database exists
        try{
            makeGameDB();
        } catch(SQLException e){
            throw new DataAccessException(e.toString());
        }

        // SQL instructions for adding a single game to database
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
        // Make sure database exists
        try{
            makeGameDB();
        } catch(SQLException e){
            throw new RuntimeException(e);
        }

        Game_Record temp;
        ResultSet results;

        // SQL instructions for finding a single game
        String sqlReq = "SELECT * FROM game WHERE gameID = ?;";
        try(PreparedStatement req = connection.prepareStatement(sqlReq)){
            req.setInt(1, gameID);
            results = req.executeQuery();
            if (results.next()){
                temp = new Game_Record(results.getInt("gameID"), results.getString("whiteUsername"),
                        results.getString("blackUsername"), results.getString("gameName"),
                        deserializeGame(results.getString("game")) );
                return temp;
            } else
                return null;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public HashSet<Game_Record> findAllGames() throws DataAccessException {
        // Make sure database exists
        try{
            makeGameDB();
        } catch(SQLException e){
            throw new DataAccessException(e.toString());
        }

        Game_Record temp;
        ResultSet results;
        HashSet<Game_Record> tempHash = new HashSet<>();

        // SQL instructions for finding all games
        String sqlReq = "SELECT * FROM game;";
        try (PreparedStatement req = connection.prepareStatement(sqlReq)) {
            results = req.executeQuery();
            while (results.next()) {
                    temp = new Game_Record(results.getInt("gameID"), results.getString("whiteUsername"),
                            results.getString("blackUsername"), results.getString("gameName"),
                            deserializeGame(results.getString("game")) );
                    tempHash.add(temp);
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.toString());
        }
        return tempHash;
    }

    public int getGameSize() {
        // Make sure database exists
        try{
            makeGameDB();
        } catch(SQLException e){
            throw new RuntimeException(e);
        }

        // SQL instructions for getting a count of the games
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
        // Make sure database exists
        try{
            makeGameDB();
        } catch(SQLException e){
            throw new RuntimeException(e);
        }

        String team = switch (color) {
            case BLACK -> "blackUsername";
            case WHITE -> "whiteUsername";
        };

        // SQL instructions for adding a user to a game
        String sqlReq = "UPDATE game SET " + team + " = ? WHERE gameID = ?;";
        try(PreparedStatement req = connection.prepareStatement(sqlReq)){
            req.setString(1,username);
            req.setInt(2,gameID);
            req.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private ChessGame deserializeGame(String gameString) {
        GsonBuilder gsonBuilder = new GsonBuilder();
//        gsonBuilder.registerTypeAdapter(Game.class, new ChessGameAdapter());
        gsonBuilder.registerTypeAdapter(ChessBoard.class, new ChessBoardAdapter());
        Gson gson = gsonBuilder.create();
        return gson.fromJson(gameString, Game.class);
    }

    public static class ChessGameAdapter implements JsonDeserializer<ChessGame> {
        public ChessGame deserialize(JsonElement el, Type type, JsonDeserializationContext ctx) throws JsonParseException {
            return new Gson().fromJson(el, Game.class);
        }
    }

    public static class ChessBoardAdapter implements JsonDeserializer<ChessBoard> {
        public ChessBoard deserialize(JsonElement el, Type type, JsonDeserializationContext ctx) throws JsonParseException {
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(ChessPiece.class, new ChessPieceAdapter());
            Gson gson = gsonBuilder.create();
            return gson.fromJson(el, Board.class);
        }
    }

    public static class ChessPieceAdapter implements JsonDeserializer<ChessPiece> {
        public ChessPiece deserialize(JsonElement el, Type type, JsonDeserializationContext ctx) throws JsonParseException {
            return new Gson().fromJson(el, Piece.class);
        }
    }
}
