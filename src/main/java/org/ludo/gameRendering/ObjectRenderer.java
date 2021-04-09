package org.ludo.gameRendering;

import org.ludo.gameLogic.*;

public class ObjectRenderer implements GameStateListener {
  private gameEngine;

  public ObjectRenderer(GameEngine gameEngine) {
    this.ga
  }

  public void renderPieces() {
    gameEngine.getPlayers().forEach(player -> player.getPieces().forEach(piece -> piece.getPieceNode().updatePosition()));
    gameEngine.getPlayers().forEach(player -> player.getPieces().forEach(piece -> piece.getPieceNode().render()));
  }

  public void reRenderPieces() {
    gameEngine.getPlayers().forEach(player -> player.getPieces().forEach(piece -> piece.getPieceNode().updatePosition()));
  }

  @Override
  public void gamestateChange(GameEngineInterface gameEngine) {
    gameEngine.getPlayers().forEach(player -> player.getPieces().forEach(piece -> piece.getPieceNode().updatePosition()));
  }

  public void indicatePlayerTurn() {
    for (Player player : gameEngine.getPlayers()) {
      var i = player.getColorIndex();
      if (i == gameEngine.getCurrentPlayerTurn())
        FXMLElements.getPlayerLabels()[player.getColorIndex()].setStyle("-fx-background-color: " + LudoBoardLayout.getColorByOrder(i) + ";-fx-text-fill: black");
      else
        FXMLElements.getPlayerLabels()[i].setStyle("-fx-background-color: none");
    }
  }
}
