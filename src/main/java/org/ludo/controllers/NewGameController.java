package org.ludo.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.ludo.App;
import org.ludo.gameLogic.Board;
import org.ludo.gameLogic.GameInitialState;
import org.ludo.gameLogic.GameState;
import org.ludo.gameLogic.Piece;

import java.io.IOException;

public class NewGameController extends GameSceneController {
  @FXML
  public TextField greenName;
  @FXML
  public TextField yellowName;
  @FXML
  public TextField blueName;
  @FXML
  public TextField redName;

  @FXML
  private Button goBackButton;

  @FXML
  void goBackMethod(ActionEvent event) throws IOException {
    App.setRoot("startScene");
  }

  @FXML
  public void newGame(ActionEvent event) throws IOException {
    App.setRoot("gameScene");
    var gameState = new GameState();

    gameState.intializeGameState(greenName.getText(), yellowName.getText(), redName.getText(), blueName.getText());

    GameInitialState.getStage().setWidth(520);

    gameState.setTestingMode();

    gameState.renderPieces();
  }

}

