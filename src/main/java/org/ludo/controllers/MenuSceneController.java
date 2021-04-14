package org.ludo.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import org.ludo.gameLogic.GameEngineInterface;

public class MenuSceneController {
	private GameEngineInterface gameEngine;


	@FXML
	private void handleSaveGame() {
		gameEngine.saveGame();
	}

	@FXML
	private void quitGame(){
		Platform.exit();
	}

	public void setGameEngine(GameEngineInterface gameEngine) {
		this.gameEngine = gameEngine;
	}
}
