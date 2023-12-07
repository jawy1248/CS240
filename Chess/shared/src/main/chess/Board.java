package chess;

public class Board implements ChessBoard {

    // Always use [row][col]
    private ChessPiece[][] board = new ChessPiece[9][9];

    @Override
    public void addPiece(ChessPosition position, ChessPiece piece) {
        int row = position.getRow();
        int col = position.getColumn();

        board[row][col] = piece;
    }

    public ChessPiece removePiece(ChessPosition position){
        int row = position.getRow();
        int col = position.getColumn();
        ChessPiece remPiece = getPiece(position);

        board[row][col] = null;

        return remPiece;
    }

    public ChessPiece[][] getBoard(){ return board; }

    @Override
    public ChessPiece getPiece(ChessPosition position) {
        int row = position.getRow();
        int col = position.getColumn();

        return board[row][col];
    }

    @Override
    public void resetBoard() {
        // Clear the board
        for(int i=0; i<8; i++){
            for(int j=0; j<8; j++)
                board[i][j] = null;
        }

        // Set up White
        // Set up the first row
        board[1][1] = new Piece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK);
        board[1][2] = new Piece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KNIGHT);
        board[1][3] = new Piece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.BISHOP);
        board[1][4] = new Piece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.QUEEN);
        board[1][5] = new Piece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KING);
        board[1][6] = new Piece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.BISHOP);
        board[1][7] = new Piece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KNIGHT);
        board[1][8] = new Piece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK);

        // Set up the pawns
        for(int i=1; i<9; i++) {
            board[2][i] = new Piece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        }

        // Set up Black
        // Set up the last row
        board[8][1] = new Piece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK);
        board[8][2] = new Piece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT);
        board[8][3] = new Piece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.BISHOP);
        board[8][4] = new Piece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.QUEEN);
        board[8][5] = new Piece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KING);
        board[8][6] = new Piece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.BISHOP);
        board[8][7] = new Piece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT);
        board[8][8] = new Piece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK);

        // Set up the pawns
        for(int i=1; i<9; i++) {
            board[7][i] = new Piece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN);
        }
    }
}