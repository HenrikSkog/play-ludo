package org.ludo.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import org.ludo.gameLogic.Game;

public class MenuSceneController {
	private Game game;


	@FXML
	private void handleSaveGame() {
		game.saveGame();
	}

	@FXML
	private void exit() {
		Platform.exit();
	}

	public void setGameEngine(Game game) {
		this.game = game;
	}
}
