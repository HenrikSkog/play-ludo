package org.ludo.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.ludo.App;
import org.ludo.gameLogic.FXMLElements;
import org.ludo.gameLogic.GameEngine;

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
	private Button goBackButton;

	@FXML
	void goBackMethod(ActionEvent event) throws IOException {
		App.setRoot("startScene");
	}

	@FXML
	public void newGame(ActionEvent event) throws IOException {
		FXMLElements.getStage().setWidth(520);
		FXMLElements.getStage().setHeight(450);

		FXMLLoader loader = new FXMLLoader(App.class.getResource("fxml/gameScene.fxml"));

		Scene activeScene = App.getScene();
		activeScene.setRoot(loader.load());

		var gameEngine = new GameEngine();
		gameEngine.initState(greenName.getText(), yellowName.getText(), redName.getText(), blueName.getText());
		gameEngine.start();

        GameSceneController controller = loader.getController();
        controller.setGameState(gameEngine);


	}

}

