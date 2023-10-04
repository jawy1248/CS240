package chess;

public class Position implements ChessPosition {

    private int row;
    private int col;

    public Position(int row, int col){
        this.row = row;
        this.col = col;
    }

    public void setPosition(int row, int col) {
        this.row = row;
        this.col = col;
    }
    @Override
    public int getRow() { return row; }

    @Override
    public int getColumn() { return col; }
}
