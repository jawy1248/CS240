package dataAccess;

import chess.ChessGame;

/**
 * Creates a single game model object. Holds the following variables
 *
 * @param gameID        unique gameID of the game being played
 * @param whiteUser     unique username of the player that is playing white
 * @param blackUser     unique username of the player that is playing black
 * @param gameName      name of the game being played
 * @param game          object of the game that is being played
 */
public record Game_Record(int gameID, String whiteUser, String blackUser, String gameName, ChessGame game) {}