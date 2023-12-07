package ui;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import chess.*;

import static ui.EscapeSequences.*;

public class PrintBoard {
    // Set up the initial board
    public static String[][] board = {
            {WHITE_ROOK, WHITE_KNIGHT, WHITE_BISHOP, WHITE_KING, WHITE_QUEEN, WHITE_BISHOP, WHITE_KNIGHT, WHITE_ROOK},
            {WHITE_PAWN, WHITE_PAWN, WHITE_PAWN, WHITE_PAWN, WHITE_PAWN, WHITE_PAWN, WHITE_PAWN, WHITE_PAWN},
            {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
            {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
            {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
            {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
            {BLACK_PAWN, BLACK_PAWN, BLACK_PAWN, BLACK_PAWN, BLACK_PAWN, BLACK_PAWN, BLACK_PAWN, BLACK_PAWN},
            {BLACK_ROOK, BLACK_KNIGHT, BLACK_BISHOP, BLACK_KING, BLACK_QUEEN, BLACK_BISHOP, BLACK_KNIGHT, BLACK_ROOK},
    };

    public void printBlack(){
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        out.print(ERASE_SCREEN);
        System.out.println("Black:");

        System.out.print(SET_TEXT_COLOR_WHITE);
        System.out.print(SET_BG_COLOR_LIGHT_GREY);
        System.out.print("    ");
        for(int i=0; i<8; i++){
            System.out.print(SET_BG_COLOR_LIGHT_GREY);
            char x = (char) ('h' - i);
            System.out.print(x + "  ");
        }
        System.out.print("  ");
        System.out.print(SET_BG_COLOR_DARK_GREY);
        System.out.println();

        for(int i=0; i<8; i++){
            for(int j=0; j<8; j++){
                boolean black = ( (i + j) % 2 == 0 );
                if(j == 0){
                    System.out.print(SET_BG_COLOR_LIGHT_GREY);
                    System.out.print(" ");
                    System.out.print(1+i);
                    System.out.print(" ");
                }
                if(black){
                    System.out.print(SET_BG_COLOR_DARK_GREEN);
                    if(!board[i][j].equals(EMPTY))
                        System.out.print(SET_TEXT_COLOR_WHITE);
                    System.out.print(board[i][j]);
                } else {
                    System.out.print(SET_BG_COLOR_DARK_GREY);
                    if(!board[i][j].equals(EMPTY))
                        System.out.print(SET_TEXT_COLOR_WHITE);
                    System.out.print(board[i][j]);
                }
                System.out.print(SET_TEXT_COLOR_WHITE);
                if(j==7){
                    System.out.print(SET_BG_COLOR_LIGHT_GREY);
                    System.out.print(" ");
                    System.out.print(1+i);
                    System.out.print(" ");
                }
            }
            System.out.print(SET_BG_COLOR_DARK_GREY);
            System.out.println();
        }
        System.out.print(SET_BG_COLOR_LIGHT_GREY);
        System.out.print("    ");
        for(int i=0; i<8; i++){
            System.out.print(SET_BG_COLOR_LIGHT_GREY);
            char x = (char) ('h' - i);
            System.out.print(x + "  ");
        }
        System.out.print(SET_BG_COLOR_LIGHT_GREY);
        System.out.print("  ");
        System.out.print(SET_BG_COLOR_DARK_GREY);
        System.out.println();
    }

    public void printWhite(){
        System.out.println("White:");

        System.out.print(SET_TEXT_COLOR_WHITE);
        System.out.print(SET_BG_COLOR_LIGHT_GREY);
        System.out.print("    ");
        for(int i=0; i<8; i++){
            System.out.print(SET_BG_COLOR_LIGHT_GREY);
            char x = (char) ('a' + i);
            System.out.print(x + "  ");
        }
        System.out.print("  ");
        System.out.print(SET_BG_COLOR_DARK_GREY);
        System.out.println();

        for(int i=0; i<8; i++){
            for(int j=0; j<8; j++){
                boolean white = ( (i + j) % 2 == 0 );
                if(j == 0){
                    System.out.print(SET_BG_COLOR_LIGHT_GREY);
                    System.out.print(" ");
                    System.out.print(8-i);
                    System.out.print(" ");
                }
                if(white){
                    System.out.print(SET_BG_COLOR_DARK_GREEN);
                    if(!board[7-i][7-j].equals(EMPTY))
                        System.out.print(SET_TEXT_COLOR_WHITE);
                    System.out.print(board[7-i][7-j]);
                } else {
                    System.out.print(SET_BG_COLOR_DARK_GREY);
                    if(!board[7-i][7-j].equals(EMPTY))
                        System.out.print(SET_TEXT_COLOR_WHITE);
                    System.out.print(board[7-i][7-j]);
                }
                System.out.print(SET_TEXT_COLOR_WHITE);
                if(j==7){
                    System.out.print(SET_BG_COLOR_LIGHT_GREY);
                    System.out.print(" ");
                    System.out.print(8-i);
                    System.out.print(" ");
                }
            }
            System.out.print(SET_BG_COLOR_DARK_GREY);
            System.out.println();
        }
        System.out.print(SET_BG_COLOR_LIGHT_GREY);
        System.out.print("    ");
        for(int i=0; i<8; i++){
            System.out.print(SET_BG_COLOR_LIGHT_GREY);
            char x = (char) ('a' + i);
            System.out.print(x + "  ");
        }
        System.out.print(SET_BG_COLOR_LIGHT_GREY);
        System.out.print("  ");
        System.out.print(SET_BG_COLOR_DARK_GREY);
        System.out.println();
    }

    public void whiteMoves(Collection<ChessMove> moves){
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        out.print(ERASE_SCREEN);
        System.out.print(SET_TEXT_COLOR_WHITE);
        System.out.print(SET_BG_COLOR_LIGHT_GREY);
        System.out.print("    ");
        for (int i = 0; i < 8; i++) {
            System.out.print(SET_BG_COLOR_LIGHT_GREY);
            char x = (char) ('a' + i);
            System.out.print(x + "  ");
        }
        System.out.print("  ");
        System.out.print(SET_BG_COLOR_DARK_GREY);
        System.out.println();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                boolean isBlack = (i + j) % 2 == 1;
                if (j == 0) {
                    System.out.print(SET_BG_COLOR_LIGHT_GREY);
                    System.out.print(" ");
                    System.out.print(8-i);
                    System.out.print(" ");
                }
                if (isBlack) {
                    if (board[7 - i][j] != EMPTY) {
                        String bg = SET_BG_COLOR_DARK_GREEN;
                        for (ChessMove x :moves){
                            if (8-x.getEndPosition().getRow() == i && x.getEndPosition().getColumn()-1 ==j){
                                bg = SET_BG_COLOR_GREEN;
                            }
                            if (8-x.getStartPosition().getRow() ==i && x.getStartPosition().getColumn()-1 ==j){
                                bg = SET_BG_COLOR_YELLOW;
                            }
                        }
                        System.out.print(bg);
                        System.out.print(board[7 - i][ j]);
                    } else {
                        String bg = SET_BG_COLOR_DARK_GREEN;
                        for (ChessMove x :moves){
                            if (8-x.getEndPosition().getRow() ==i && x.getEndPosition().getColumn()-1 ==j){
                                bg = SET_BG_COLOR_GREEN;
                            }

                        }
                        System.out.print(bg);
                        System.out.print(EMPTY);
                    }
                } else {
                    if (board[7 - i][j] != EMPTY) {
                        String background = SET_BG_COLOR_DARK_GREY;
                        for (ChessMove x :moves){
                            if (8-x.getEndPosition().getRow() == i && x.getEndPosition().getColumn()-1 ==j){
                                background = SET_BG_COLOR_GREEN;
                            }
                            if (8-x.getStartPosition().getRow() == i && x.getStartPosition().getColumn()-1 ==j){
                                background = SET_BG_COLOR_YELLOW;
                            }
                        }
                        System.out.print(background);
                        System.out.print(SET_TEXT_COLOR_WHITE);
                        System.out.print(board[7 - i][j]);
                    } else {
                        String background = SET_BG_COLOR_DARK_GREY;
                        for (ChessMove x :moves){
                            if (8-x.getEndPosition().getRow()== i && x.getEndPosition().getColumn()-1 ==j){
                                background = SET_BG_COLOR_GREEN;
                            }

                        }
                        System.out.print(background);
                        System.out.print(EMPTY);
                    }
                }
                System.out.print(SET_TEXT_COLOR_WHITE);
                if (j == 7) {
                    System.out.print(SET_BG_COLOR_LIGHT_GREY);
                    System.out.print(" ");
                    System.out.print(8-i);
                    System.out.print(" ");
                }

            }
            System.out.print(SET_BG_COLOR_DARK_GREY);
            System.out.println();
        }
        System.out.print(SET_BG_COLOR_LIGHT_GREY);
        System.out.print("    ");

        for (int i = 0; i < 8; i++) {
            System.out.print(SET_BG_COLOR_LIGHT_GREY);
            char x = (char) ('a' + i);
            System.out.print( x+ "  " );
        }
        System.out.print(SET_BG_COLOR_LIGHT_GREY);
        System.out.print("  ");
        System.out.print(SET_BG_COLOR_DARK_GREY);
        System.out.println();
    }

    public void blackMoves(Collection<ChessMove> moves){
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        out.print(ERASE_SCREEN);
        System.out.print(SET_TEXT_COLOR_WHITE);
        System.out.print(SET_BG_COLOR_LIGHT_GREY);
        System.out.print("    ");
        for (int i = 0; i < 8; i++) {
            System.out.print(SET_BG_COLOR_LIGHT_GREY);
            char x = (char) ('a' + i);
            System.out.print(x + "  ");
        }
        System.out.print(SET_BG_COLOR_LIGHT_GREY);
        System.out.print("  ");
        System.out.print(SET_BG_COLOR_DARK_GREY);
        System.out.println();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                boolean isBlack = (i + j) % 2 == 1;
                if (j == 0) {
                    System.out.print(SET_BG_COLOR_LIGHT_GREY);
                    System.out.print(" ");
                    System.out.print(1+i);
                    System.out.print(" ");
                }
                if (isBlack) {
                    System.out.print(SET_BG_COLOR_DARK_GREEN);
                    if (board[i][j] != EMPTY) {
                        String background = SET_BG_COLOR_DARK_GREEN;
                        for (ChessMove x :moves){
                            if (x.getEndPosition().getRow()-1 == i && x.getEndPosition().getColumn() -1==j){
                                background = SET_BG_COLOR_GREEN;
                            }
                            if (x.getStartPosition().getRow()-1 == i && x.getStartPosition().getColumn()-1 ==j){
                                background = SET_BG_COLOR_YELLOW;
                            }
                        }
                        System.out.print(background);
                        System.out.print(SET_TEXT_COLOR_WHITE);
                        System.out.print(board[i][j]);
                    } else {
                        String background = SET_BG_COLOR_DARK_GREEN;
                        for (ChessMove x :moves){
                            if (x.getEndPosition().getRow()-1 == i && x.getEndPosition().getColumn()-1==j){
                                background = SET_BG_COLOR_GREEN;
                            }
                            if (x.getStartPosition().getRow()-1 == i && x.getStartPosition().getColumn()-1==j){
                                background = SET_BG_COLOR_YELLOW;
                            }
                        }
                        System.out.print(background);
                        System.out.print(EMPTY);
                    }
                } else {
                    System.out.print(SET_BG_COLOR_DARK_GREY);
                    if (board[i][j] != EMPTY) {
                        String background = SET_BG_COLOR_DARK_GREY;
                        for (ChessMove x :moves){
                            if (x.getEndPosition().getRow()-1  == i && x.getEndPosition().getColumn()-1 ==j){
                                background = SET_BG_COLOR_GREEN;
                            }
                            if (x.getStartPosition().getRow()-1 == i && x.getStartPosition().getColumn()-1==j){
                                background = SET_BG_COLOR_YELLOW;
                            }
                        }
                        System.out.print(background);
                        System.out.print(SET_TEXT_COLOR_WHITE);
                        System.out.print(board[i][j]);
                    } else {
                        String background = SET_BG_COLOR_DARK_GREY;
                        for (ChessMove x :moves){
                            if (x.getEndPosition().getRow()-1 == i && x.getEndPosition().getColumn()-1 ==j){
                                background = SET_BG_COLOR_GREEN;
                            }
                            if (x.getStartPosition().getRow()-1 == i && x.getStartPosition().getColumn()-1 ==j){
                                background = SET_BG_COLOR_YELLOW;
                            }
                        }
                        System.out.print(background);
                        System.out.print(EMPTY);
                    }
                }
                System.out.print(SET_TEXT_COLOR_WHITE);
                if (j == 7) {
                    System.out.print(SET_BG_COLOR_LIGHT_GREY);
                    System.out.print(" ");
                    System.out.print(1+i);
                    System.out.print(" ");
                }

            }
            System.out.print(SET_BG_COLOR_DARK_GREY);
            System.out.println();
        }
        System.out.print(SET_BG_COLOR_LIGHT_GREY);
        System.out.print("    ");
        for (int i = 0; i < 8; i++) {
            System.out.print(SET_BG_COLOR_LIGHT_GREY);
            char x = (char) ('a' + i);
            System.out.print(x + "  ");
        }
        System.out.print(SET_BG_COLOR_LIGHT_GREY);
        System.out.print("  ");
        System.out.print(SET_BG_COLOR_BLACK);
        System.out.println();
    }

