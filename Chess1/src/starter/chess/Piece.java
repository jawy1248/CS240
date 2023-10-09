package chess;

import java.util.Collection;
import java.util.HashSet;

import static chess.ChessGame.TeamColor.*;
import static chess.ChessPiece.PieceType.*;

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

                // Look at the 3x3 square around the king
                for(int i=-1; i<2; i++){
                    for(int j=-1; j<2; j++){
                        int tempRow = row + i;
                        int tempCol = col + j;
                        ChessPosition tempPos = new Position(tempRow, tempCol);

                        // if the position is on the board, check that position
                        if (tempRow < 9 && tempRow > 0 && tempCol < 9 && tempCol > 0) {

                            // if the square is null, it is a valid move
                            if(board.getPiece(tempPos) == null)
                                possMoves.add(new Move(myPosition, tempPos, null));
                            else {

                                // if the square is occupied by the same color, it is invalid
                                if (myColor != board.getPiece(tempPos).getTeamColor())
                                    possMoves.add(new Move(myPosition, tempPos, null));
                            }
                        }
                    }
                }
            }
            case QUEEN -> {
                checkCross(board, possMoves, myPosition);
                checkDiag(board, possMoves, myPosition);
            }
            case ROOK -> {
                checkCross(board, possMoves, myPosition);
            }
            case KNIGHT -> {
                // Create variables
                int tempRow;
                int tempCol;
                ChessPosition tempPos;
                ChessPiece tempPiece;

                // Look at each of the moves
                // Up
                tempRow = row+2;

                tempCol = col-1;
                if(tempRow > 0 && tempRow < 9 && tempCol > 0 && tempCol < 9) {
                    tempPos = new Position(tempRow, tempCol);
                    tempPiece = board.getPiece(tempPos);
                    if (tempPiece == null || tempPiece.getTeamColor() != myColor)
                        possMoves.add(new Move(myPosition, tempPos, null));
                }

                tempCol = col+1;
                if(tempRow > 0 && tempRow < 9 && tempCol > 0 && tempCol < 9) {
                    tempPos = new Position(tempRow, tempCol);
                    tempPiece = board.getPiece(tempPos);
                    if (tempPiece == null || tempPiece.getTeamColor() != myColor)
                        possMoves.add(new Move(myPosition, tempPos, null));
                }

                // Right
                tempCol = col+2;

                tempRow = row-1;
                if(tempRow > 0 && tempRow < 9 && tempCol > 0 && tempCol < 9) {
                    tempPos = new Position(tempRow, tempCol);
                    tempPiece = board.getPiece(tempPos);
                    if (tempPiece == null || tempPiece.getTeamColor() != myColor)
                        possMoves.add(new Move(myPosition, tempPos, null));
                }

                tempRow = row+1;
                if(tempRow > 0 && tempRow < 9 && tempCol > 0 && tempCol < 9) {
                    tempPos = new Position(tempRow, tempCol);
                    tempPiece = board.getPiece(tempPos);
                    if (tempPiece == null || tempPiece.getTeamColor() != myColor)
                        possMoves.add(new Move(myPosition, tempPos, null));
                }

                // Down
                tempRow = row-2;

                tempCol = col-1;
                if(tempRow > 0 && tempRow < 9 && tempCol > 0 && tempCol < 9) {
                    tempPos = new Position(tempRow, tempCol);
                    tempPiece = board.getPiece(tempPos);
                    if (tempPiece == null || tempPiece.getTeamColor() != myColor)
                        possMoves.add(new Move(myPosition, tempPos, null));
                }

                tempCol = col+1;
                if(tempRow > 0 && tempRow < 9 && tempCol > 0 && tempCol < 9) {
                    tempPos = new Position(tempRow, tempCol);
                    tempPiece = board.getPiece(tempPos);
                    if (tempPiece == null || tempPiece.getTeamColor() != myColor)
                        possMoves.add(new Move(myPosition, tempPos, null));
                }

                // Left
                tempCol = col-2;

                tempRow = row-1;
                if(tempRow > 0 && tempRow < 9 && tempCol > 0 && tempCol < 9) {
                    tempPos = new Position(tempRow, tempCol);
                    tempPiece = board.getPiece(tempPos);
                    if (tempPiece == null || tempPiece.getTeamColor() != myColor)
                        possMoves.add(new Move(myPosition, tempPos, null));
                }

                tempRow = row + 1;
                if(tempRow > 0 && tempRow < 9 && tempCol > 0 && tempCol < 9) {
                    tempPos = new Position(tempRow, tempCol);
                    tempPiece = board.getPiece(tempPos);
                    if (tempPiece == null || tempPiece.getTeamColor() != myColor)
                        possMoves.add(new Move(myPosition, tempPos, null));
                }

            }
            case BISHOP -> {
                checkDiag(board, possMoves, myPosition);
            }
            case PAWN -> {
                // Create variables
                ChessPosition tempPos;
                ChessPiece tempPiece;
                int tempRow;

                // Look at current position, if it is in row 2 (white) or 7 (black), can move 2
                if(myColor == WHITE){
                    // Move forward 1 row
                    tempRow = row + 1;

                    // Check diag left pos
                    if(col != 1) {
                        tempPos = new Position(tempRow, col - 1);
                        tempPiece = board.getPiece(tempPos);
                        if (tempPiece != null && tempPiece.getTeamColor() != WHITE)
                            addWhitePawnMoves(possMoves, tempRow, myPosition, tempPos);
                    }

                    // Check straight forward pos
                    tempPos = new Position(tempRow, col);
                    tempPiece = board.getPiece(tempPos);
                    if (tempPiece == null)
                        addWhitePawnMoves(possMoves, tempRow, myPosition, tempPos);

                    // Check diag right pos
                    if(col != 8) {
                        tempPos = new Position(tempRow, col + 1);
                        tempPiece = board.getPiece(tempPos);
                        if (tempPiece != null && tempPiece.getTeamColor() != WHITE)
                            addWhitePawnMoves(possMoves, tempRow, myPosition, tempPos);
                    }

                    // If this is the first move
                    if(row == 2){
                        ChessPosition tempPos1 = new Position(tempRow, col);
                        ChessPosition tempPos2 = new Position(tempRow+1, col);
                        tempPiece = board.getPiece(tempPos1);
                        if (tempPiece == null) {
                            tempPiece = board.getPiece(tempPos2);
                            if (tempPiece == null) {
                                addWhitePawnMoves(possMoves, tempRow+1, myPosition, tempPos2);
                            }
                        }
                    }
                }else{
                    // Move forward one row
                    tempRow = row - 1;

                    // Check diag right pos
                    if(col != 1) {
                        tempPos = new Position(tempRow, col - 1);
                        tempPiece = board.getPiece(tempPos);
                        if (tempPiece != null && tempPiece.getTeamColor() != BLACK)
                            addBlackPawnMoves(possMoves, tempRow, myPosition, tempPos);
                    }

                    // Check straight forward pos
                    tempPos = new Position(tempRow, col);
                    tempPiece = board.getPiece(tempPos);
                    if (tempPiece == null)
                        addBlackPawnMoves(possMoves, tempRow, myPosition, tempPos);

                    // Check diag left pos
                    if(col != 8) {
                        tempPos = new Position(tempRow, col + 1);
                        tempPiece = board.getPiece(tempPos);
                        if (tempPiece != null && tempPiece.getTeamColor() != BLACK)
                            addBlackPawnMoves(possMoves, tempRow, myPosition, tempPos);
                    }

                    // If this is the first move
                    if(row == 7){
                        ChessPosition tempPos1 = new Position(tempRow, col);
                        ChessPosition tempPos2 = new Position(tempRow-1, col);
                        tempPiece = board.getPiece(tempPos1);
                        if (tempPiece == null) {
                            tempPiece = board.getPiece(tempPos2);
                            if (tempPiece == null) {
                                addBlackPawnMoves(possMoves, tempRow-1, myPosition, tempPos2);
                            }
                        }
                    }
                }
            }
        }
        return possMoves;
    }

    private void addWhitePawnMoves(Collection<ChessMove> possMoves, int tempRow, ChessPosition myPosition, ChessPosition tempPos){
        if(tempRow == 8) {
            possMoves.add(new Move(myPosition, tempPos, QUEEN));
            possMoves.add(new Move(myPosition, tempPos, BISHOP));
            possMoves.add(new Move(myPosition, tempPos, ROOK));
            possMoves.add(new Move(myPosition, tempPos, KNIGHT));
        }
        else
            possMoves.add(new Move(myPosition, tempPos, null));
    }

    private void addBlackPawnMoves(Collection<ChessMove> possMoves, int tempRow, ChessPosition myPosition, ChessPosition tempPos){
        if(tempRow == 1) {
            possMoves.add(new Move(myPosition, tempPos, QUEEN));
            possMoves.add(new Move(myPosition, tempPos, BISHOP));
            possMoves.add(new Move(myPosition, tempPos, ROOK));
            possMoves.add(new Move(myPosition, tempPos, KNIGHT));
        }
        else
            possMoves.add(new Move(myPosition, tempPos, null));
    }

    private void checkCross(ChessBoard board, Collection<ChessMove> possMoves, ChessPosition myPosition){
        ChessGame.TeamColor myColor = board.getPiece(myPosition).getTeamColor();
        int row = myPosition.getRow();
        int col = myPosition.getColumn();

        int tempRow;
        int tempCol;
        ChessPosition tempPos;
        ChessPiece tempPiece;

        // Check up
        for(int i=1; i<8; i++){
            // Step through the rows
            tempRow = row+i;

            // If you get off the board, break
            if(tempRow > 8)
                break;

            // If on the board, check tempPos for a piece
            tempPos = new Position(tempRow, col);
            tempPiece = board.getPiece(tempPos);

            // If there is not a piece, add to possMoves and check next row
            if(tempPiece == null)
                possMoves.add(new Move(myPosition, tempPos, null));

            // Otherwise, check if piece is same color and then break...if it is not same color, add to possMoves
            else{
                if(myColor != tempPiece.getTeamColor())
                    possMoves.add(new Move(myPosition, tempPos, null));
                break;
            }
        }

        // Check down
        for(int i=1; i<8; i++){
            // Step through the rows
            tempRow = row-i;

            // If you get off the board, break
            if(tempRow < 1)
                break;

            // If on the board, check tempPos for a piece
            tempPos = new Position(tempRow, col);
            tempPiece = board.getPiece(tempPos);

            // If there is not a piece, add to possMoves and check next row
            if(tempPiece == null)
                possMoves.add(new Move(myPosition, tempPos, null));

            // Otherwise, check if piece is same color and then break...if it is not same color, add to possMoves
            else{
                if(myColor != tempPiece.getTeamColor())
                    possMoves.add(new Move(myPosition, tempPos, null));
                break;
            }
        }

        // Check left
        for(int i=1; i<8; i++){
            // Step through the rows
            tempCol = col-i;

            // If you get off the board, break
            if(tempCol < 1)
                break;

            // If on the board, check tempPos for a piece
            tempPos = new Position(row, tempCol);
            tempPiece = board.getPiece(tempPos);

            // If there is not a piece, add to possMoves and check next row
            if(tempPiece == null)
                possMoves.add(new Move(myPosition, tempPos, null));

            // Otherwise, check if piece is same color and then break...if it is not same color, add to possMoves
            else{
                if(myColor != tempPiece.getTeamColor())
                    possMoves.add(new Move(myPosition, tempPos, null));

                break;
            }
        }

        // Check right
        for(int i=1; i<8; i++){
            // Step through the rows
            tempCol = col+i;

            // If you get off the board, break
            if(tempCol > 8)
                break;

            // If on the board, check tempPos for a piece
            tempPos = new Position(row, tempCol);
            tempPiece = board.getPiece(tempPos);

            // If there is not a piece, add to possMoves and check next row
            if(tempPiece == null)
                possMoves.add(new Move(myPosition, tempPos, null));

                // Otherwise, check if piece is same color and then break...if it is not same color, add to possMoves
            else{
                if(myColor != tempPiece.getTeamColor())
                    possMoves.add(new Move(myPosition, tempPos, null));

                break;
            }
        }
    }

    private void checkDiag(ChessBoard board, Collection<ChessMove> possMoves, ChessPosition myPosition){
        ChessGame.TeamColor myColor = board.getPiece(myPosition).getTeamColor();
        int row = myPosition.getRow();
        int col = myPosition.getColumn();

        int tempRow;
        int tempCol;
        ChessPosition tempPos;
        ChessPiece tempPiece;

        // Check up right
        for(int i=1; i<8; i++){
            // Step through the diag
            tempRow = row+i;
            tempCol = col+i;

            // If you get off the board, break
            if(tempRow > 8 || tempCol > 8)
                break;

            // If on the board, check tempPos for a piece
            tempPos = new Position(tempRow, tempCol);
            tempPiece = board.getPiece(tempPos);

            // If there is not a piece, add to possMoves and check next row
            if(tempPiece == null)
                possMoves.add(new Move(myPosition, tempPos, null));

            // Otherwise, check if piece is same color and then break...if it is not same color, add to possMoves
            else{
                if(myColor != tempPiece.getTeamColor())
                    possMoves.add(new Move(myPosition, tempPos, null));
                break;
            }
        }

        // Check up left
        for(int i=1; i<8; i++){
            // Step through the diag
            tempRow = row+i;
            tempCol = col-i;

            // If you get off the board, break
            if(tempRow > 8 || tempCol < 1)
                break;

            // If on the board, check tempPos for a piece
            tempPos = new Position(tempRow, tempCol);
            tempPiece = board.getPiece(tempPos);

            // If there is not a piece, add to possMoves and check next row
            if(tempPiece == null)
                possMoves.add(new Move(myPosition, tempPos, null));

            // Otherwise, check if piece is same color and then break...if it is not same color, add to possMoves
            else{
                if(myColor != tempPiece.getTeamColor())
                    possMoves.add(new Move(myPosition, tempPos, null));
                break;
            }
        }

        // Check down left
        for(int i=1; i<8; i++){
            // Step through the diag
            tempRow = row-i;
            tempCol = col-i;

            // If you get off the board, break
            if(tempRow < 1 || tempCol < 1)
                break;

            // If on the board, check tempPos for a piece
            tempPos = new Position(tempRow, tempCol);
            tempPiece = board.getPiece(tempPos);

            // If there is not a piece, add to possMoves and check next row
            if(tempPiece == null)
                possMoves.add(new Move(myPosition, tempPos, null));

            // Otherwise, check if piece is same color and then break...if it is not same color, add to possMoves
            else{
                if(myColor != tempPiece.getTeamColor())
                    possMoves.add(new Move(myPosition, tempPos, null));
                break;
            }
        }

        // Check down right
        for(int i=1; i<8; i++){
            // Step through the diag
            tempRow = row-i;
            tempCol = col+i;

            // If you get off the board, break
            if(tempRow < 1 || tempCol > 8)
                break;

            // If on the board, check tempPos for a piece
            tempPos = new Position(tempRow, tempCol);
            tempPiece = board.getPiece(tempPos);

            // If there is not a piece, add to possMoves and check next row
            if(tempPiece == null)
                possMoves.add(new Move(myPosition, tempPos, null));

            // Otherwise, check if piece is same color and then break...if it is not same color, add to possMoves
            else{
                if(myColor != tempPiece.getTeamColor())
                    possMoves.add(new Move(myPosition, tempPos, null));
                break;
            }
        }
    }

    @Override
    public String toString(){
        StringBuilder finalString = new StringBuilder();
        finalString.append(color);
        finalString.append(" ");
        finalString.append(type);

        return finalString.toString();
    }
}
