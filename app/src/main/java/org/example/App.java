package org.example;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Score log = new Score();
        char currentFirst = 'X';               // X starts first in round 1

        System.out.println("Welcome to Tic-Tac-Toe!\n");

        boolean again = true;
        while (again) {
            Board board = new Board();
            char turn = currentFirst;

            // main round loop
            while (!board.isFinished()) {
                board.printBoard();
                System.out.print("\n" + turn + ", choose your move (1-9): ");
                String in = sc.nextLine().trim();
                try {
                    int pos = Integer.parseInt(in);
                    if (!board.playMove(pos, turn)) {
                        System.out.println("Invalid move, try again.");
                        continue;
                    }
                    turn = (turn == 'X') ? 'O' : 'X';
                } catch (NumberFormatException nfe) {
                    System.out.println("Please enter a number 1-9.");
                }
            }

            // round ended
            board.printBoard();
            char result = board.getWinner();
            if (result == 'T') System.out.println("\nIt’s a tie!");
            else System.out.println("\nPlayer " + result + " wins!");
            log.record(result);
            System.out.println("The current log is:");
            log.printLog();

            // decide first player for next round
            if (result == 'T') {
                currentFirst = (currentFirst == 'X') ? 'O' : 'X'; // just swap
            } else {
                currentFirst = (result == 'X') ? 'O' : 'X';       // loser goes first
            }

            // ask to play again
            System.out.print("Would you like to play again (yes/no)? ");
            again = sc.nextLine().trim().equalsIgnoreCase("yes");
            if (again) {
                System.out.println("\nGreat! This time " + currentFirst + " will go first!\n");
            }
        }

        // save log to disk
        System.out.println("\nWriting the game log to disk...");
        try (FileWriter fw = new FileWriter("game.txt")) {
            fw.write(log.toFileString());
            System.out.println("Saved to game.txt");
        } catch (IOException e) {
            System.out.println("Error writing file: " + e.getMessage());
        }
        sc.close();
    }
}
