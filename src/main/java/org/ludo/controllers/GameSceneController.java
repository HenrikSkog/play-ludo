package org.ludo.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.ludo.App;
import org.ludo.gameRendering.CanvasRenderer;

import java.net.URL;
import java.util.ResourceBundle;

public class GameSceneController implements Initializable {
  @FXML
  private Canvas boardCanvas;
  @FXML
  private Label name1;
  @FXML
  private Label name2;
  @FXML
  private Label name3;
  @FXML
  private Label name4;




  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {

  }
  public void setLabelText(String text){
    name1.setText(text);
  }


}
