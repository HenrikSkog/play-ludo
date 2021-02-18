package org.ludo.gameLogic;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.util.Arrays;

public class GameInitialState {
    private static Pane gameContainer;
    private static Label[] playerLabels;
    private static TextField[] playerText;

    public static Pane getGameContainer() {
        return gameContainer;
    }

    public static void setGameContainer(Pane gameContainer) {
        GameInitialState.gameContainer = gameContainer;
    }


    public static void setPlayerLabels(Label[] labels) {
        GameInitialState.playerLabels = labels;
    }

    public static void setPlayerLabelsTextValues(String[] textValues) throws IllegalArgumentException{
        if(playerLabels.length != textValues.length)
            throw new IllegalArgumentException("Number of text values does not match number of labels");

        for (int i = 0; i < playerLabels.length; i++) {
            playerLabels[i].setText(textValues[i]);
        }
    }

    public static Label[] getPlayerLabels() {
        return playerLabels;
    }


}
