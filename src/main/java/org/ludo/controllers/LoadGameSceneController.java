package org.ludo.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.ludo.App;
import org.ludo.gameLogic.Game;
import org.ludo.utils.gameSaving.LudoFileHandler;
import org.ludo.utils.gameSaving.LudoSaveHandler;

import java.io.IOException;

public class LoadGameSceneController{

    @FXML
    private VBox savedGamesVBox;

    @FXML
    private Text errorText;

    @FXML
    private void initialize() {
        //TODO: bilde på load game
        LudoFileHandler ludoFileHandler = new LudoSaveHandler();
        ludoFileHandler.getSavedGames().forEach(gameFilename -> {
            Button saveButton = new Button(gameFilename.substring(0, gameFilename.length() - 4));
            saveButton.setOnAction(event -> {
                try {
                    loadGame(ludoFileHandler.buildGameFromFile(gameFilename));
                } catch (IOException e) {
                    errorText.setText("Error loading game.");
                }
            });
            savedGamesVBox.getChildren().add(saveButton);
        });
    }

    @FXML
    private void back() throws IOException {
        App.setRoot("startScene");
    }

    private void loadGame(Game game) throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("fxml/gameScene.fxml"));

        Scene activeScene = App.getScene();
        activeScene.setRoot(loader.load());

        GameSceneController controller = loader.getController();
        controller.setGameState(game);
        controller.startGame();
    }
}
