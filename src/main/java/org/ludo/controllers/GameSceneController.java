package org.ludo.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import org.ludo.gameLogic.Die;
import org.ludo.gameLogic.GameInitialState;

public class GameSceneController {
    @FXML
    private Pane gameContainer;

    @FXML
    private Button dieBtn;

    @FXML
    private Text dieTextOutput;

    @FXML
    private Label player1Label;

    @FXML
    private Label player2Label;

    @FXML
    private Label player3Label;

    @FXML
    private Label player4Label;


    @FXML
    private void initialize(){
        GameInitialState.setGameContainer(gameContainer);
        GameInitialState.setPlayerLabels(new Label[] {player1Label, player2Label, player3Label, player4Label});
        Die.setDieTextOutput(dieTextOutput);
        Die.setDieBtn(dieBtn);
    }
}
