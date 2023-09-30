package chess;

public class Board implements ChessBoard{

    // Always use [row][col]
    private ChessPiece[][] board = new ChessPiece[8][8];

    public Board(){ resetBoard(); }

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
        // Clear the board
        for(int i=0; i<8; i++){
            for(int j=0; j<8; j++)
                board[i][j] = null;
        }

        // Set up White
        // Set up the first row
        board[0][0] = new Piece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK);
        board[0][1] = new Piece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KNIGHT);
        board[0][2] = new Piece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.BISHOP);
        board[0][3] = new Piece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.QUEEN);
        board[0][4] = new Piece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KING);
        board[0][5] = new Piece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.BISHOP);
        board[0][6] = new Piece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KNIGHT);
        board[0][7] = new Piece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK);

        // Set up the pawns
        for(int i=0; i<8; i++) {
            board[1][i] = new Piece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        }

        // Set up Black
        // Set up the last row
        board[7][0] = new Piece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK);
        board[7][1] = new Piece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT);
        board[7][2] = new Piece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.BISHOP);
        board[7][3] = new Piece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.QUEEN);
        board[7][4] = new Piece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KING);
        board[7][5] = new Piece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.BISHOP);
        board[7][6] = new Piece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT);
        board[7][7] = new Piece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK);

        // Set up the pawns
        for(int i=0; i<8; i++) {
            board[6][i] = new Piece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN);
        }
    }
}