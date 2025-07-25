package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class OpportunisticPlayer {
    private final char aiMark;
    private final char opponentMark;
    private final Random rand = new Random();

    public OpportunisticPlayer(char aiMark) {
        this.aiMark = aiMark;
        if (aiMark == 'X') {
            this.opponentMark = 'O';
        } else {
            this.opponentMark = 'X';
        }
    }

    public int getMove(Board board) {
        char[] cells = board.getCells();
        int moveCount = board.getMoveCount();

        // Step 1: First move of the game - pick a corner
        if (moveCount == 0) {
            int[] corners = {1, 3, 7, 9};
            return pickRandomFromList(corners, cells);
        }

        // Step 2: Second move of the game - take center if available
        if (moveCount == 1 && cells[4] == ' ') {
            return 5;
        }

        // Step 3: Try to win
        for (int i = 0; i < 9; i++) {
            if (cells[i] == ' ') {
                cells[i] = aiMark;
                if (isWinningMove(cells, aiMark)) {
                    cells[i] = ' ';
                    return i + 1;
                }
                cells[i] = ' ';
            }
        }

        // Step 4: Try to block opponent
        for (int i = 0; i < 9; i++) {
            if (cells[i] == ' ') {
                cells[i] = opponentMark;
                if (isWinningMove(cells, opponentMark)) {
                    cells[i] = ' ';
                    return i + 1;
                }
                cells[i] = ' ';
            }
        }


        // Step 5: Pick a random empty space
        List<Integer> empty = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            if (cells[i] == ' ') {
                empty.add(i + 1);
            }
        }
        return empty.get(rand.nextInt(empty.size()));
    }

    private int pickRandomFromList(int[] positions, char[] cells) {
        List<Integer> available = new ArrayList<>();
        for (int pos : positions) {
            if (cells[pos - 1] == ' ') {
                available.add(pos);
            }
        }
        return available.get(rand.nextInt(available.size()));
    }

    private boolean isWinningMove(char[] cells, char mark) {
        int[][] lines = {
            {0,1,2}, {3,4,5}, {6,7,8}, // rows
            {0,3,6}, {1,4,7}, {2,5,8}, // cols
            {0,4,8}, {2,4,6}           // diagonals
        };
        for (int[] line : lines) {
            if (cells[line[0]] == mark && cells[line[1]] == mark && cells[line[2]] == mark) {
                return true;
            }
        }
        return false;
    }
    
}
