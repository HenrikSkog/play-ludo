package org.ludo.gameLogic;

import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class GameTurn {
    private Player player;
    private int tries;


    public GameTurn(Player player) {
        this.player = player;
    }

    private void indicatePlayerTurn() {
        for (int i = 0; i < GameInitialState.getPlayerLabels().length; i++) {
            if(i == player.getIndex())
                GameInitialState.getPlayerLabels()[i].setFont(Font.font(null, FontWeight.BOLD, 16));
            else
                GameInitialState.getPlayerLabels()[i].setFont(Font.font(null, FontWeight.NORMAL, 14));
        }
    }
}
