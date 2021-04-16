package org.ludo;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.ludo.gameLogic.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PieceMoverTest {
    PieceMover pieceMover;
    Piece piece1;
    Piece piece2;
    Piece piece3;
    Piece piece4;
    List<Piece> testPieces;

    ArrayList<Player> players = new ArrayList<>();
    Player testingPlayer;
    Player player2;

    @BeforeEach
    public void init() {
        String[] colorOrder = new String[]{"green", "yellow", "blue", "red"};

        for (int i = 0; i < 4; i++) {
            Player player = new Player("player", i);
            player.initializePieces();
            players.add(player);
        }

        testingPlayer = players.get(0);
        player2 = players.get(1);


        piece1 = players.get(0).getPieces().get(0);
        piece2 = players.get(1).getPieces().get(0);
        piece3 = players.get(2).getPieces().get(0);
        piece4 = players.get(3).getPieces().get(0);

        testPieces = new ArrayList<>();
        testPieces.add(piece1);
        testPieces.add(piece2);
        testPieces.add(piece3);
        testPieces.add(piece4);

        pieceMover = new PieceMover(players, new BoardPositions(25, 0, 0));
    }

    private void assertPosition(int expectedPosition, String expectedArea, int actualPosition, String actualArea) {
        assertEquals(expectedPosition, actualPosition);
        assertEquals(expectedArea, actualArea);
    }

    @Test
    @DisplayName("Testing setting the index on a piece")
    //TODO: finish
    public void testSetPositionIndex() {
        assertThrows(IllegalArgumentException.class, () -> pieceMover.setPosIndex(piece1, -1));
    }

    @DisplayName("Testing setting board area on a piece")
    @Test
    public void testSetBoardArea() {
        assertThrows(IllegalArgumentException.class, () -> pieceMover.setBoardArea(piece1, "hei"));

        pieceMover.setBoardArea(piece1, "homeColumn");
        assertEquals("homeColumn", piece1.getBoardArea());

        pieceMover.setBoardArea(piece1, "yard");
        assertEquals("yard", piece1.getBoardArea());

        pieceMover.setBoardArea(piece1, "goal");
        assertEquals("goal", piece1.getBoardArea());

        pieceMover.setBoardArea(piece1, "gameTrack");
        assertEquals("gameTrack", piece1.getBoardArea());
    }

    @Test
    @DisplayName("Moving out of yard")
    public void outOfYardTest() {
        testPieces.forEach(piece -> assertThrows(IllegalArgumentException.class, () -> pieceMover.moveToGameTrack(piece, 4)));

        testPieces.forEach(piece -> pieceMover.moveToGameTrack(piece, 6));
        testPieces.forEach(piece -> assertPosition(piece.getColorIndex()*13, "gameTrack", piece.getPosIndex(), piece.getBoardArea()));
    }

    @Test
    @DisplayName("Moving into home column")
    public void intoHomeColumn() {
        //setting each piece to 3 before home column entrance
        testPieces.forEach(piece -> {
            pieceMover.setBoardArea(piece, "gameTrack");
            if(piece.getColorIndex() != 0) pieceMover.setPosIndex(piece, piece.getColorIndex()*13-3);
        });
        pieceMover.setPosIndex(piece1, 50);

        //moving each peace 4 forward, should enter home column
        testPieces.forEach(piece -> {
            pieceMover.move(piece, 4);
            assertEquals("homeColumn", piece.getBoardArea());
            assertEquals(piece.getColorIndex()*5+2, piece.getPosIndex());
        });
    }
    //"Testing all of PieceMovers methods"

    @Test
    @DisplayName("Move into goal with exactly right die result")
    public void intoGoalExactDie() {
        //setting each piece to 1 before goal
        testPieces.forEach(piece -> {
            pieceMover.setBoardArea(piece, "homeColumn");
            pieceMover.setPosIndex(piece, piece.getColorIndex() * 6 + 5);
            pieceMover.move(piece, 1);
            assertEquals("goal", piece.getBoardArea());
        });
    }

    @Test
    @DisplayName("Move into goal with actually too high die result")
    public void intoGoalTooHighDie() {
        //setting each piece to 1 before goal
        testPieces.forEach(piece -> {
            pieceMover.setBoardArea(piece, "homeColumn");
            pieceMover.setPosIndex(piece, piece.getColorIndex() * 6 + 5);
            pieceMover.move(piece, 4);
            assertEquals("goal", piece.getBoardArea());
        });
    }
/*



        //Testing each piece moving onto the home column, on the home column, and to the goal
        @Test
        public void testColor0OnHomeColumn () {
            pieceMover.setBoardArea("homeColumn", testingPiece);
            pieceMover.moveOnHomeColumn(3, piece);

            assertEquals(3, greenPiece.getPosIndex());
            assertEquals("homeColumn", greenPiece.getBoardArea());
        }

        @Test
        public void testColor1MoveOnHomeColumn () {
            pieceMover.setBoardArea("homeColumn", piece2);
            pieceMover.moveOnHomeColumn(3, piece2);

            assertEquals(1, bluePiece.getPosIndex());
            assertEquals("homeColumn", bluePiece.getBoardArea());
        }

        @Test
        public void testColor2MoveOnHomeColumn () {
            Piece redPiece = new Piece(2, 2);
            redPiece.setBoardArea("homeColumn");
            redPiece.moveOnHomeColumn(2);
            assertEquals(4, redPiece.getPosIndex());
            assertEquals("homeColumn", redPiece.getBoardArea());
        }

        @Test
        public void testColor3MoveOnHomeColumn () {
            Piece yellowPiece = new Piece(3, 4);
            yellowPiece.setBoardArea("homeColumn");
            yellowPiece.moveOnHomeColumn(1);
            assertEquals(5, yellowPiece.getPosIndex());
            assertEquals("homeColumn", yellowPiece.getBoardArea());
        }

        @DisplayName("Moving piece to goal")
        @Test
        public void testMoveToGoal () {
            piece.setBoardArea("homeColumn");
            piece.setPosIndex(0);
            piece.moveOnHomeColumn(6);
            assertEquals("goal", piece.getBoardArea());
        }

        @DisplayName("Move piece to home column")
        @Test
        public void moveToHomeColumn () {
            piece.setBoardArea("gameTrack");
            piece.setPosIndex(49);
            piece.moveOnGameTrack(4);
            System.out.println(piece.getPosIndex());
            assertEquals("homeColumn", piece.getBoardArea());
            assertEquals(1, piece.getPosIndex());
        }

        @DisplayName("Move piece into home column")
        @Test
        public void testWillPassHomeColumnEntranceWith () {
            piece.setBoardArea("gameTrack");
            piece.setPosIndex(0);
            assertEquals(-1, piece.willPassHomeColumnEntranceWith(4));

            piece.setPosIndex(50);
            assertEquals(4, piece.willPassHomeColumnEntranceWith(6));

            piece2.setBoardArea("gameTrack");
            piece2.setPosIndex(9);

            assertEquals(2, piece2.willPassHomeColumnEntranceWith(6));
        }


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

        assertPosition(piece2.getInitialPosIndex());
    }

    @Test
    public void testMovePiece() {
        piece.setBoardArea("gameTrack");
        piece.setPosIndex(0);

        piece.move(5, player, players);
        assertPosition(5, "gameTrack", piece.getPosIndex(), piece.getBoardArea());
    }
*/

}