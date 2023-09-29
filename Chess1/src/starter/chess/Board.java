package chess;

public class Board implements ChessBoard{

    // Always use [row][col]
    private ChessPiece[][] board = new ChessPiece[8][8];

    @Override
    public void addPiece(ChessPosition position, ChessPiece piece) {
        int row = position.getRow();
        int col = position.getColumn();

        board[row][col] = piece;
    }

    @Override
    public ChessPiece getPiece(ChessPosition position) {
        int row = position.getRow();
        int col = position.getColumn();

        return board[row][col];
    }

    @Override
    public void resetBoard() {
        for(int i=0; i<8; i++){
            for(int j=0; j<8; j++)
                board[i][j] = null;
        }
    }
}