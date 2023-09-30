package chess;

import java.util.Collection;

public class Piece implements ChessPiece {

    // 0 is white, 1 is black
    private ChessGame.TeamColor color;
    private PieceType type;

    public Piece(ChessGame.TeamColor color, PieceType piece){
        this.color = color;
        this.type = piece;
    }

    @Override
    public ChessGame.TeamColor getTeamColor() {
        return color;
    }

    @Override
    public PieceType getPieceType() {
        return type;
    }

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        return null;
    }
}
