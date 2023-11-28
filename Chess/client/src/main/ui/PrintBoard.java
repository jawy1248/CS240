package ui;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Random;

import static ui.EscapeSequences.*;

public class PrintBoard {
//    private static final int BOARD_SIZE = 8;
//    private static final int SQUARE_SIZE = 1;
//    private static final int LINE_SIZE = 1;

    private static Random rand = new Random();

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

        System.out.print(SET_TEXT_COLOR_WHITE);
        System.out.print(SET_BG_COLOR_LIGHT_GREY);
        System.out.print("       ");
        for(int i=0; i<8; i++){
            System.out.print(SET_BG_COLOR_LIGHT_GREY);
            char x = (char) ('a' + i);
            System.out.print(x + " ");
        }
        System.out.print(SET_BG_COLOR_LIGHT_GREY);
        System.out.print("   ");
        System.out.print(SET_BG_COLOR_DARK_GREY);
        System.out.println();

        for(int i=0; i<8; i++){
            for(int j=0; j<8; j++){
                boolean white = ( (i + j) % 2 == 0 );
                if(j == 0){
                    System.out.print(SET_BG_COLOR_LIGHT_GREY);
                    System.out.print(" ");
                    System.out.print(1+i);
                    System.out.print(" ");
                }
                if(!white){
                    System.out.print(SET_BG_COLOR_MAGENTA);
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
        System.out.print("       ");
        for(int i=0; i<8; i++){
            System.out.print(SET_BG_COLOR_LIGHT_GREY);
            char x = (char) ('a' + i);
            System.out.print(x + " ");
        }
        System.out.print(SET_BG_COLOR_LIGHT_GREY);
        System.out.print("   ");
        System.out.print(SET_BG_COLOR_DARK_GREY);
        System.out.println();
    }
}
