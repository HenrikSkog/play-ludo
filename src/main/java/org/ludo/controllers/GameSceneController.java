package org.ludo.controllers;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import org.ludo.gameRendering.CanvasRenderer;

public class GameSceneController {
  @FXML
  private Canvas boardCanvas;

  public void initialize() {
    GraphicsContext gc = boardCanvas.getGraphicsContext2D();
    CanvasRenderer.setGc(gc);
  }
}
