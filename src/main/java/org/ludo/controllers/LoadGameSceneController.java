package org.ludo.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import org.ludo.gameLogic.GameEngine;
import org.ludo.utils.gameSaving.GameSaveHandler;
import org.ludo.utils.gameSaving.FileHandler;

import java.io.IOException;

public class LoadGameSceneController {
    @FXML
    private VBox savedGamesVBox;

    @FXML
    private void initialize() {
        FileHandler fileHandler = new GameSaveHandler();
        fileHandler.getSavedGames().forEach(game -> {
            Button saveButton = new Button(game.substring(0, game.length() - 5));
            saveButton.setOnAction(event -> {
                try {
                    System.out.println(fileHandler.loadGameSave("gamesave#1.json").toString());
                } catch (IOException e) {
                    //TODO: give feedback to user that error occured
                    e.printStackTrace();
                }
            });

            savedGamesVBox.getChildren().add(saveButton);
        });
    }

    private void loadGame(String serializedGameState) {
        var gameEngine = GameEngine.getInstance();
        gameEngine.init();
        gameEngine.getRenderer().renderPieces();

        //GameSceneController controller = loader.getController();
        //controller.setGameState(gameEngine);
    }
}
