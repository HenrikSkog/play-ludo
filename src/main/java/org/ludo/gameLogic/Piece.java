package org.ludo.gameLogic;

import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import org.ludo.gameRendering.Renderable;
import org.ludo.utils.PieceImages;

public class Piece extends ImageView implements Renderable{
  //TODO: Move the piece that is clicked on

  private int index;
  private Color color;

  public Piece(Color color, int index) {
    this.color = color;
    this.index = index;

    super.setImage(PieceImages.getPieceImage(color));
    setPosition();
  }


  private void setPosition() {
    super.setLayoutX(Board.getBoardPositionX(index));
    super.setLayoutY(Board.getBoardPositionY(index));
    System.out.println("Printing in Piece: " + Board.betweenColorsBoardPositions.get(0));
    System.out.println("Printing in Piece: " + Board.betweenColorsBoardPositions.get(index));
  }

  public int getIndex() {
    return index;
  }

  public void setIndex(int index) {
    this.index = index;
  }

  public Color getColor() {
    return color;
  }

  @Override
  public void render() {
  }
}
