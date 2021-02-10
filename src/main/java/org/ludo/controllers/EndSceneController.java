package org.ludo.controllers;

import java.io.IOException;
import javafx.fxml.FXML;
import org.ludo.App;

public class EndSceneController {

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
}
