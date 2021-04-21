package org.ludo.gameLogic;

import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class Die {
    private int lastThrow;

    public int roll() {
        int result = (int)(Math.random() * 6 + 1);
        return result;
    }

    public int getLastRoll() {
        return lastThrow;
    }
}
