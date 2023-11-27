package ui;

import ui.ChessClient;
import java.util.Scanner;

public class clientMain {
    public static void main(String[] args) throws Exception {
        // Print starting message
        System.out.println("CHESS");
        // Set scanner to read in text from user command line
        Scanner scanner = new Scanner(System.in);
        // Set up a new chess Client
        ChessClient client = new ChessClient();
        // Set up  a control variables to end loop when user quits
        boolean control = true;
        // Until "Quit" occurs, keep running program
        while(control){
            // Read the line and send it to the client
            String temp = scanner.nextLine();
            System.out.println(client.command(temp));
            // Exit program if user says "Quit"
            if(temp.equals("Quit"))
                control = false;
        }
    }
}
