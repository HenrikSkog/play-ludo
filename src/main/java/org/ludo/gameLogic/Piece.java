package org.ludo.gameLogic;

import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import org.ludo.gameRendering.Renderable;
import org.ludo.utils.PieceImages;

import java.util.ArrayList;

public class Piece extends ImageView implements Renderable {
  private Color color;
  private String currentBoardPositionArea;
  private int index;

  public Piece(Color color, int index) {
    this.color = color;
    this.index = index;

    initializePiece();
  }

  public void movePieceOnGameTrack(int dieResult) {
    index = (index + dieResult > 51) ? dieResult - (52 - index) : index + dieResult;
    setPosition();
  }

  //TODO: refctor to movetohomecolumn and moveonhomecolumn
  public void movePieceOnHomeColumn(int dieResult){
    if(getCurrentBoardPositionArea() != "homeColumn") {
      setCurrentBoardPositionArea("homeColumn");
      index = dieResult + Board.getIndexOfColor(color);
    } else {
      index += dieResult;
    }
    setPosition();
  }

  public void movePieceOutOfYard() {
    setCurrentBoardPositionArea("gameTrack");
    setIndex(13*Board.getIndexOfColor(color));
    setPosition();
  }

  private void initializePiece() {
    super.setImage(PieceImages.getPieceImage(color));
    setCurrentBoardPositionArea("yard");
    setPosition();
  }

  public Color getColor() {
    return color;
  }

  public int getIndex() {
    return index;
  }

  private void setPosition() {
    super.setLayoutX(Board.getBoardPositionX(currentBoardPositionArea, index));
    super.setLayoutY(Board.getBoardPositionY(currentBoardPositionArea, index));
  }

  private void setIndex(int index) {
    this.index = index;
  }

  public void setCurrentBoardPositionArea(String area) throws IllegalArgumentException{
    if(!Board.getAllowedBoardPositionAreas().contains(area)) {
      throw new IllegalArgumentException("Not an allowed area");
    }

    currentBoardPositionArea = area;
  }

  public String getCurrentBoardPositionArea() {
    return currentBoardPositionArea;
  }

  @Override
  public void render() {
    GameInitialState.getGameContainer().getChildren().add(this);
  }

  @Override
  public String toString() {
    return "Piece{" +
            "color=" + color +
            ", currentBoardPositionArea='" + currentBoardPositionArea + '\'' +
            ", index=" + index +
            '}';
  }

}
