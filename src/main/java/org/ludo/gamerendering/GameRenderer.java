package org.ludo.gamerendering;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import org.ludo.gamelogic.*;

public class GameRenderer implements PieceMoverObserver {
  private Game game;
  private Label[] playerLabels;
  private Pane gameContainer;
  private Text dieText;
  private Button dieBtn;

  //inits to default values, but can be changed here later if layout changes instead of changing values scattered around
  private final int scale = 25;
  private final String[] colorOrder = { "green", "yellow", "blue", "red" };

  private BoardPositions boardPositions;

  public GameRenderer(Game game) {
    this.game = game;
  }

  public void setFXMLElements(Pane gameContainer, Text dieTextOutput, Button dieBtn, Label[] playerLabels) {
    this.gameContainer = gameContainer;
    this.dieText = dieTextOutput;
    this.dieBtn = dieBtn;
    this.playerLabels = playerLabels;
  }

  public void initGraphics() {
    this.boardPositions = new BoardPositions(scale, 50, 100);

    initPlayerGraphics();
    indicatePlayerTurn();
    renderPieces();
  }

  private void initPlayerGraphics() {
    int index = 0;
    for (Player player: game.getPlayers()) {
      playerLabels[index].setText(player.getName());
      addPieceNodes(player);
      index+=1;
    }
  }

  private void addPieceNodes(Player player) {
    player.getPieces().forEach(piece -> {
      PieceNode pieceNode = new PieceNode(piece, colorOrder[piece.getColorIndex()], scale);
      piece.setPieceNode(pieceNode);
    });
  }

  public void renderPieces() {
    game.getPlayers().forEach(player -> player.getPieces().forEach(piece -> gameContainer.getChildren().add(piece.getPieceNode())));
    game.getPlayers().forEach(player -> player.getPieces().forEach(piece -> piece.getPieceNode().setPosition(boardPositions)));
  }

  public void indicatePlayerTurn() {
    for (Player player : game.getPlayers()) {
      var i = player.getColorIndex();
      if (i == game.getCurrentPlayerTurn())
        playerLabels[player.getColorIndex()].setStyle("-fx-background-color: " + colorOrder[i] + ";-fx-text-fill: black");
      else
        playerLabels[i].setStyle("-fx-background-color: none");
    }
  }

  public Text getDieText() {
    return dieText;
  }

  public Button getDieBtn() {
    return dieBtn;
  }

  @Override
  public void handlePieceMoved(Piece piece) {
    piece.getPieceNode().setPosition(boardPositions);
  }
}
