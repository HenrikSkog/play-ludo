package org.ludo.gameLogic;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public abstract class FXMLElements {
  private static Pane gameContainer;
  private static Label[] playerLabels;
  private static Text dieTextOutput;
  private static Button dieBtn;
  private static Stage stage;

  public static Pane getGameContainer() {
    return gameContainer;
  }

  public static Label[] getPlayerLabels() {
    return playerLabels;
  }

  public static Text getDieTextOutput() {
    return dieTextOutput;
  }

  public static Button getDieBtn() {
    return dieBtn;
  }

  public static Stage getStage() {
    return stage;
  }

  public static void setGameContainer(Pane gameContainer) {
    FXMLElements.gameContainer = gameContainer;
  }

  public static void setPlayerLabels(Label[] labels) {
    FXMLElements.playerLabels = labels;
  }

  public static void setDieTextOutput(Text dieTextOutput) {
    FXMLElements.dieTextOutput = dieTextOutput;
  }

  public static void setDieBtn(Button dieBtn) {
    FXMLElements.dieBtn = dieBtn;
  }

  public static void setStage(Stage stage) {
    FXMLElements.stage = stage;
  }


}