    public void updateUIBoard(ChessGame game){
        Board boardNEW = (Board) game.getBoard();
        for(int i =1; i<9;i++){
            for(int j =1; j<9; j++){
                if (boardNEW.getBoard()[i][j] == null){
                    board[j-1][i-1] = EMPTY;
                }
                else if (boardNEW.getBoard()[i][j].getPieceType() == ChessPiece.PieceType.PAWN && boardNEW.getBoard()[i][j].getTeamColor() == ChessGame.TeamColor.WHITE ){
                    board[j-1][i-1] = WHITE_PAWN;
                }
                else if (boardNEW.getBoard()[i][j].getPieceType() == ChessPiece.PieceType.KNIGHT && boardNEW.getBoard()[i][j].getTeamColor() == ChessGame.TeamColor.WHITE ){
                    board[j-1][i-1] = WHITE_KNIGHT;
                }
                else if (boardNEW.getBoard()[i][j].getPieceType() == ChessPiece.PieceType.ROOK && boardNEW.getBoard()[i][j].getTeamColor() == ChessGame.TeamColor.WHITE ){
                    board[j-1][i-1] = WHITE_ROOK;
                }
                else if (boardNEW.getBoard()[i][j].getPieceType() == ChessPiece.PieceType.KING && boardNEW.getBoard()[i][j].getTeamColor() == ChessGame.TeamColor.WHITE ){
                    board[j-1][i-1] = WHITE_KING;
                }
                else if (boardNEW.getBoard()[i][j].getPieceType() == ChessPiece.PieceType.QUEEN && boardNEW.getBoard()[i][j].getTeamColor() == ChessGame.TeamColor.WHITE ){
                    board[j-1][i-1] = WHITE_QUEEN;
                }
                else if (boardNEW.getBoard()[i][j].getPieceType() == ChessPiece.PieceType.BISHOP && boardNEW.getBoard()[i][j].getTeamColor() == ChessGame.TeamColor.WHITE ){
                    board[j-1][i-1] = WHITE_BISHOP;
                }
                else if (boardNEW.getBoard()[i][j].getPieceType() == ChessPiece.PieceType.PAWN && boardNEW.getBoard()[i][j].getTeamColor() == ChessGame.TeamColor.BLACK ){
                    board[j-1][i-1] = BLACK_PAWN;
                }
                else if (boardNEW.getBoard()[i][j].getPieceType() == ChessPiece.PieceType.KNIGHT && boardNEW.getBoard()[i][j].getTeamColor() == ChessGame.TeamColor.BLACK ){
                    board[j-1][i-1] = BLACK_KNIGHT;
                }
                else if (boardNEW.getBoard()[i][j].getPieceType() == ChessPiece.PieceType.ROOK && boardNEW.getBoard()[i][j].getTeamColor() == ChessGame.TeamColor.BLACK ){
                    board[j-1][i-1] = BLACK_ROOK;
                }
                else if (boardNEW.getBoard()[i][j].getPieceType() == ChessPiece.PieceType.KING && boardNEW.getBoard()[i][j].getTeamColor() == ChessGame.TeamColor.BLACK ){
                    board[j-1][i-1] = BLACK_KING;
                }
                else if (boardNEW.getBoard()[i][j].getPieceType() == ChessPiece.PieceType.QUEEN && boardNEW.getBoard()[i][j].getTeamColor() == ChessGame.TeamColor.BLACK ){
                    board[j-1][i-1] = BLACK_QUEEN;
                }
                else if (boardNEW.getBoard()[i][j].getPieceType() == ChessPiece.PieceType.BISHOP && boardNEW.getBoard()[i][j].getTeamColor() == ChessGame.TeamColor.BLACK ){
                    board[j-1][i-1] = BLACK_BISHOP;
                }

            }
        }
    }

}