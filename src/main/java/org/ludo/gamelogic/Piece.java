package org.ludo.gamelogic;

public class Piece {
  //doesn't change
  private final int colorIndex;
  private final int initialPosIndex;

  //desides its position on the board
  private int posIndex;
  private Areas boardArea;

  //renders the piece
  private PieceNode pieceNode;

  public Piece(int colorIndex, int initialPosIndex) {
    this.colorIndex = colorIndex;
    this.initialPosIndex = initialPosIndex;
    this.posIndex = initialPosIndex;
    setBoardArea(Areas.YARD);
  }

  public Piece(int colorIndex, int initialPosIndex, int posIndex, Areas boardArea) {
    this.colorIndex = colorIndex;
    this.initialPosIndex = initialPosIndex;
    this.posIndex = posIndex;
    setBoardArea(boardArea);
  }


  public int getPosIndex() {
    return posIndex;
  }

  public void setPosIndex(int posIndex) {
    this.posIndex = posIndex;
  }

  public void setBoardArea(Areas boardArea) {
    this.boardArea = boardArea;
  }

  public Areas getBoardArea() {
    return boardArea;
  }

  public int getColorIndex() {
    return colorIndex;
  }

  public int getInitialPosIndex() {
    return initialPosIndex;
  }

  public void setPieceNode(PieceNode pieceNode) {
    this.pieceNode = pieceNode;
  }

  public PieceNode getPieceNode() {
    return pieceNode;
  }

  @Override
  public String toString() {
    return "Piece{" +
            "colorIndex=" + colorIndex +
            ", initialPosIndex=" + initialPosIndex +
            ", posIndex=" + posIndex +
            ", boardArea='" + boardArea + '\'' +
            '}';
  }
}
