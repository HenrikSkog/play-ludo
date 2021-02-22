package org.ludo.gameLogic;

import javafx.scene.layout.Border;

import java.util.ArrayList;


public class GameState {
  private ArrayList<Player> players = new ArrayList<>();
  private int currentPlayerTurn = 0;
  private int currentTurnTries = 3;
  private Player currentPlayer;
  private PieceMover pieceMover = new PieceMover(this);


  public void intializeGameState(String... playerNames) {
    setPlayers(playerNames);
    indicatePlayerTurn();
    enableDieRoll();
  }

  private void handleDieRoll() {
    int dieResult = Die.roll();
    if (dieResult != 6)
      currentTurnTries -= 1;

    if (currentTurnTries == 0 && currentPlayer.getPiecesInYard().size() == 4) {
      nextPlayer();
    }
    else {
      if (dieResult == 6 && currentPlayer.getPiecesInYard().size() == 4) {
        currentTurnTries = 1;
      }
      enableMoveToPlayer(dieResult);
      disableDieRoll();
    }
  }


  private void enableMoveToPlayer(int dieResult) {
    for (Piece piece : currentPlayer.getPieces()) {
      piece.setOnMouseClicked(event -> {
        pieceMover.move(piece, dieResult);
        removePieceListeners();
        enableDieRoll();
        if (currentTurnTries == 0)
          nextPlayer();
      });
    }
  }

  private void enableDieRoll() {
    Die.getDieBtn().setOnMouseClicked(event -> handleDieRoll());
  }

  private void disableDieRoll() {
    Die.getDieBtn().setOnMouseClicked(null);

  }

  private void removePieceListeners() {
    for (Piece piece : currentPlayer.getPieces()) {
      piece.setOnMouseClicked(null);
    }
  }

  private void nextPlayer() {
    removePieceListeners();
    currentPlayerTurn = (currentPlayerTurn == players.size() - 1) ? 0 : currentPlayerTurn + 1;

    currentPlayer = players.get(currentPlayerTurn);

    indicatePlayerTurn();
    if (currentPlayer.getPiecesInYard().size() == 4)
      currentTurnTries = 3;
    else
      currentTurnTries = 1;
  }

  public void renderPieces() {
    getPlayers().stream().forEach(player -> player.renderPieces());
  }

  private void indicatePlayerTurn() {
    for (int i = 0; i < GameInitialState.getPlayerLabels().length; i++) {
      if (i == currentPlayerTurn)
        GameInitialState.getPlayerLabels()[i].setStyle("-fx-border-color: black");
      else
        GameInitialState.getPlayerLabels()[i].setStyle("-fx-border-color: none");
    }
  }

  public void setPlayers(String[] playerNames) throws IllegalArgumentException {
    if (playerNames.length > 4) {
      throw new IllegalArgumentException("There cannot be more than 4 players");
    }

    int playerIndex = 0;
    for (String playerName : playerNames) {
      //TODO: move line under to dedicated class for rendering stuff maybe
      GameInitialState.getPlayerLabels()[playerIndex].setText(playerName);

      var player = new Player(playerName, Board.getColorByOrder(playerIndex));
      players.add(player);

      playerIndex += 1;
    }
    currentPlayer = players.get(currentPlayerTurn);
  }

  public void setCurrentPlayer(int currentTurnPlayer, Player currentPlayer) {
    this.currentPlayer = currentPlayer;
  }

  public ArrayList<Player> getPlayers() {
    return players;
  }
}