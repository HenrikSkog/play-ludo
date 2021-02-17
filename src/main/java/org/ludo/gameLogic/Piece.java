package org.ludo.gameLogic;

import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import org.ludo.gameRendering.Renderable;
import org.ludo.utils.PieceImages;

import java.util.ArrayList;

public class Piece extends ImageView implements Renderable {
  //TODO: Move the piece that is clicked on

  private Color color;
  private String currentBoardPositionArea;
  private int index;

  public Piece(Color color, int index) {
    this.color = color;
    this.index = index;

    initializePiece();
  }




  public void movePieceOnGameTrack(int dieResult) {
    //TODO: Move piece into home column
    index += dieResult;
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
