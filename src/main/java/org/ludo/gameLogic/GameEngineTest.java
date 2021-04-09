package org.ludo.gameLogic;

import org.ludo.gameRendering.DieAnimator;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


public class GameEngineTest {
  private final ArrayList<Player> players = new ArrayList<>();

  //about current turn
  private int currentPlayerTurn;
  private Player currentPlayer;
  private int currentTurnTries;
  private boolean testing = false;

  public GameEngineTest(String... playerNames) {
    setPlayers(playerNames);
    indicatePlayerTurn();
    enableDieRoll();

    currentTurnTries = 3;
    currentPlayerTurn = 0;
    currentPlayer = players.get(currentPlayerTurn);
  }

  private void handleDieRoll() {
    System.out.println("tries:" + currentTurnTries);
    var die = new Die(FXMLElements.getDieTextOutput(), FXMLElements.getDieBtn());

    String lastDieRoll = FXMLElements.getDieTextOutput().getText();
    int lastDieRollInt;

    if (lastDieRoll.equals(""))
      lastDieRollInt = 1;
    else
      lastDieRollInt = Integer.parseInt(lastDieRoll);


    if (testing) {
      FXMLElements.getStage().getScene().setOnKeyPressed(
              event -> {
                try {
                  var dieResult = Integer.parseInt(event.getText());
                  System.out.println(dieResult);
                  if (currentPlayer.hasAllPiecesInYard()) {
                    handleDieRollWhenAllInYard(dieResult);
                    System.out.println("Handling piece when all in yard");
                  } else {
                    handleDieRollWhenOutOfYard(dieResult);
                    System.out.println("handling when out of yard");
                  }
                }
//                if input not number, go to next player when testing
                catch (NumberFormatException e) {
                  nextPlayer();
                }
              }
      );
    } else {
      var dieResult = die.roll();
      var dieAnimator = new DieAnimator(FXMLElements.getDieTextOutput(), FXMLElements.getDieBtn(), lastDieRollInt, dieResult);

      var timer = new Timer();

      var task = new TimerTask() {
        @Override
        public void run() {
          if (currentPlayer.hasAllPiecesInYard()) {
            handleDieRollWhenAllInYard(dieResult);
          } else {
            handleDieRollWhenOutOfYard(dieResult);
          }
        }
        // all pieces in yard and no tries left -> only go to next player and dont enable move to player
      };

      int delay = 1000;
      int flashes = 10;
      int rotations = 4;


      if (testing) {
        delay = 50;
        flashes = 2;
      }
      disableDieRoll();
      dieAnimator.animate(delay, rotations, flashes, dieResult);
      timer.schedule(task, delay + 10);
    }

  }

  private void enableMoveToPlayer(int dieResult) {
    for (Piece piece : currentPlayer.getPieces()) {
//    if dieResult not 6 and piece in yard, dont add listener to it
      if (!(piece.getBoardArea().equals("yard") && dieResult != 6)) {
        piece.getPieceNode().setOnMouseClicked(event -> {
          piece.move(dieResult, currentPlayer, players);
          removePieceListeners();
          enableDieRoll();
          if (currentTurnTries == 0)
            nextPlayer();
          reRenderPieces();
        });
      }
    }
  }

  private void nextPlayer() {
    removePieceListeners();
    enableDieRoll();

    currentPlayerTurn = (currentPlayerTurn == (players.size() - 1)) ? 0 : (currentPlayerTurn + 1);
    currentPlayer = players.get(currentPlayerTurn);

    indicatePlayerTurn();
    if (currentPlayer.hasAllPiecesInYard())
      currentTurnTries = 3;
    else
      currentTurnTries = 1;
  }

  private void handleDieRollWhenOutOfYard(int dieResult) {
    System.out.println("forsøk: " + currentTurnTries);
    if (dieResult != 6) {
      currentTurnTries--;
    }
    System.out.println("etter minus: " + currentTurnTries);
    enableMoveToPlayer(dieResult);
    disableDieRoll();
  }

  private void handleDieRollWhenAllInYard(int dieResult) {
    currentTurnTries -= 1;

    if (dieResult == 6) {
      enableMoveToPlayer(dieResult);
      disableDieRoll();
      currentTurnTries = 1;
    } else if (currentTurnTries == 0) {
      System.out.println("Next player");
      nextPlayer();
    } else {
      enableDieRoll();
    }
  }

  private void enableDieRoll() {
    FXMLElements.getDieBtn().setOnMouseClicked(event -> handleDieRoll());
  }

  private void disableDieRoll() {
    FXMLElements.getDieBtn().setOnMouseClicked(null);
  }

  private void removePieceListeners() {
    for (Piece piece : currentPlayer.getPieces()) {
      piece.getPieceNode().setOnMouseClicked(null);
    }
  }

  public void renderPieces() {
    players.forEach(player -> player.getPieces().forEach(piece -> piece.getPieceNode().updatePosition()));
    players.forEach(player -> player.getPieces().forEach(piece -> piece.getPieceNode().render()));
  }

  private void reRenderPieces() {
    players.forEach(player -> player.getPieces().forEach(piece -> piece.getPieceNode().updatePosition()));
  }

  private void indicatePlayerTurn() {
    for (Player player : players) {
      var i = player.getColorIndex();
      if (i == currentPlayerTurn)
        FXMLElements.getPlayerLabels()[player.getColorIndex()].setStyle("-fx-background-color: " + LudoBoardLayout.getColorByOrder(i) + ";-fx-text-fill: black");
      else
        FXMLElements.getPlayerLabels()[i].setStyle("-fx-background-color: none");
    }
  }

  public void setPlayers(String[] playerNames) throws IllegalArgumentException {
    if (playerNames.length > 4) {
      throw new IllegalArgumentException("There cannot be more than 4 players");
    }

    int playerIndex = 0;
    for (String playerName : playerNames) {
      //TODO: move line under to dedicated class for rendering stuff maybe
      FXMLElements.getPlayerLabels()[playerIndex].setText(playerName);

      players.add(new Player(playerName, playerIndex));
      playerIndex += 1;
    }
  }

  public ArrayList<Player> getPlayers() {
    return players;
  }

  public void setTestingMode() {
    this.testing = true;
  }
}