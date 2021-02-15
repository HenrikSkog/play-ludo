package org.ludo.utils;

import javafx.scene.image.Image;
import org.ludo.App;

public class Helper {



    public static Image getImage(String source) {
        return new Image(App.class.getResource("assets/" + source + ".png").toExternalForm(), false);
    }

    public static Image getImage(String source, int width, int height) {
        return new Image(App.class.getResource("assets/" + source + ".png").toExternalForm(), width, height, false, false);
    }
}
