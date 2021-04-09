package org.ludo.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.ludo.App;
import org.ludo.gameLogic.FXMLElements;
import org.ludo.gameLogic.GameEngine;
import org.ludo.utils.gameSaving.GameSaveHandler;
import org.ludo.utils.gameSaving.SerializedGameState;
import org.ludo.utils.gameSaving.Serializer;

import java.io.IOException;

public class LoadGameSceneController {
	@FXML
	private VBox savedGamesVBox;

	@FXML
	private void initialize() {
		GameSaveHandler.getSavedGames().forEach(gameSaveName -> {
			Button gameSaveBtn = new Button(gameSaveName);
			gameSaveBtn.setOnAction(event -> {
				FXMLElements.getStage().setWidth(520);
				FXMLLoader loader = new FXMLLoader(App.class.getResource("fxml/gameScene.fxml"));

				Scene activeScene = App.getScene();
				try {
					activeScene.setRoot(loader.load());
				} catch (IOException e) {
					e.printStackTrace();
				}

				SerializedGameState gameState = null;

				try {
					gameState = GameSaveHandler.loadGameSave(gameSaveName);
				} catch (IOException e) {
					e.printStackTrace();
				}

				//var gameEngine = GameEngine.getInstance();
				//gameEngine.loadGameState(gameState);
				//gameEngine.getRenderer().renderPieces();

				//GameSceneController controller = loader.getController();
				//controller.setGameState(gameEngine);
			});
			savedGamesVBox.getChildren().add(gameSaveBtn);
		});
	}
}
