package webSocketMessages.serverMessages;

import chess.ChessGame;

public class LoadMessage extends ServerMessage {

    public final ChessGame game;

    public LoadMessage(ChessGame game){
        super(ServerMessageType.LOAD_GAME);
        this.game = game;
    }
}