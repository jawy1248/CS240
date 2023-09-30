package chess;

import java.util.Collection;
import java.util.Set;

public class Piece implements ChessPiece {

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
        ChessPiece piece = board.getPiece(myPosition);
        if(piece == null)
            return null;

        Collection<ChessPosition> possMoves;

        int row = myPosition.getRow();
        int col = myPosition.getColumn();

        PieceType tempType = piece.getPieceType();
        ChessGame.TeamColor tempColor = piece.getTeamColor();

        switch (tempType) {
            case KING -> {
                for(int i=-1; i<2; i++){
                    int tempRow = row+i;
                    if(tempRow < 8 && tempRow > 0) {}
//                        possMoves.add(new Position(tempRow, col));
                }
            }
            case QUEEN -> {

            }
            case ROOK -> {

            }
            case KNIGHT -> {

            }
            case BISHOP -> {

            }
            case PAWN -> {

            }
        }
        return null;
    }
}
