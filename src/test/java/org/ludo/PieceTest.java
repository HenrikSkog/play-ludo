package org.ludo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.ludo.gamelogic.Areas;
import org.ludo.gamelogic.Piece;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PieceTest {
    @Test
    @DisplayName("Creating a new piece")
    public void testFreshPieceConstructor() {
        Piece piece = new Piece(0, 0);
        assertEquals(0, piece.getPosIndex());
        assertEquals(Areas.YARD, piece.getBoardArea());
    }

    @Test
    @DisplayName("Creating a new piece with defined position on initialization")
    public void testLoadedPieceConstructor() {
        Piece piece = new Piece(0, 0, 10, Areas.GAMETRACK);
        assertEquals(10, piece.getPosIndex());
        assertEquals(Areas.GAMETRACK, piece.getBoardArea());
    }
}
