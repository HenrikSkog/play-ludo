package org.ludo.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.ludo.App;

import java.io.IOException;

public class StartSceneController {

    @FXML
    void startNewGame(ActionEvent event) throws IOException {
        App.setRoot("newGameScene");
    }


    @FXML
    void quitGameMethod(ActionEvent event) throws IOException{
        Platform.exit();
    }
    //TODO: loadgamemethod a function, make load game scene?
    public void loadGameMethod(ActionEvent event) {
    }
}
