package org.ludo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.ludo.gameLogic.Piece;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PieceTest {
    @Test
    @DisplayName("Creating a new piece")
    public void testFreshPieceConstructor() {
        Piece piece = new Piece(0, 0);
        assertEquals(0, piece.getPosIndex());
        assertEquals("yard", piece.getBoardArea());
    }

    @Test
    @DisplayName("Creating a new piece with defined position on initialization")
    public void testLoadedPieceConstructor() {
        Piece piece = new Piece(0, 0, 10, "gameTrack");
        assertEquals(10, piece.getPosIndex());
        assertEquals("gameTrack", piece.getBoardArea());
    }
}
