package org.example;

public class Score {
    private int xWins = 0;
    private int oWins = 0;
    private int ties  = 0;

    public void record(char result) {
        switch (result) {
            case 'X' -> xWins++;
            case 'O' -> oWins++;
            case 'T' -> ties++;
        }
    }
    public void printLog() {
        System.out.printf("\nPlayer X Wins   %d\n", xWins);
        System.out.printf("Player O Wins   %d\n", oWins);
        System.out.printf("Ties            %d\n\n", ties);
    }
    public String toFileString() {
        return "Final Tic-Tac-Toe Log\n"
             + "Player X Wins: " + xWins + "\n"
             + "Player O Wins: " + oWins + "\n"
             + "Ties: " + ties   + "\n";
    }
    public char lastWinner() {
        // no rounds played yet
        if (xWins + oWins + ties == 0) {
            return ' ';
        }
        // X is ahead
        if (xWins > oWins) {
            return 'X';
        }
        // O is ahead
        if (oWins > xWins) {
            return 'O';
        }
        // otherwise it’s tied
        return 'T';
    }
    
}
