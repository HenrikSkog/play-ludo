package org.ludo.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import org.ludo.App;
import org.ludo.gameLogic.Game;
import org.ludo.utils.gameSaving.LudoFileHandler;
import org.ludo.utils.gameSaving.LudoSaveHandler;
import org.ludo.utils.gameSaving.SerializedGameState;

import java.io.IOException;

public class LoadGameSceneController{

    @FXML
    private VBox savedGamesVBox;

    @FXML
    private void initialize() {
        LudoFileHandler ludoFileHandler = new LudoSaveHandler();
        ludoFileHandler.getSavedGames().forEach(gameFilename -> {
            Button saveButton = new Button(gameFilename.substring(0, gameFilename.length() - 5));
            saveButton.setOnAction(event -> {
                try {
                    loadGame(ludoFileHandler.buildGameFromFile(gameFilename));
                } catch (IOException e) {
                    //TODO: give feedback to user that error occured
                    System.out.println("error loading game");
                    e.printStackTrace();
                }
            });
            savedGamesVBox.getChildren().add(saveButton);
        });
    }

    public void back() throws IOException {
        App.setRoot("startScene");
    }

    private void loadGame(Game game) throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("fxml/gameScene.fxml"));

        Scene activeScene = App.getScene();
        activeScene.setRoot(loader.load());

        game.initGraphics();
        game.start();

        GameSceneController controller = loader.getController();
        controller.setGameState(game);
    }
}
