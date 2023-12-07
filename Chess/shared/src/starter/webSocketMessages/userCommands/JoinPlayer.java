package webSocketMessages.userCommands;

import chess.ChessGame;

public class JoinPlayer extends UserGameCommand {

    public final ChessGame.TeamColor playerColor;

    public JoinPlayer(String gameId, ChessGame.TeamColor playerColor, String username, String  auth){
        super(CommandType.JOIN_PLAYER,auth, gameId, username);
        this.playerColor = playerColor;
    }
    public ChessGame.TeamColor getPlayerColor() {
        return playerColor;
    }
}