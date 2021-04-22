package org.ludo.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.ludo.App;
import org.ludo.gamelogic.Game;

import java.io.IOException;

public class NewGameController extends GameSceneController {
	@FXML
	private TextField greenName;
	@FXML
	private TextField yellowName;
	@FXML
	private TextField blueName;
	@FXML
	private TextField redName;
	@FXML
	private Text errorText;

	@FXML
	private void goBackMethod() throws IOException {
		App.setRoot("startScene");
	}

	@FXML
	private void newGame() throws IOException {
		try {
			FXMLLoader loader = new FXMLLoader(App.class.getResource("fxml/gameScene.fxml"));
			Game game = new Game();
			game.initState(new String[]{greenName.getText(), yellowName.getText(), redName.getText(), blueName.getText()});
			Scene activeScene = App.getScene();
			activeScene.setRoot(loader.load());

			GameSceneController controller = loader.getController();
			controller.setGameState(game);
			controller.startGame();
		} catch(Exception e) {
			errorText.setText("Error in initializing names");
		}


	}
}

