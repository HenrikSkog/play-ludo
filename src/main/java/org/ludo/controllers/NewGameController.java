package org.ludo.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.ludo.App;
import org.ludo.gameLogic.GameInitialState;
import org.ludo.gameLogic.GameState;

import java.io.IOException;
import java.util.HashMap;

public class NewGameController extends GameSceneController {
    @FXML
    public TextField greenName;
    @FXML
    public TextField yellowName;
    @FXML
    public TextField blueName;
    @FXML
    public TextField redName;

    @FXML
    private Button goBackButton;

    @FXML
    void goBackMethod(ActionEvent event) throws IOException {

        App.setRoot("startScene");

    }

    @FXML
    public void newGame(ActionEvent event) throws IOException {
        App.setRoot("gameScene");
        App.stage.setWidth(550);

        var enteredPlayersByColor = new HashMap<Color, String>();
        enteredPlayersByColor.put(Color.GREEN, greenName.getText());
        enteredPlayersByColor.put(Color.YELLOW, yellowName.getText());
        enteredPlayersByColor.put(Color.BLUE, blueName.getText());
        enteredPlayersByColor.put(Color.RED, redName.getText());

        var gameState = new GameState();

        gameState.intializeGameState(enteredPlayersByColor);
        gameState.renderPieces();

    }

}

