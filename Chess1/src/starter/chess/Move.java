package chess;

public class Move implements ChessMove {

    private ChessPosition initPos;
    private ChessPosition endPos;
    private ChessPiece.PieceType promo;


    public Move(ChessPosition startPosition, ChessPosition endPosition, ChessPiece.PieceType promotionPiece){
        this.initPos = startPosition;
        this.endPos = endPosition;
        this.promo = promotionPiece;
    }

    @Override
    public ChessPosition getStartPosition() {
        return initPos;
    }

    @Override
    public ChessPosition getEndPosition() {
        return endPos;
    }

    @Override
    public ChessPiece.PieceType getPromotionPiece() {
        return promo;
    }

    public boolean checkMove(ChessBoard board, ChessGame.TeamColor color, ChessPosition newPos){
        ChessPiece newSpot = board.getPiece(newPos);
        if(newSpot != null) {
            if(newSpot.getTeamColor() == color)
                return false;
        }

        return false;
    }
}
