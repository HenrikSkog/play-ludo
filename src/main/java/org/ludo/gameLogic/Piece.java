package org.ludo.gameLogic;

import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import org.ludo.gameRendering.Renderable;
import org.ludo.utils.PieceImages;

public class Piece extends ImageView implements Renderable {
  private Color color;
  private String boardArea;
  private int index;

  public Piece(Color color, int index) {
    this.color = color;
    this.index = index;

    initializePiece();
  }

  public void movePieceOnGameTrack(int dieResult) throws IllegalArgumentException {
    if(boardArea != "gameTrack")
      throw new IllegalArgumentException("Tried to move piece on game track when it was not on game track");
    index = (index + dieResult > 51) ? dieResult - (52 - index) : index + dieResult;
    setPosition();
  }
//TODO: feil exception?
  public void moveToHomeColumn(int dieResult) throws IllegalArgumentException {
    if(boardArea != "gameTrack")
      throw new IllegalArgumentException("Tried to move piece from gametrack to home column when it was not on game track");
    index = dieResult + Board.getIndexOfColor(color)*7;
    setBoardArea("homeColumn");
    setPosition();
  }

  //TODO: refctor to movetohomecolumn and moveonhomecolumn
  public void movePieceOnHomeColumn(int dieResult){
    if(dieResult + index - Board.getIndexOfColor(color)*7 > 7) {
      System.out.println(this.toString() + "har vunnet!");
    } else {
      index += dieResult;
      setPosition();
    }
  }

  public void movePieceOutOfYard(int dieResult) throws IllegalArgumentException{
    if(boardArea != "yard" || dieResult != 6)
      throw new IllegalArgumentException("Tried to move piece out of yard with another die result than 6 or piece not on gameTrack");
    setBoardArea("gameTrack");
    setIndex(13*Board.getIndexOfColor(color));
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
