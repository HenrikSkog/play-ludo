package org.ludo.gameLogic;

import javafx.scene.image.ImageView;
import org.ludo.gameRendering.Renderable;
import org.ludo.utils.PieceImages;

public class PieceNode extends ImageView implements Renderable {
  private final Piece piece;

  public PieceNode(Piece piece) {
    this.piece = piece;
    super.setImage(PieceImages.getPieceImage(BoardPositions.getColorByOrder(piece.getColorIndex())));
  }

  @Override
  public void render() {
    FXMLElements.getGameContainer().getChildren().add(this);
  }

  public void setPosition() {
    super.setLayoutX(BoardPositions.getBoardPositionX(piece.getBoardArea(), piece.getPosIndex()));
    super.setLayoutY(BoardPositions.getBoardPositionY(piece.getBoardArea(), piece.getPosIndex()));
  }
}
