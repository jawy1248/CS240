package chess;

import java.util.Collection;
import java.util.HashSet;

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

        Collection<ChessMove> possMoves = new HashSet<>();

        int row = myPosition.getRow();
        int col = myPosition.getColumn();

        PieceType myType = piece.getPieceType();
        ChessGame.TeamColor myColor = piece.getTeamColor();

        switch (myType) {
            case KING -> {
                System.out.println("myPos: (" + row + ", " + col + ")");
                // Look at the 3x3 square around the king
                for(int i=-1; i<2; i++){
                    for(int j=-1; j<2; j++){
                        int tempRow = row + i;
                        int tempCol = col + j;
                        ChessPosition tempPos = new Position(tempRow, tempCol);

                        System.out.println("tempPos: (" + tempRow + ", " + tempCol + ")");

                        // if the position is on the board, check that position
                        if (tempRow < 8 && tempRow >= 0 && tempCol < 8 && tempCol >= 0) {
                            ChessPiece tempPiece = board.getPiece(tempPos);

                            // if the square is null, it is a valid move
                            if(tempPiece == null) {
                                possMoves.add(new Move(myPosition, tempPos, null));
                                System.out.println("Added Position (null): (" + tempRow + ", " + tempCol + ")");
                            }
                            else {
                                ChessGame.TeamColor tempColor = tempPiece.getTeamColor();

                                // if the square is occupied by the same color, it is invalid
                                if (myColor != tempColor) {
                                    possMoves.add(new Move(myPosition, tempPos, null));
                                    System.out.println("Added Position (color): (" + tempRow + ", " + tempCol + ")");
                                }
                            }
                        }
                    }
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
        return possMoves;
    }
}
