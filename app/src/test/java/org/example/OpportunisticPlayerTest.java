package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class OpportunisticPlayerTest {

    @Test
    void firstMoveShouldPickCorner() {
        Board b = new Board();
        OpportunisticPlayer ai = new OpportunisticPlayer('X');
        int move = ai.getMove(b);
        assertTrue(move == 1 || move == 3 || move == 7 || move == 9);
    }

    @Test
    void secondMoveShouldPickCenterIfAvailable() {
        Board b = new Board();
        b.playMove(1, 'O');  // human moves in corner
        OpportunisticPlayer ai = new OpportunisticPlayer('X');
        int move = ai.getMove(b);
        assertEquals(5, move);  // center is move 5
    }

    @Test
    void shouldTakeWinningMove() {
        Board b = new Board();
        b.playMove(1, 'X');
        b.playMove(4, 'O');
        b.playMove(2, 'X');
        b.playMove(5, 'O');
        OpportunisticPlayer ai = new OpportunisticPlayer('X');
        int move = ai.getMove(b);
        assertEquals(3, move);  // win by completing top row
    }

    @Test
    void shouldBlockOpponentWinningMove() {
        Board b = new Board();
        b.playMove(1, 'O');
        b.playMove(2, 'O');
        b.playMove(4, 'X');
        OpportunisticPlayer ai = new OpportunisticPlayer('X');
        int move = ai.getMove(b);
        assertEquals(3, move);  // block O's win
    }

    @Test
    void shouldPickAnyValidMoveIfNoWinOrBlock() {
        Board b = new Board();
        b.playMove(5, 'O');
        b.playMove(1, 'X');
        b.playMove(9, 'O');
        OpportunisticPlayer ai = new OpportunisticPlayer('X');
        int move = ai.getMove(b);
        assertTrue(move >= 1 && move <= 9);
        assertEquals(' ', b.getCells()[move - 1]);  // move is on an empty square
    }
}
