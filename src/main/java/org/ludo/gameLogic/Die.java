package org.ludo.gameLogic;

import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class Die {
    private Text dieTextOutput;
    private Button dieBtn;

    public Die(Text dieTextOutput, Button dieBtn) {
        this.dieTextOutput = dieTextOutput;
        this.dieBtn = dieBtn;
    }

    public int roll() {
        int result = (int)(Math.random() * 6 + 1);
        return result;
    }
}
