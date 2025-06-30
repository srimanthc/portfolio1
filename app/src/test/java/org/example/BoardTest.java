package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    @Test   // X wins across the top row
    void rowWin() {
        Board b = new Board();
        b.playMove(1,'X'); b.playMove(4,'O');
        b.playMove(2,'X'); b.playMove(5,'O');
        b.playMove(3,'X');
        assertTrue(b.isFinished());
        assertEquals('X', b.getWinner());
    }

    @Test   // O wins down the first column
    void columnWin() {
        Board b = new Board();
        b.playMove(1,'O'); b.playMove(2,'X');
        b.playMove(4,'O'); b.playMove(5,'X');
        b.playMove(7,'O');
        assertTrue(b.isFinished());
        assertEquals('O', b.getWinner());
    }

    @Test   // X wins on main diagonal
    void diagonalWin() {
        Board b = new Board();
        b.playMove(1,'X'); b.playMove(2,'O');
        b.playMove(5,'X'); b.playMove(3,'O');
        b.playMove(9,'X');
        assertTrue(b.isFinished());
        assertEquals('X', b.getWinner());
    }

    @Test   // tie game (board full, no winner)
    void tieGame() {
        Board b = new Board();
        int[] cells = {1,2,3,5,4,6,8,7,9};
        char[] marks = {'X','O','X','O','O','X','X','X','O'};
        for (int i=0;i<9;i++) b.playMove(cells[i], marks[i]);
        assertTrue(b.isFinished());
        assertEquals('T', b.getWinner());
    }

    @Test   // reject move on occupied square
    void invalidMove() {
        Board b = new Board();
        assertTrue(b.playMove(1,'X'));
        assertFalse(b.playMove(1,'O'));     // same spot
        assertFalse(b.isFinished());        // game not over
    }
}
