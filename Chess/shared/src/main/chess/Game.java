package chess;

import java.util.Collection;
import java.util.HashSet;

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
        ChessPiece startPiece = board.getPiece(startPosition);
        if(startPiece == null)
            return null;

        Collection<ChessMove> possMoves = startPiece.pieceMoves(board, startPosition);

        if(possMoves == null || possMoves.isEmpty())
            return null;

        ChessPosition tempStart;
        ChessPosition tempEnd;
        ChessPiece tempStartPiece;
        ChessPiece tempEndPiece;

        Collection<ChessMove> vMoves = new HashSet<>();

        for(ChessMove move:possMoves) {
            tempStart = move.getStartPosition();
            tempEnd = move.getEndPosition();
            tempStartPiece = board.getPiece(tempStart);
            board.removePiece(tempStart);
            tempEndPiece = board.getPiece(tempEnd);
            board.removePiece(tempEnd);

            // Move the piece
            board.addPiece(tempEnd, tempStartPiece);

            // If it puts the king in check, remove the move from valid moves
            if(!isInCheck(tempStartPiece.getTeamColor())) {
                vMoves.add(move);
            }

            // Reset the board to how it was before
            board.removePiece(tempStart);
            board.removePiece(tempEnd);

            board.addPiece(tempStart, tempStartPiece);
            board.addPiece(tempEnd, tempEndPiece);
        }

        return vMoves;
    }

    @Override
    public void makeMove(ChessMove move) throws InvalidMoveException {
        Collection<ChessMove> validMoves = validMoves(move.getStartPosition());
        TeamColor tempColor = board.getPiece(move.getStartPosition()).getTeamColor();

        if(tempColor != curTurn)
            throw new InvalidMoveException();

        if(validMoves == null || !validMoves.contains(move))
            throw new InvalidMoveException();

        ChessPiece pieceMoving = board.getPiece(move.getStartPosition());
        board.removePiece(move.getStartPosition());
        if(move.getPromotionPiece() == null)
            board.addPiece(move.getEndPosition(), pieceMoving);
        else
            board.addPiece(move.getEndPosition(), new Piece(pieceMoving.getTeamColor(), move.getPromotionPiece()));

        // Change turn
        if(tempColor == WHITE)
            setTeamTurn(BLACK);
        else
            setTeamTurn(WHITE);

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
        return isInCheck(teamColor) && !anyValidMoves(teamColor);
    }

    @Override
    public boolean isInStalemate(TeamColor teamColor) {
        return !isInCheck(teamColor) && !anyValidMoves(teamColor);
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

    private boolean anyValidMoves(TeamColor teamColor){
        // Look at entire board
        for(int i=1; i<9; i++){
            for(int j=1; j<9; j++){
                ChessPosition tempPos = new Position(i,j);
                ChessPiece tempPiece = board.getPiece(tempPos);
                // If the spot is empty or not your piece, skip
                if(tempPiece == null || tempPiece.getTeamColor() != teamColor)
                    continue;
                // Get list of valid moves
                Collection<ChessMove> vMoves = validMoves(tempPos);
                // If there is a valid move, return true
                if(vMoves != null && !vMoves.isEmpty())
                    return true;
            }
        }
        return false;
    }
}

