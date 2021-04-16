package org.ludo.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import org.ludo.App;
import java.io.IOException;

public class StartSceneController {
    @FXML
    void startNewGame() throws IOException {
        App.setRoot("newGameScene");
    }

    @FXML
    void quitGameMethod() {
        Platform.exit();
    }

    public void loadGameMethod() throws IOException {
        App.setRoot("loadGameScene");
    }
}