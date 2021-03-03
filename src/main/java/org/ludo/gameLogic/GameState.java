package org.ludo.gameLogic;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Border;
import org.ludo.gameRendering.DieAnimator;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;


public class GameState {
  private ArrayList<Player> players = new ArrayList<>();
  private PieceMover pieceMover = new PieceMover(this);

  //about current turn
  private int currentPlayerTurn;
  private Player currentPlayer;
  private int currentTurnTries;
  private int dieResult;

  private boolean testing = false;


  public void intializeGameState(String... playerNames) {
    setPlayers(playerNames);
    indicatePlayerTurn();
    enableDieRoll();

    currentTurnTries = 3;
    currentPlayerTurn = 0;
    currentPlayer = players.get(currentPlayerTurn);
  }

  private void handleDieRoll() {
    System.out.println("tries:" + currentTurnTries);
    var die = new Die(GameInitialState.getDieTextOutput(), GameInitialState.getDieBtn());

    String lastDieRoll = GameInitialState.getDieTextOutput().getText();
    int lastDieRollInt;

    if (lastDieRoll == "")
      lastDieRollInt = 1;
    else
      lastDieRollInt = Integer.parseInt(lastDieRoll);


    if (testing) {
      GameInitialState.getStage().getScene().setOnKeyPressed(
              new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent event) {
                  try {
                    dieResult = Integer.parseInt(event.getText());
                    System.out.println(dieResult);
                    if (currentPlayer.hasAllPiecesInYard()) {
                      handleDieRollWhenAllInYard(dieResult);
                      System.out.println("Handling piece when all in yard");
                    } else {
                      handleDieRollWhenOutOfYard(dieResult);
                      System.out.println("handling when out of yard");
                    }
                  } catch (NumberFormatException e) {
                    nextPlayer();
                  }
                }
              }
      );
    } else {
      this.dieResult = die.roll();
      var dieAnimator = new DieAnimator(GameInitialState.getDieTextOutput(), GameInitialState.getDieBtn(), lastDieRollInt, dieResult);

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

      dieAnimator.animate(delay, rotations, flashes, dieResult);
      timer.schedule(task, delay + 10);

    }

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
    GameInitialState.getDieBtn().setOnMouseClicked(event -> handleDieRoll());
  }

  private void disableDieRoll() {
    GameInitialState.getDieBtn().setOnMouseClicked(null);

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
    if (currentPlayer.hasAllPiecesInYard())
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
  }

  public void setCurrentPlayer(int currentTurnPlayer, Player currentPlayer) {
    this.currentPlayer = currentPlayer;
  }

  public Player getCurrentPlayer() {
    return currentPlayer;
  }

  public ArrayList<Player> getPlayers() {
    return players;
  }

  public void setCurrentTurnTries(int currentTurnTries) {
    this.currentTurnTries = currentTurnTries;
  }

  public void setTestingMode() {
    this.testing = true;
  }


}