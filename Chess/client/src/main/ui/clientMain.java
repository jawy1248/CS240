package ui;

import static ui.EscapeSequences.*;
import java.util.Scanner;

public class clientMain {
    public static void main(String[] args) throws Exception {
        // Print starting message
        System.out.println("\n" + SET_TEXT_COLOR_WHITE + WHITE_KING + "Welcome to Chess" + WHITE_KING + "\nType " + SET_TEXT_COLOR_GREEN + "help" + SET_TEXT_COLOR_WHITE + " to get started\n");
        // Set scanner to read in text from user command line
        Scanner scanner = new Scanner(System.in);
        // Set up a new chess Client
        ChessClient client = new ChessClient();
        // Set up  a control variables to end loop when user quits
        boolean control = true;
        // Until "Quit" occurs, keep running program
        String status = "[PRE-LOG IN]";
        while(control){
            System.out.print(SET_TEXT_COLOR_WHITE + status + " >>> " + SET_TEXT_COLOR_GREEN);
            // Read the line and send it to the client
            String temp = scanner.nextLine();
            temp = temp.toLowerCase();
            System.out.print(SET_TEXT_COLOR_WHITE);
            status = client.command(temp);
            System.out.println();
            // Exit program if user says "quit"
            if(temp.contains("quit"))
                control = false;
        }
    }
}
