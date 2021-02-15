package org.ludo.gameLogic;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import org.ludo.utils.Helper;
import org.ludo.utils.PieceImages;

public class GameInitialState {
    private static BoardPosition[] boardpositions;
    private static Pane gameContainer;

    public static void setGameContainer(Pane gameContainer) {
        GameInitialState.gameContainer = gameContainer;
    }

    public static Pane getGameContainer() {
        return gameContainer;
    }
}
