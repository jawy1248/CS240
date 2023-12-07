package ui;

import chess.ChessGame;

public interface NotificationHandler {
    void updateBoard(ChessGame game);
    void message(String message);
    void error(String error);


}