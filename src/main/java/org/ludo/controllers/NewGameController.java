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
import org.ludo.gameLogic.GameEngineInterface;
import org.ludo.gameLogic.LudoBoardLayout;

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
		FXMLLoader loader = new FXMLLoader(App.class.getResource("fxml/gameScene.fxml"));

		Scene activeScene = App.getScene();
		activeScene.setRoot(loader.load());

		var gameEngine = new GameEngine();
		gameEngine.init(greenName.getText(), yellowName.getText(), redName.getText(), blueName.getText());

		gameEngine.getRenderer().renderPieces();

        GameSceneController controller = loader.getController();
        controller.setGameState(gameEngine);
	}

}

