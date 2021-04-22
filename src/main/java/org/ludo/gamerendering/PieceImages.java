package org.ludo.gamerendering;

import javafx.scene.image.Image;
import org.ludo.App;

import java.util.HashMap;

public class PieceImages {
    public HashMap<String, Image> pieceImages= new HashMap();

    public PieceImages(int scale) {
        this.pieceImages.put("green", getImage("greenpiece", scale, scale));
        this.pieceImages.put("yellow", getImage("yellowpiece", scale, scale));
        this.pieceImages.put("red", getImage("redpiece", scale, scale));
        this.pieceImages.put("blue", getImage("bluepiece", scale, scale));
    }

    private Image getImage(String source, int width, int height) {
        return new Image(App.class.getResource("assets/" + source + ".png").toExternalForm(), width, height, false, false);
    }

    public Image getPieceImage(String color) {
        return pieceImages.get(color);
    }
}
