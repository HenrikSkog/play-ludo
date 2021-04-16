package org.ludo.gameLogic;

import java.util.ArrayList;
import java.util.HashMap;

public class Piece implements Serializable {
  //doesn't change
  private final int colorIndex;
  private final int initialPosIndex;

  //desides its position on the board
  private int posIndex;
  private String boardArea;

  //renders the piece
  private PieceNode pieceNode;

  public Piece(int colorIndex, int initialPosIndex) {
    this.colorIndex = colorIndex;
    this.initialPosIndex = initialPosIndex;
    this.posIndex = initialPosIndex;
    setBoardArea("yard");
  }

  public Piece(int colorIndex, int initialPosIndex, int posIndex, String boardArea) {
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

  public void setBoardArea(String boardArea) {
    this.boardArea = boardArea;
  }

  public String getBoardArea() {
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

  public HashMap<String, Object> getState() {
    var stateVars = new HashMap<String, Object>();
    stateVars.put("colorIndex", colorIndex);
    stateVars.put("posIndex", posIndex);
    stateVars.put("initialPosIndex", initialPosIndex);
    stateVars.put("boardArea", boardArea);
    return stateVars;
  }
}
