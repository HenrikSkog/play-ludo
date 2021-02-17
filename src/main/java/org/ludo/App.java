package org.ludo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.ludo.gameLogic.*;

import java.io.IOException;
import java.util.ArrayList;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("gameScene"));
        scene.getStylesheets().add(getClass().getResource("css/styles.css").toExternalForm());

        stage.setScene(scene);

        var gamestate1 = new GameState();

        gamestate1.intializeGameState("Henrik", "Jørgen", "Ola", "Brage");

        gamestate1.renderPieces();

        drawBoardPositions(Board.homeColumnPositions);

        stage.show();
    }

    private void drawRectangle(int x, int y) {
        var rect = new Rectangle(x, y);
        rect.setX(x);
        rect.setY(y);
        rect.setWidth(25);
        rect.setHeight(25);
        GameInitialState.getGameContainer().getChildren().add(rect);
    }

    private void drawBoardPositions(ArrayList<BoardPosition> array) {
        for (int i = 0; i < array.size(); i++) {
            drawRectangle(array.get(i).getX(), array.get(i).getY());
        }
    }


    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("fxml/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}