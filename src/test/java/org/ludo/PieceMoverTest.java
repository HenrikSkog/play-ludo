package org.ludo;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.ludo.gamelogic.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class PieceMoverTest {
    PieceMover pieceMover;
    Piece piece1;
    ArrayList<Player> players = new ArrayList<>();

    @BeforeEach
    public void init() {
        for (int i = 0; i < 4; i++) {
            Player player = new Player("player", i);
            player.initializePieces();
            players.add(player);
        }

        this.piece1 = players.get(0).getPieces().get(0);
        this.pieceMover = new PieceMover(players);
    }

   private interface PieceModifier {
        void apply(Piece piece);
   }

   private void applyToPieces(PieceModifier method) {
       players.forEach(player -> player.getPieces().forEach(method::apply));
   }

    private void assertPosition(int expectedPosition, Areas expectedArea, int actualPosition, Areas actualArea) {
        assertEquals(expectedPosition, actualPosition);
        assertEquals(expectedArea, actualArea);
    }

    @DisplayName("Testing setting board area on a piece")
    @Test
    public void testSetBoardArea() {
        //should be yard at start
        assertEquals(Areas.YARD, piece1.getBoardArea());

        //if player gets 6, should move out to gametrack
        applyToPieces(piece -> pieceMover.move(piece, 6));
        applyToPieces(piece -> assertEquals(Areas.GAMETRACK, piece.getBoardArea()));




        //when moving past entrance, should go into home column
        piece1.setBoardArea(Areas.GAMETRACK);
        piece1.setPosIndex(51);
        pieceMover.move(piece1, 5);
        assertEquals(Areas.HOMECOLUMN, piece1.getBoardArea());

        //when moving to goal, area should be goal
        piece1.setBoardArea(Areas.HOMECOLUMN);
        piece1.setPosIndex(4);
        pieceMover.move(piece1, 5);
        assertEquals(Areas.GOAL, piece1.getBoardArea());
    }

    @Test
    @DisplayName("Moving out of yard")
    public void outOfYardTest() {
        applyToPieces(piece -> {
            pieceMover.move(piece, 4);
            assertEquals(Areas.YARD, piece.getBoardArea());

            pieceMover.move(piece, 6);
            assertPosition(piece.getColorIndex()*13, Areas.GAMETRACK, piece.getPosIndex(), piece.getBoardArea());
        });
    }

    @Test
    @DisplayName("Moving on the gameTrack")
    public void moveOnGameTrack() {
        applyToPieces(piece -> {
            pieceMover.move(piece, 6);
            pieceMover.move(piece, 4);
            assertPosition(piece.getColorIndex()*13+4, Areas.GAMETRACK, piece.getPosIndex(), piece.getBoardArea());
        });
    }

    @Test
    @DisplayName("Moving into home column")
    public void intoHomeColumn() {
        //setting each piece to 1 before home column entrance
        applyToPieces(piece -> {
            piece.setBoardArea(Areas.GAMETRACK);
            if(piece.getColorIndex() != 0) piece.setPosIndex(piece.getColorIndex()*13-1);
        });

        players.get(0).getPieces().forEach(piece -> piece.setPosIndex(51));

        applyToPieces(piece -> {
            //moving each peace 4 forward, should enter home column with 4
            pieceMover.move(piece, 4);
            assertEquals(Areas.HOMECOLUMN, piece.getBoardArea());
            assertEquals(piece.getColorIndex()*6+3, piece.getPosIndex());
        });
    }

    @Test
    @DisplayName("Move into goal with exactly right die result")
    public void intoGoalExactDie() {
        //setting each piece to 1 before goal
        applyToPieces(piece -> {
            piece.setBoardArea(Areas.HOMECOLUMN);
            piece.setPosIndex(piece.getColorIndex() * 6 + 5);

            pieceMover.move(piece, 1);
            assertEquals(Areas.GOAL, piece.getBoardArea());
        });
    }

    @Test
    @DisplayName("Move into goal with actually too high die result")
    public void intoGoalTooHighDie() {
        //setting each piece to 1 before goal
        applyToPieces(piece -> {
            piece.setBoardArea(Areas.HOMECOLUMN);
            piece.setPosIndex(piece.getColorIndex() * 6 + 5);

            pieceMover.move(piece, 4);
            assertEquals(Areas.GOAL, piece.getBoardArea());
        });
    }

    @Test
    @DisplayName("Landing on another piece")
    public void testLandingOnPiece() {
        applyToPieces(piece -> {
           piece.setBoardArea(Areas.GAMETRACK);
           piece.setPosIndex(30);
        });
        var testingPlayer = players.get(0);

        piece1.setPosIndex(29);

        pieceMover.move(piece1, 1);
        applyToPieces(piece -> {
            if(piece.getColorIndex() != testingPlayer.getColorIndex()) assertPosition(piece.getInitialPosIndex(), Areas.YARD, piece.getPosIndex(), piece.getBoardArea());
            else assertPosition(30, Areas.GAMETRACK, piece.getPosIndex(), piece.getBoardArea());
        });
    }
}