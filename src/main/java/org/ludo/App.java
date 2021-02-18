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
    //TODO: Gå inn i HomeColumn etter en runde
    //TODO: Ta brikker hvis en lander på en annen
    //TODO: Fikse navngiving når man kommer inn i et nytt spill
    //TODO: Lagring av gamestate

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("StartScene"));
        scene.getStylesheets().add(getClass().getResource("css/styles.css").toExternalForm());

        stage.setScene(scene);

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