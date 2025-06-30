package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ScoredTest {

    @Test
    void recordAndReport() {
        Score sb = new Score();
        sb.record('X');   // X win
        sb.record('O');   // O win
        sb.record('O');   // O win
        sb.record('T');   // tie

        String out = sb.toFileString();
        assertTrue(out.contains("Player X Wins: 1"));
        assertTrue(out.contains("Player O Wins: 2"));
        assertTrue(out.contains("Ties: 1"));
    }
}
