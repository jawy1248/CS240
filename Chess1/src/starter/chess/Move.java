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
}
