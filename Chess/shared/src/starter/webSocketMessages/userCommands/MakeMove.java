package webSocketMessages.userCommands;

import chess.ChessMove;

public class MakeMove  extends UserGameCommand{
    ChessMove move;
    String userID;
    String auth;
    public MakeMove(String gameID , String auth, String username, ChessMove move) {
        super(CommandType.MAKE_MOVE, auth, gameID, username);
        this.commandType = CommandType.MAKE_MOVE;
        this.auth = auth;
        this.move = move;
    }

    public ChessMove getMove() {
        return move;
    }

    public void setMove(ChessMove move) {
        this.move = move;
    }

    public String getAuthToken() {
        return auth;
    }

    public void setAuthToken(String authToken) {
        this.auth = authToken;
    }
}