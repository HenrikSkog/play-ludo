package org.ludo.gameRendering;

import org.ludo.gameLogic.BoardPositions;
import org.ludo.gameLogic.FXMLElements;
import org.ludo.gameLogic.Game;
import org.ludo.gameLogic.Player;

public class GameRenderer {
  private Game game;

  public GameRenderer(Game game) {
    this.game = game;
  }

  public void renderPieces() {
    game.getPlayers().forEach(player -> player.getPieces().forEach(piece -> piece.getPieceNode().setPosition(game.getBoardPositions())));
    game.getPlayers().forEach(player -> player.getPieces().forEach(piece -> piece.getPieceNode().render()));
  }

  public void reRenderPieces() {
    game.getPlayers().forEach(player -> player.getPieces().forEach(piece -> piece.getPieceNode().setPosition(game.getBoardPositions())));
  }

  public void indicatePlayerTurn() {
    for (Player player : game.getPlayers()) {
      var i = player.getColorIndex();
      if (i == game.getCurrentPlayerTurn())
        FXMLElements.getPlayerLabels()[player.getColorIndex()].setStyle("-fx-background-color: " + game.getColorOrder()[i] + ";-fx-text-fill: black");
      else
        FXMLElements.getPlayerLabels()[i].setStyle("-fx-background-color: none");
    }
  }
}
