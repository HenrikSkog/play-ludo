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

  public void move(int dieResult, Player player, ArrayList<Player> players) {
    try {
      switch (getBoardArea()) {
        case "yard":
          if (dieResult == 6 && player.hasAllPiecesInYard()) {
            moveToGameTrack(dieResult);
          } else if (dieResult == 6 && getBoardArea().equals("yard"))
            moveToGameTrack(dieResult);
          break;
        case "gameTrack":
          int willPassHomeColumnEntranceWith = willPassHomeColumnEntranceWith(dieResult);

          if (willPassHomeColumnEntranceWith != -1) {
            moveToHomeColumn(willPassHomeColumnEntranceWith);
          } else {
            moveOnGameTrack(dieResult);
            handleLandingOnAnotherPiece(players);
          }
          break;
        case "homeColumn":
          moveOnHomeColumn(dieResult);
          break;
      }
    } catch (Error error) {
      System.out.println("Tried to move" + toString() + "with dieResult " + dieResult);
    }
  }

  public void moveOnGameTrack(int dieResult) throws IllegalArgumentException {
    if(!boardArea.equals("gameTrack"))
      throw new IllegalArgumentException("Tried to move piece on game track when it was not on game track");
    posIndex = (posIndex + dieResult > 51) ? dieResult - (52 - posIndex) : posIndex + dieResult;
  }

  public void moveToHomeColumn(int dieResult) throws IllegalArgumentException {
    if(!boardArea.equals("gameTrack"))
      throw new IllegalArgumentException("Tried to move piece from gametrack to home column when it was not on game track");
    posIndex = dieResult + colorIndex*6;
    setBoardArea("homeColumn");
  }

  public void moveOnHomeColumn(int dieResult){
    System.out.println("index: " + posIndex + "colorIndex: " + colorIndex);
    if(((posIndex) % 6 + dieResult) > 5) {
      moveToGoal();
    } else {
      posIndex += dieResult;
    }
  }

  public void moveToGameTrack(int dieResult) throws IllegalArgumentException{
    if(!boardArea.equals("yard") || dieResult != 6)
      throw new IllegalArgumentException("Tried to move piece out of yard with another die result than 6 or piece not on gameTrack");
    setBoardArea("gameTrack");
    setPosIndex(13*colorIndex);
  }

  public void moveToYard() throws IllegalStateException{
    if(this.boardArea.equals("yard")) {
      throw new IllegalStateException("Piece already in yard!");
    }
    this.boardArea = "yard";
    this.posIndex = initialPosIndex;
  }

  public void moveToGoal() {
    this.boardArea = "goal";
    this.posIndex = initialPosIndex;
  }

  public int willPassHomeColumnEntranceWith(int dieResult) {
    //find lane
    int colorLane = (getColorIndex() - 1) % 4;

    //java gives remainder and not modulo:
    if(colorLane<0) colorLane+=4;

    int currentLane = (getPosIndex() / 13);
    int currentLaneIndex = getPosIndex() % 13;

    if (currentLane == colorLane && currentLaneIndex + dieResult >= 13) {
      return currentLaneIndex + dieResult - 13;
    }
    return -1;
  }

  private void handleLandingOnAnotherPiece(ArrayList<Player> players) {
    players.forEach(player -> player.getPieces().forEach(piece -> {
      if (piece.getPosIndex() == this.getPosIndex() && piece != this && piece.getBoardArea().equals("gameTrack")) {
        piece.moveToYard();
        System.out.println("TRAFF");
      }
    }));
  }

  public int getPosIndex() {
    return posIndex;
  }

  public void setPosIndex(int posIndex) throws IllegalArgumentException {
    if(posIndex > BoardPositions.getBoardPositionsLength(getBoardArea())-1)
      throw new IllegalArgumentException("Index out of bounds");
    this.posIndex = posIndex;
  }

  public void setBoardArea(String boardArea) throws IllegalArgumentException{
      if(!BoardPositions.getAllowedBoardPositionAreas().contains(boardArea)) {
        throw new IllegalArgumentException("Not an allowed area");
      }

      this.boardArea = boardArea;
    }


  public String getBoardArea() {
    return boardArea;
  }

  public int getColorIndex() {
    return colorIndex;
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
            "color=" + BoardPositions.getColorByOrder(colorIndex) +
            ", currentBoardPositionArea='" + boardArea + '\'' +
            ", index=" + posIndex +
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
