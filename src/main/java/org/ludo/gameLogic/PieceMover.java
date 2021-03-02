package org.ludo.gameLogic;

import javafx.scene.paint.Color;

public class PieceMover {
  private GameState gameState;

  public PieceMover(GameState gameState) {
    this.gameState = gameState;
  }

  public void move(Piece piece, int dieResult) {
    try {
    if(dieResult == 6 && gameState.getCurrentPlayer().hasAllPiecesInYard()) {
      piece.movePieceOutOfYard(dieResult);
    } else if (dieResult == 6 && piece.getBoardArea() == "yard")
      piece.movePieceOutOfYard(dieResult);

    else if (piece.getBoardArea() == "gameTrack") {

      int willPassHomeColumnEntranceWith = willPassHomeColumnEntranceWith(piece, dieResult);

      if (willPassHomeColumnEntranceWith != -1) {
        piece.moveToHomeColumn(willPassHomeColumnEntranceWith);

      } else {
        piece.movePieceOnGameTrack(dieResult);
      }
    } else if (piece.getBoardArea() == "homeColumn") {
      piece.movePieceOnHomeColumn(dieResult);
    }
  } catch(Error error) {
      System.out.println("Tried to move" + piece.toString() + "with dieResult " + dieResult);
    }
    }

  private boolean isInRangeOfHomeEntrance(Piece piece) {
    var gameTrackIndex = piece.getIndex();
    var colorIndex = Board.getIndexOfColor(piece.getColor());
    if ((gameTrackIndex >= colorIndex * 13 - 6 && gameTrackIndex <= colorIndex * 13 - 1) || (piece.getColor() == Color.GREEN && gameTrackIndex >= 46 && gameTrackIndex <= 51)) {
      return true;
    }
    return false;
  }

  private int willPassHomeColumnEntranceWith(Piece piece, int dieResult) {
    var gameTrackIndex = piece.getIndex();
    var colorIndex = Board.getIndexOfColor(piece.getColor());

    if (isInRangeOfHomeEntrance(piece)) {
      if (gameTrackIndex + dieResult > colorIndex * 13) {
        return gameTrackIndex + dieResult - colorIndex * 13;
      }
    }
    return -1;
  }
}
