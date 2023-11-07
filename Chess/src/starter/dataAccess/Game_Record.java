package dataAccess;

import chess.ChessGame;

/**
 * Creates a single Game object
 * @param gameID int of unique gameID of the game being played
 * @param whiteUsername String of username of the player that is playing white
 * @param blackUsername String of username of the player that is playing black
 * @param gameName String of the name of the game being played
 * @param game ChessGame object of the game that is being played
 */
public record Game_Record(Integer gameID, String whiteUsername, String blackUsername, String gameName, ChessGame game) {}