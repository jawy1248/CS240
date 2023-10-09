package chess;

import java.util.Collection;

import static chess.ChessGame.TeamColor.*;
import static chess.ChessPiece.PieceType.*;

public class Game implements ChessGame {

    private ChessBoard board = new Board();
    private TeamColor curTurn = null;


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
        if(possMoves.isEmpty())
            return null;

        ChessPosition tempStart;
        ChessPosition tempEnd;
        ChessPiece tempStartPiece;
        ChessPiece tempEndPiece;

        for(ChessMove move: possMoves) {
            tempStart = move.getStartPosition();
            tempEnd = move.getEndPosition();
            tempStartPiece = board.removePiece(tempStart);
            tempEndPiece = board.removePiece(tempEnd);

            // Move the piece
            board.addPiece(tempEnd, tempStartPiece);

            // If it puts the king in check, remove the move from valid moves
            if(isInCheck(tempStartPiece.getTeamColor()))
                possMoves.remove(move);

            // Reset the board to how it was before
            board.removePiece(tempStart);
            board.removePiece(tempEnd);

            board.addPiece(tempStart, tempStartPiece);
            board.addPiece(tempEnd, tempEndPiece);
        }

        return possMoves;
    }

    @Override
    public void makeMove(ChessMove move) throws InvalidMoveException {
//        if(curTurn != null) {
//            if (board.getPiece(move.getStartPosition()).getTeamColor() != curTurn)
//                return;
//        }

        Collection<ChessMove> validMoves = validMoves(move.getStartPosition());

        boolean validMoveBool = false;
        ChessPosition moveTo = move.getEndPosition();

        for(ChessMove moves : validMoves){
            if(moveTo.equals(moves.getEndPosition())) {
                validMoveBool = true;
                break;
            }
        }

        if(!validMoveBool)
            throw new InvalidMoveException();

        ChessPiece pieceMoving = board.removePiece(move.getStartPosition());
        board.addPiece(move.getEndPosition(), pieceMoving);
    }

    @Override
    public boolean isInCheck(TeamColor teamColor) {
        ChessPosition myKingPos = findKing(teamColor);

        if(myKingPos == null)
            return false;

        boolean inCheck = false;

        for(int i=1; i<9; i++){
            for(int j=1; j<9; j++){
                // Check each spot, check the piece color
                ChessPosition tempPos = new Position(i,j);
                ChessPiece tempPiece = board.getPiece(tempPos);

                // If it is null or the same color, skip
                if(tempPiece == null || tempPiece.getTeamColor() == teamColor)
                    continue;

                // Otherwise, get a list of all possible moves of the piece
                Collection<ChessMove> possMoves = tempPiece.pieceMoves(board, tempPos);
                for(ChessMove spot : possMoves){
                    // If the end position of the other team's move is the same as the king, in check
                    ChessPosition endPos = spot.getEndPosition();
                    if(myKingPos.equals(endPos))
                        inCheck = true;

                    if(inCheck)
                        break;
                }
                if(inCheck)
                    break;
            }
            if(inCheck)
                break;
        }

        return inCheck;
    }

    @Override
    public boolean isInCheckmate(TeamColor teamColor) {
        if(!isInCheck(teamColor))
            return false;

        return false;
    }

    @Override
    public boolean isInStalemate(TeamColor teamColor) {
        if(isInCheck(teamColor))
            return false;

        for(int i=1; i<9; i++){
            for(int j=1; j<9; j++){
                // Look at piece on the board
                ChessPosition tempPos = new Position(i,j);
                ChessPiece tempPiece = board.getPiece(tempPos);
                if(tempPiece == null || tempPiece.getTeamColor() != teamColor)
                    continue;

                // If it's your own color, try to make a move to see if it puts you out of check

            }
        }
        return true;
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

