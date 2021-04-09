package org.ludo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.ludo.gameLogic.*;

import java.io.IOException;
import java.util.ArrayList;

/**
 * JavaFX App
 */
public class App extends Application {
    //TODO: Fikse navngiving når man kommer inn i et nytt spill
    //TODO: Lagring av gamestate

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("StartScene"));
        scene.getStylesheets().add(getClass().getResource("css/styles.css").toExternalForm());

        FXMLElements.setStage(stage);

        stage.setScene(scene);

        stage.show();
    }

    private static void drawRectangle(int x, int y) {
        var rect = new Rectangle(x, y);
        rect.setX(x);
        rect.setY(y);
        rect.setWidth(25);
        rect.setHeight(25);
        FXMLElements.getGameContainer().getChildren().add(rect);
    }

    public static void drawBoardPositions(ArrayList<BoardPosition> array) {
        for (int i = 0; i < array.size(); i++) {
            drawRectangle(array.get(i).getX(), array.get(i).getY());
        }
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    public static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("fxml/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static Scene getScene() {
        return scene;
    }
    public static void main(String[] args) {
        launch();
    }

}