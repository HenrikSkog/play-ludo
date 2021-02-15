package org.ludo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.ludo.gameLogic.Board;
import org.ludo.gameLogic.BoardPosition;
import org.ludo.gameLogic.GameInitialState;
import org.ludo.gameLogic.Piece;
import org.ludo.gameRendering.GameRenderer;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("gameScene"));
        scene.getStylesheets().add(getClass().getResource("css/styles.css").toExternalForm());

        //var test = new Board();

        // GameRenderer.addRenderable(test);
        // GameRenderer.renderObjects();

        stage.setScene(scene);

        var piece = new Piece(Color.GREEN, 49);

        System.out.println(piece.getLayoutY());

        GameInitialState.getGameContainer().getChildren().add(piece);

        System.out.println(Board.getPositions());

        System.out.println("Printing in app: " + Board.betweenColorsBoardPositions.get(0));

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