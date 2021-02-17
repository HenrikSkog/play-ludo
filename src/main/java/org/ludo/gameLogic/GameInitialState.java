package org.ludo.gameLogic;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class GameInitialState {
    private static BoardPosition[] boardpositions;
    private static Pane gameContainer;
    private static Label[] playerLabels;
    private static TextField[] playerText;

    public static Pane getGameContainer() {
        return gameContainer;
    }
    public static void setPlayerNames(){
        for (Label player : playerLabels) {
            player.setText("hei");
        }
    }

    public static void setGameContainer(Pane gameContainer) {
        GameInitialState.gameContainer = gameContainer;
    }


    public static void setPlayerLabels(Label[] labels) {
        GameInitialState.playerLabels = labels;
    }

    public static Label[] getPlayerLabels() {
        return playerLabels;
    }

}
