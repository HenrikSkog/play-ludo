package org.ludo.gameLogic;

import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import org.ludo.gameRendering.Renderable;
import org.ludo.utils.PieceImages;

public class Piece extends ImageView implements Renderable {
  private Color color;
  private String boardArea;
  private int index;
  private int initialIndex;

  public Piece(Color color, int initialIndex) {
    this.color = color;
    this.initialIndex = initialIndex;
    this.index = initialIndex;

    initializePiece();
  }

  public void moveOnGameTrack(int dieResult) throws IllegalArgumentException {
    if(boardArea != "gameTrack")
      throw new IllegalArgumentException("Tried to move piece on game track when it was not on game track");
    index = (index + dieResult > 51) ? dieResult - (52 - index) : index + dieResult;
    setPosition();
  }

  public void moveToHomeColumn(int dieResult) throws IllegalArgumentException {
    if(boardArea != "gameTrack")
      throw new IllegalArgumentException("Tried to move piece from gametrack to home column when it was not on game track");
    index = dieResult + Board.getIndexOfColor(color)*6;
    setBoardArea("homeColumn");
    setPosition();
  }

  public void moveOnHomeColumn(int dieResult){
    System.out.println("index: " + index + "colorIndex: " + Board.getIndexOfColor(color));
    if(((index) % 6 + dieResult) > 5) {
      moveToGoal();
    } else {
      index += dieResult;
      setPosition();
    }
  }

  public void moveToGameTrack(int dieResult) throws IllegalArgumentException{
    if(boardArea != "yard" || dieResult != 6)
      throw new IllegalArgumentException("Tried to move piece out of yard with another die result than 6 or piece not on gameTrack");
    setBoardArea("gameTrack");
    setIndex(13*Board.getIndexOfColor(color));
    setPosition();
  }

  public void moveToYard() throws IllegalStateException{
    if(this.boardArea == "yard") {
      throw new IllegalStateException("Piece already in yard!");
    }
    this.boardArea = "yard";
    this.index = initialIndex;
    setPosition();
  }

  public void moveToGoal() {
    this.boardArea = "goal";
    this.index = initialIndex;
    setPosition();
  }

  private void initializePiece() {
    super.setImage(PieceImages.getPieceImage(color));
    setBoardArea("yard");
    setPosition();
  }

  public Color getColor() {
    return color;
  }

  public int getIndex() {
    return index;
  }

  public void setPosition() {
    super.setLayoutX(Board.getBoardPositionX(boardArea, index));
    super.setLayoutY(Board.getBoardPositionY(boardArea, index));
  }

  public void setIndex(int index) {
    this.index = index;
  }

  public void setBoardArea(String boardArea) throws IllegalArgumentException{
    if(!Board.getAllowedBoardPositionAreas().contains(boardArea)) {
      throw new IllegalArgumentException("Not an allowed area");
    }

    this.boardArea = boardArea;
  }

  public String getBoardArea() {
    return boardArea;
  }

  @Override
  public void render() {
    GameInitialState.getGameContainer().getChildren().add(this);
  }

  @Override
  public String toString() {
    return "Piece{" +
            "color=" + color +
            ", currentBoardPositionArea='" + boardArea + '\'' +
            ", index=" + index +
            '}';
  }
}
