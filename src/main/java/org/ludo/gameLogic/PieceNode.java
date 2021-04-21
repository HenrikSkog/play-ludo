package org.ludo.gameLogic;

import javafx.scene.image.ImageView;
import org.ludo.utils.PieceImages;

public class PieceNode extends ImageView {
  private final Piece piece;

  public PieceNode(Piece piece, String color, int scale) {
    this.piece = piece;
    PieceImages pieceImages = new PieceImages(scale);
    super.setImage(pieceImages.getPieceImage(color));
  }

  public void setPosition(BoardPositions boardPositions) {
    super.setLayoutX(boardPositions.getBoardPositionX(piece.getBoardArea(), piece.getPosIndex()));
    super.setLayoutY(boardPositions.getBoardPositionY(piece.getBoardArea(), piece.getPosIndex()));
  }
}
