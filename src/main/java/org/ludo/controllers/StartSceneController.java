package org.ludo.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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

    public void loadGameMethod(ActionEvent event) throws IOException {
        App.setRoot("loadGameScene");
    }
}