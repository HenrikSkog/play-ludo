package org.ludo.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.ludo.App;

import java.io.IOException;

public class NewGameController {

    @FXML
    private Button goBackButton;

    @FXML
    void goBackMethod(ActionEvent event) throws IOException {
        App.setRoot("startScene");

    }

}
