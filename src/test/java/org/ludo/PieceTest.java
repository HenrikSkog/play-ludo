package org.ludo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.ludo.gameLogic.Piece;
import org.ludo.gameLogic.Player;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PieceTest {
    Piece piece;
    Piece piece2;
    ArrayList<Player> players = new ArrayList<>();
    Player player;

    @BeforeEach
    public void init() {
        String[] names = new String[]{
                "Henrik", "Erlend", "Sebastian", "Julian"
        };
        for (int i = 0; i < 4; i++) {
            var player = new Player(names[i], i);
            player.initializePieces();
            players.add(player);
        }
        piece = players.get(0).getPieces().get(0);
        piece2 = players.get(1).getPieces().get(0);
        player = players.get(0);
    }

    private void testPosition(int expectedPosition, String expectedArea, int actualPosition, String actualArea) {
        assertEquals(expectedPosition, actualPosition);
        assertEquals(expectedArea, actualArea);
    }

    //Test all pieces to move on the HomeColumn
    @Test
    public void testColor0OnHomeColumn() {
        Piece greenPiece = new Piece(0, 0);
        greenPiece.setBoardArea("homeColumn");
        greenPiece.moveOnHomeColumn(3);
        assertEquals(3, greenPiece.getPosIndex());
        assertEquals("homeColumn", greenPiece.getBoardArea());
    }

    @Test
    public void testColor1MoveOnHomeColumn() {
        Piece bluePiece = new Piece(1, 0);
        bluePiece.setBoardArea("homeColumn");
        bluePiece.moveOnHomeColumn(1);
        assertEquals(1, bluePiece.getPosIndex());
        assertEquals("homeColumn", bluePiece.getBoardArea());
    }

    @Test
    public void testColor2MoveOnHomeColumn() {
        Piece redPiece = new Piece(2, 2);
        redPiece.setBoardArea("homeColumn");
        redPiece.moveOnHomeColumn(2);
        assertEquals(4, redPiece.getPosIndex());
        assertEquals("homeColumn", redPiece.getBoardArea());
    }

    @Test
    public void testColor3MoveOnHomeColumn() {
        Piece yellowPiece = new Piece(3, 4);
        yellowPiece.setBoardArea("homeColumn");
        yellowPiece.moveOnHomeColumn(1);
        assertEquals(5, yellowPiece.getPosIndex());
        assertEquals("homeColumn", yellowPiece.getBoardArea());
    }

    @DisplayName("Moving piece to goal")
    @Test
    public void testMoveToGoal() {
        piece.setBoardArea("homeColumn");
        piece.setPosIndex(0);
        piece.moveOnHomeColumn(6);
        assertEquals("goal", piece.getBoardArea());
    }

    @DisplayName("Move piece to home column")
    @Test
    public void moveToHomeColumn() {
        piece.setBoardArea("gameTrack");
        piece.setPosIndex(49);
        piece.moveOnGameTrack(4);
        System.out.println(piece.getPosIndex());
        assertEquals("homeColumn", piece.getBoardArea());
        assertEquals(1, piece.getPosIndex());
    }

    @DisplayName("Move piece into home column")
    @Test
    public void testWillPassHomeColumnEntranceWith() {
        piece.setBoardArea("gameTrack");
        piece.setPosIndex(0);
        assertEquals(-1, piece.willPassHomeColumnEntranceWith(4));

        piece.setPosIndex(50);
        assertEquals(4, piece.willPassHomeColumnEntranceWith(6));

        piece2.setBoardArea("gameTrack");
        piece2.setPosIndex(9);

        assertEquals(2, piece2.willPassHomeColumnEntranceWith(6));
    }


    @Test
    @DisplayName("Test for setting Illegal boardArea")
    public void setBoardArea() {
        assertThrows(IllegalArgumentException.class, () -> piece.setBoardArea("hei"));
    }

    @Test
    public void testSetPosIndex() {
        assertEquals(0, piece.getPosIndex());

        piece.setPosIndex(10);
        assertEquals(10, piece.getPosIndex());

        piece.setBoardArea("yard");
        assertThrows(IllegalArgumentException.class, () -> piece.setPosIndex(20));
    }


    @Test
    @DisplayName("Test for moving pieces out of yard")
    public void testMovePiecesOutOfYard() {
        piece.moveToGameTrack(6);
        assertEquals("gameTrack", piece.getBoardArea());
    }

    @Test
    public void testSetBoardArea() {
        assertThrows(IllegalArgumentException.class, () -> piece.setBoardArea("hei"));
    }

    @Test
    @DisplayName("Moving into yard from gameTrack")
    public void testMovingIntoYard() {
        piece.setBoardArea("gameTrack");
        piece.setPosIndex(5);
        piece2.setBoardArea("gameTrack");
        piece2.setPosIndex(2);
        piece2.move(3, players.get(1), players);
        assertEquals("yard", piece.getBoardArea());
    }

    @Test
    public void testSetPosIndexArea() {
        assertThrows(IllegalArgumentException.class, () -> piece.setPosIndex(74));
    }

    @Test
    public void testLandingOnPiece() {
        piece.setBoardArea("gameTrack");
        piece.setPosIndex(10);

        piece2.setBoardArea("gameTrack");
        piece2.setPosIndex(14);

        piece.move(4, player, players);

        testPosition(piece2.getInitialPosIndex());
    }

    @Test
    public void testMovePiece() {
        piece.setBoardArea("gameTrack");
        piece.setPosIndex(0);

        piece.move(5, player, players);
        testPosition(5, "gameTrack", piece.getPosIndex(), piece.getBoardArea());
    }

}