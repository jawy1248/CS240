package chess;

import java.util.Collection;

import static chess.ChessGame.TeamColor.*;
import static chess.ChessPiece.PieceType.*;

public class Game implements ChessGame {

    private ChessBoard board = new Board();
    private TeamColor curTurn = WHITE;


    @Override
    public TeamColor getTeamTurn() {
        return curTurn;
    }

    @Override
    public void setTeamTurn(TeamColor team) { curTurn = team; }

    @Override
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        if(board.getPiece(startPosition) == null)
                return null;

        Collection<ChessMove> possMoves = board.getPiece(startPosition).pieceMoves(board, startPosition);

        return possMoves;
    }

    @Override
    public void makeMove(ChessMove move) throws InvalidMoveException {

    }

    @Override
    public boolean isInCheck(TeamColor teamColor) {
        ChessPosition myKingPos = findKing(teamColor);
        System.out.println("King Position: " + myKingPos);
        boolean inCheck = false;

        for(int i=1; i<9; i++){
            for(int j=1; j<9; j++){
                // Check each spot, check the piece color
                ChessPosition tempPos = new Position(i,j);
                ChessPiece tempPiece = board.getPiece(tempPos);
                // If it is null or the same color, skip
                if(tempPiece == null || tempPiece.getTeamColor() == teamColor)
                    break;

                System.out.println("TempPiece: " + tempPiece);
                // Otherwise, get a list of all possible moves of the piece
                Collection<ChessMove> possMoves = tempPiece.pieceMoves(board, tempPos);
                for(ChessMove spot : possMoves){
                    // If the end position of the other team's move is the same as the king, in check
                    System.out.println(spot);
                    if(myKingPos == spot.getEndPosition()) {
                        System.out.println("Check");
                        inCheck = true;
                    }
                    if(inCheck) {
                        System.out.println("BREAKING OUT");
                        break;
                    }
                }
                if(inCheck) {
                    System.out.println("BREAKING OUT");
                    break;
                }
            }
            if(inCheck) {
                System.out.println("BREAKING OUT");
                break;
            }
        }

        return inCheck;
    }

    @Override
    public boolean isInCheckmate(TeamColor teamColor) {
        return false;
    }

    @Override
    public boolean isInStalemate(TeamColor teamColor) {
        return false;
    }

    @Override
    public void setBoard(ChessBoard board) { this.board = board; }

    @Override
    public ChessBoard getBoard() {
        return board;
    }

    private ChessPosition findKing(TeamColor team){
        ChessPosition kingPos = null;

        for(int i=1; i<9; i++){
            for(int j=1; j<9; j++){
                ChessPosition tempPos = new Position(i,j);
                ChessPiece tempPiece = board.getPiece(tempPos);
                if(tempPiece != null && tempPiece.getPieceType() == KING && tempPiece.getTeamColor() == team) {
                    kingPos = tempPos;
                    break;
                }
            }
            if(kingPos != null)
                break;
        }

        return kingPos;
    }
}
