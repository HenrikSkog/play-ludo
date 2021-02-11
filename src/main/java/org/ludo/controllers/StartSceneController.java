package org.ludo.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.ludo.App;

import java.io.IOException;

public class StartSceneController {

    @FXML
    private Button newGameButton;

    @FXML
    void startNewGameMethod(ActionEvent event) throws IOException {
        App.setRoot("newGameScene");
    }

    @FXML
    private Button loadGameButton;

    @FXML
    private Button quitGameButton;

}
