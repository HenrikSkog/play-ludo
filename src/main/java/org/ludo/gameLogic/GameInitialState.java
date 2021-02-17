package org.ludo.gameLogic;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class GameInitialState {
    private static BoardPosition[] boardpositions;
    private static Pane gameContainer;
    private static Label[] playerLabels;
    private static String[] playerNames;

    public static Pane getGameContainer() {
        return gameContainer;
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

    public static String[] getPlayerNames() {
        return playerNames;
    }

    public static void setPlayerNames(String[] playerNames) {
        GameInitialState.playerNames = playerNames;
    }
}
