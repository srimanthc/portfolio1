package org.example;

import java.util.Arrays;

public class Board {
    private final char[] cells = new char[9]; // index 0..8
    private char winner = ' ';                // 'X','O', or 'T' for tie

    public Board() {
        Arrays.fill(cells, ' ');              // empty board
    }

    public boolean playMove(int pos, char mark) {
        if (pos < 1 || pos > 9) return false; // invalid index
        if (cells[pos - 1] != ' ') return false;
        cells[pos - 1] = mark;
        computeWinner();
        return true;
    }

    public boolean isFinished() {
        return winner != ' ';
    }

    public char getWinner() {
        return winner;
    }

    private void computeWinner() {
        int[][] lines = {
            {0,1,2},{3,4,5},{6,7,8}, // rows
            {0,3,6},{1,4,7},{2,5,8}, // cols
            {0,4,8},{2,4,6}          // diags
        };
        for (int[] ln : lines) {
            char c = cells[ln[0]];
            if (c != ' ' && c == cells[ln[1]] && c == cells[ln[2]]) {
                winner = c;
                return;
            }
        }
        // tie?
        boolean full = true;
        for (char c : cells) if (c == ' ') full = false;
        if (full) winner = 'T';
    }

    public void printBoard() {
        System.out.println();
        for (int i = 0; i < 9; i++) {
            char cell = (cells[i] == ' ') ? Character.forDigit(i + 1, 10) : cells[i];
            System.out.print(" " + cell + " ");          // one space on each side
            if (i % 3 != 2) System.out.print("|");
            else if (i != 8) System.out.print("\n---+---+---\n");
        }
        System.out.println();
    }
    
}
