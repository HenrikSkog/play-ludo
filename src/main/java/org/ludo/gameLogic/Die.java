package org.ludo.gameLogic;

import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class Die {
    static Text dieTextOutput;
    static Button dieBtn;

    public static int roll() {
        int result = (int)(Math.random() * 6 + 1);
        dieTextOutput.setText(Integer.toString(result));
        return result;
    }

    public static Button getDieBtn() {
        return dieBtn;
    }

    public static void setDieTextOutput(Text dieTextOutput) {
        Die.dieTextOutput = dieTextOutput;
    }

    public static void setDieBtn(Button dieBtn) {
        Die.dieBtn = dieBtn;
    }
}
