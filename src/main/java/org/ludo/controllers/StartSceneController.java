package org.ludo.controllers;

import java.io.IOException;
import javafx.fxml.FXML;
import org.ludo.App;

public class StartSceneController {

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
}