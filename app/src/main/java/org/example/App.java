package org.example;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Score log = new Score();
        char currentFirst = 'X';  // X goes first in the first round

        System.out.println("Welcome to Tic-Tac-Toe!\n");
        System.out.println("What kind of game would you like to play?\n");
        System.out.println("1. Human vs. Human");
        System.out.println("2. Human vs. Computer");
        System.out.println("3. Computer vs. Human");

        int mode = 0;
        while (mode < 1 || mode > 3) {
            System.out.print("\nWhat is your selection? ");
            try {
                mode = Integer.parseInt(sc.nextLine().trim());
                if (mode < 1 || mode > 3) {
                    System.out.println("Please enter 1, 2, or 3.");
                }
            } catch (NumberFormatException e) {
                System.out.println("That is not a valid number. Try again.");
            }
        }

        boolean playAgain = true;

        while (playAgain) {
            Board board = new Board();
            char turn = currentFirst;
            OpportunisticPlayer ai = null;

            // Set up AI player if needed
            if (mode != 1) {
                char aiMark;
                if (mode == 2) {
                    aiMark = 'O';
                } else {
                    aiMark = 'X';
                }
                ai = new OpportunisticPlayer(aiMark);

                if (aiMark == 'X') {
                    System.out.println("\nGreat! The computer will go first.\n");
                } else {
                    System.out.println("\nGreat! The human will go first.\n");
                }
            }

            // Play one round
            while (!board.isFinished()) {
                board.printBoard();

                boolean isHumanTurn = false;
                if (mode == 1) {
                    isHumanTurn = true;  // Human vs Human
                } else if (mode == 2 && turn == 'X') {
                    isHumanTurn = true;  // Human is X
                } else if (mode == 3 && turn == 'O') {
                    isHumanTurn = true;  // Human is O
                }

                if (isHumanTurn) {
                    System.out.print("\n" + turn + ", choose your move (1-9): ");
                    String input = sc.nextLine().trim();
                    try {
                        int pos = Integer.parseInt(input);
                        boolean success = board.playMove(pos, turn);
                        if (!success) {
                            System.out.println("Invalid move. Try again.");
                            continue;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Please enter a number between 1 and 9.");
                        continue;
                    }
                } else {
                    int aiMove = ai.getMove(board);
                    System.out.println("\nComputer chooses: " + aiMove);
                    board.playMove(aiMove, turn);
                }

                // Switch turn
                if (turn == 'X') {
                    turn = 'O';
                } else {
                    turn = 'X';
                }
            }

            // Round over
            board.printBoard();
            char result = board.getWinner();

            if (result == 'T') {
                System.out.println("\nIt’s a tie!");
            } else {
                System.out.println("\nPlayer " + result + " wins!");
            }

            log.record(result);
            log.printLog();

            // Decide who goes first next round
            if (result == 'T') {
                if (currentFirst == 'X') {
                    currentFirst = 'O';
                } else {
                    currentFirst = 'X';
                }
            } else {
                if (result == 'X') {
                    currentFirst = 'O';
                } else {
                    currentFirst = 'X';
                }
            }

            // Ask if user wants to play again
            System.out.print("Would you like to play again (yes/no)? ");
            String againInput = sc.nextLine().trim();
            if (againInput.equalsIgnoreCase("yes")) {
                playAgain = true;
                System.out.println("\nGreat! This time " + currentFirst + " will go first!\n");
            } else {
                playAgain = false;
            }
        }

        // Save the log to a file
        System.out.println("\nWriting the game log to disk...");
        try {
            FileWriter writer = new FileWriter("game.txt");
            writer.write(log.toFileString());
            writer.close();
            System.out.println("Saved to game.txt");
        } catch (IOException e) {
            System.out.println("Error writing file: " + e.getMessage());
        }

        sc.close();
    }
}
