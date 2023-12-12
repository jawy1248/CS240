package chess;

import java.util.Objects;

public class Move implements ChessMove {

    private ChessPosition initPos;
    private ChessPosition endPos;
    private ChessPiece.PieceType promo;


    public Move(ChessPosition startPosition, ChessPosition endPosition, ChessPiece.PieceType promotionPiece){
        this.initPos = startPosition;
        this.endPos = endPosition;
        this.promo = promotionPiece;
    }

    public Move(ChessPosition startPosition, ChessPosition endPosition){
        this.initPos = startPosition;
        this.endPos = endPosition;
    }

    @Override
    public ChessPosition getStartPosition() {
        return initPos;
    }

    @Override
    public ChessPosition getEndPosition() { return endPos; }

    @Override
    public ChessPiece.PieceType getPromotionPiece() {
        return promo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Move move = (Move) o;
        return Objects.equals(initPos, move.initPos) && Objects.equals(endPos, move.endPos) && promo == move.promo;
    }

    @Override
    public int hashCode() {
        return Objects.hash(initPos, endPos, promo);
    }

    @Override
    public String toString(){
        StringBuilder finalString = new StringBuilder();
        finalString.append(initPos);
        finalString.append(" to ");
        finalString.append(endPos);
        return finalString.toString();
    }
}
