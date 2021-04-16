package org.ludo.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import org.ludo.App;
import org.ludo.gameLogic.FXMLElements;
import org.ludo.gameLogic.Game;

import java.io.IOException;

public class NewGameController extends GameSceneController {
	@FXML
	public TextField greenName;
	@FXML
	public TextField yellowName;
	@FXML
	public TextField blueName;
	@FXML
	public TextField redName;

	@FXML
	private void goBackMethod() throws IOException {
		App.setRoot("startScene");
	}

	@FXML
	public void newGame() throws IOException {
		FXMLElements.getStage().setWidth(600);
		FXMLElements.getStage().setHeight(600);

		FXMLLoader loader = new FXMLLoader(App.class.getResource("fxml/gameScene.fxml"));

		Scene activeScene = App.getScene();
		activeScene.setRoot(loader.load());

		var game = new Game();
		game.initState(new String[]{"green", "yellow", "blue", "red"}, greenName.getText(), yellowName.getText(), redName.getText(), blueName.getText());
		game.initGraphics();
		game.start();

        GameSceneController controller = loader.getController();
        controller.setGameState(game);
	}
}

