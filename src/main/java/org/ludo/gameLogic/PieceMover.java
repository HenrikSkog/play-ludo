package org.ludo.gameLogic;

public class PieceMover {
  private GameState gameState;

  public PieceMover(GameState gameState) {
    this.gameState = gameState;
  }

  public void move(Piece piece, int dieResult) {
    try {
      switch (piece.getBoardArea()) {
        case "yard":
          if (dieResult == 6 && gameState.getCurrentPlayer().hasAllPiecesInYard()) {
            piece.moveToGameTrack(dieResult);
          } else if (dieResult == 6 && piece.getBoardArea() == "yard")
            piece.moveToGameTrack(dieResult);
          break;
        case "gameTrack":
          int willPassHomeColumnEntranceWith = willPassHomeColumnEntranceWith(piece, dieResult);

          if (willPassHomeColumnEntranceWith != -1) {
            piece.moveToHomeColumn(willPassHomeColumnEntranceWith);
          } else {
            piece.moveOnGameTrack(dieResult);
            handleLandingOnAnotherPiece(piece);
          }
          break;
        case "homeColumn":
          piece.moveOnHomeColumn(dieResult);
          break;
      }
    } catch (Error error) {
      System.out.println("Tried to move" + piece.toString() + "with dieResult " + dieResult);
    }
  }

  private void handleLandingOnAnotherPiece(Piece currentPiece) {
    gameState.getPlayers().stream().forEach(player -> player.getPieces().stream().forEach(piece -> {
      if (piece.getIndex() == currentPiece.getIndex() && piece != currentPiece && piece.getBoardArea() == "gameTrack") {
        piece.moveToYard();
        System.out.println("TRAFF");
      }
    }));
  }

  private int willPassHomeColumnEntranceWith(Piece piece, int dieResult) {
    var colorIndex = Board.getIndexOfColor(piece.getColor());
    //find lane
    int colorLane = (colorIndex - 1) % 4;
    int currentLane = (piece.getIndex() / 13);
    int currentLaneIndex = piece.getIndex() % 13;

    if (currentLane == colorLane) {
      int entrancePassValue = 13 - currentLaneIndex + dieResult;
      if (entrancePassValue > 0)
        return entrancePassValue;
    }
    return -1;
  }
}
