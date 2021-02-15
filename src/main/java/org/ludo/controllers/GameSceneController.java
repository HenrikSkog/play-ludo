package org.ludo.controllers;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import org.ludo.gameLogic.GameInitialState;
import org.ludo.gameRendering.GameRenderer;

public class GameSceneController {
    @FXML
    private Pane gameContainer;

    @FXML
    private void initialize(){
        GameInitialState.setGameContainer(gameContainer);
    }
}
