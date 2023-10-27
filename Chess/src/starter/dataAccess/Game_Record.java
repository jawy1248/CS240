package dataAccess;

import chess.ChessGame;

/**
 * Creates a single Game object
 * @param gameID int of unique gameID of the game being played
 * @param whiteUser String of username of the player that is playing white
 * @param blackUser String of username of the player that is playing black
 * @param gameName String of the name of the game being played
 * @param game ChessGame object of the game that is being played
 */
public record Game_Record(int gameID, String whiteUser, String blackUser, String gameName, ChessGame game) {}