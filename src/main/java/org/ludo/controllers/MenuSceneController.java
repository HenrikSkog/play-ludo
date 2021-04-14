package org.ludo.controllers;

import javafx.fxml.FXML;
import org.ludo.gameLogic.GameEngine;
import org.ludo.gameLogic.GameEngineInterface;

public class MenuSceneController {
	private GameEngineInterface gameEngine;


	@FXML
	private void handleSaveGame() {
		gameEngine.saveGame();
	}

	@FXML
	private void exit() {
		System.out.println("EXITNG");
	}

	public void setGameEngine(GameEngineInterface gameEngine) {
		this.gameEngine = gameEngine;
	}
}
