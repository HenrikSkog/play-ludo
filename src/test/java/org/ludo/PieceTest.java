package org.ludo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.ludo.gameLogic.Piece;

import static org.junit.jupiter.api.Assertions.*;

public class PieceTest {
  Piece piece;

  @BeforeEach
  public void init() {
    piece = new Piece(0, 0);
  }


  @Test
  public void testWillPassHomeColumnEntranceWith() {
    piece.setBoardArea("gameTrack");
    piece.setPosIndex(0);
    assertEquals(-1, piece.willPassHomeColumnEntranceWith(4));

    piece.setPosIndex(50);
    assertEquals(4, piece.willPassHomeColumnEntranceWith(6));
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
  public void testSetBoardArea() {
    assertThrows(IllegalArgumentException.class, () -> piece.setBoardArea("hei"));
  }






}