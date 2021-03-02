package org.ludo.gameLogic;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GameInitialState {
  private static Pane gameContainer;
  private static Label[] playerLabels;
  private static TextField[] playerText;
  private static Text dieTextOutput;
  private static Button dieBtn;
  private static Stage stage;

  public static Pane getGameContainer() {
    return gameContainer;
  }

  public static void setGameContainer(Pane gameContainer) {
    GameInitialState.gameContainer = gameContainer;
  }


  public static void setPlayerLabels(Label[] labels) {
    GameInitialState.playerLabels = labels;
  }

  public static Label[] getPlayerLabels() {
    return playerLabels;
  }

  public static void setDieTextOutput(Text dieTextOutput) {
    GameInitialState.dieTextOutput = dieTextOutput;
  }

  public static void setDieBtn(Button dieBtn) {
    GameInitialState.dieBtn = dieBtn;
  }

  public static Text getDieTextOutput() {
    return dieTextOutput;
  }

  public static Button getDieBtn() {
    return dieBtn;
  }

  public static void setStage(Stage stage) {
    GameInitialState.stage = stage;
  }

  public static Stage getStage() {
    return stage;
  }
}
