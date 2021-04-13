package org.ludo.gameRendering;

import org.ludo.gameLogic.BoardPositions;
import org.ludo.gameLogic.FXMLElements;
import org.ludo.gameLogic.GameEngineInterface;
import org.ludo.gameLogic.Player;

import java.util.ArrayList;

public class GameRenderer {
  private GameEngineInterface gameEngine;

  public GameRenderer(GameEngineInterface gameEngine) {
    this.gameEngine = gameEngine;
  }

  public void renderPieces() {
    gameEngine.getPlayers().forEach(player -> player.getPieces().forEach(piece -> piece.getPieceNode().setPosition()));
    gameEngine.getPlayers().forEach(player -> player.getPieces().forEach(piece -> piece.getPieceNode().render()));
  }

  public void reRenderPieces() {
    gameEngine.getPlayers().forEach(player -> player.getPieces().forEach(piece -> piece.getPieceNode().setPosition()));
  }

  public void indicatePlayerTurn() {
    for (Player player : gameEngine.getPlayers()) {
      var i = player.getColorIndex();
      if (i == gameEngine.getCurrentPlayerTurn())
        FXMLElements.getPlayerLabels()[player.getColorIndex()].setStyle("-fx-background-color: " + BoardPositions.getColorByOrder(i) + ";-fx-text-fill: black");
      else
        FXMLElements.getPlayerLabels()[i].setStyle("-fx-background-color: none");
    }
  }
}
