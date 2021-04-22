package org.ludo.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.ludo.App;
import org.ludo.gamelogic.Game;

import java.io.IOException;

public class MenuSceneController {
	private Game game;
	@FXML
	private Text errorText;


	@FXML
	private void handleSaveGame() {
		try {
			game.saveGame();
			errorText.setText("Successfully saved game!");
		} catch(IllegalStateException | IOException e) {
			errorText.setText("Could not save game");
			e.printStackTrace();
		}
	}

	@FXML
	private void exit() {
		Platform.exit();
	}

	@FXML
	private void goToMenu() throws IOException {
		App.setRoot("startScene");
		Stage stage = (Stage) errorText.getScene().getWindow();
		stage.close();
	}

	public void setGameEngine(Game game) {
		this.game = game;
	}
}
