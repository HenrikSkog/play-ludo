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
  Player player_1;
  Player player_2;
  Player player_3;
  Player player_4;
  ArrayList<Player> players = new ArrayList<>();

  @BeforeEach
  public void init() {
    String[] names = new String[]{
            "Henrik", "Erlend", "Sebastian", "Julian"
    };
    for (int i = 0; i <= 4; i++) {
      var player = new Player(names[i], i);
      for (int j = 0; j < 4; j++) {
        var piece = new Piece(player.getColorIndex(), player.getColorIndex() * 4 + i);
        player.addPiece(piece);
      }
      players.add(player);
    }
    piece = players.get(0).getPieces().get(0);
    piece2 = players.get(1).getPieces().get(0);
  }


//Test all pieces to move on the HomeColumn
  @Test
  public void testColor0OnHomeColumn(){
    Piece greenPiece = new Piece(0, 0);
    greenPiece.setBoardArea("homeColumn");
    greenPiece.moveOnHomeColumn(3);
    assertEquals(3,greenPiece.getPosIndex());
    assertEquals("homeColumn", greenPiece.getBoardArea());
  }

  @Test
  public void testColor1MoveOnHomeColumn(){
    Piece bluePiece = new Piece(1, 0);
    bluePiece.setBoardArea("homeColumn");
    bluePiece.moveOnHomeColumn(1);
    assertEquals(1,bluePiece.getPosIndex());
    assertEquals("homeColumn", bluePiece.getBoardArea());
  }

  @Test
  public void testColor2MoveOnHomeColumn(){
    Piece redPiece = new Piece(2, 2);
    redPiece.setBoardArea("homeColumn");
    redPiece.moveOnHomeColumn(2);
    assertEquals(4,redPiece.getPosIndex());
    assertEquals("homeColumn", redPiece.getBoardArea());
  }

  @Test
  public void testColor3MoveOnHomeColumn(){
    Piece yellowPiece = new Piece(3, 4);
    yellowPiece.setBoardArea("homeColumn");
    yellowPiece.moveOnHomeColumn(1);
    assertEquals(5,yellowPiece.getPosIndex());
    assertEquals("homeColumn", yellowPiece.getBoardArea());
  }

  //Test if pieces will move into goal
  @Test
  public void testMoveToGoal(){
    piece.setBoardArea("homeColumn");
    piece.setPosIndex(0);
    piece.moveOnHomeColumn(6);
    assertEquals("goal", piece.getBoardArea());
  }

  @Test
  public void moveToHomeColumn(){
    piece.setBoardArea("gameTrack");
    piece.setPosIndex(49);
    piece.moveOnGameTrack(4);
    System.out.println(piece.getPosIndex());
    assertEquals("homeColumn", piece.getBoardArea());
    assertEquals(1, piece.getPosIndex());
  }

  @Test
  public void testWillPassHomeColumnEntranceWith() {
    piece.setBoardArea("gameTrack");
    piece.setPosIndex(0);

    assertEquals(-1, piece.willPassHomeColumnEntranceWith(4));
    piece.setPosIndex(50);
    assertEquals(4, piece.willPassHomeColumnEntranceWith(6));
    Piece yellowPiece = new Piece(1, 4);
    yellowPiece.setBoardArea("gameTrack");
    yellowPiece.setPosIndex(9);
    yellowPiece.moveOnGameTrack(6);
    System.out.println(yellowPiece.getPosIndex());
    assertEquals(2, yellowPiece.willPassHomeColumnEntranceWith(4));
    assertEquals("homeColumn", yellowPiece.getBoardArea());
  }


  @Test
  @DisplayName("Test for setting Illegal boardArea")
  public void setBoardArea(){
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
  public void testMovePiecesOutOfYard(){
    piece.moveToGameTrack(6);
    assertEquals("gameTrack", piece.getBoardArea());
  }

  @Test
  public void testSetBoardArea() {
    assertThrows(IllegalArgumentException.class, () -> piece.setBoardArea("hei"));
  }

  @Test
  @DisplayName("Moving into yard from gameTrack")
  public void testMovingIntoYard(){
    piece.setBoardArea("gameTrack");
    piece.setPosIndex(5);
    piece2.setBoardArea("gameTrack");
    piece2.setPosIndex(2);
    piece2.move(2, players.get(1), players);
    assertEquals("yard", piece.getBoardArea());
  }

  @Test
  public void testSetPosIndexArea() {
    assertThrows(IllegalArgumentException.class, () -> piece.setPosIndex(74));
  }



}