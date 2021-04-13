package org.ludo.utils;

import javafx.scene.image.Image;
import org.ludo.App;
import org.ludo.gameLogic.BoardPositions;

import java.util.HashMap;

public class PieceImages {
    public static HashMap<String, Image> pieceImages= new HashMap();

    private static Image getImage(String source, int width, int height) {
        return new Image(App.class.getResource("assets/" + source + ".png").toExternalForm(), width, height, false, false);
    }

    static {
        int pieceWidth = BoardPositions.getScale();
        PieceImages.pieceImages.put("green", getImage("greenpiece", pieceWidth, pieceWidth));
        PieceImages.pieceImages.put("yellow", getImage("yellowpiece", pieceWidth, pieceWidth));
        PieceImages.pieceImages.put("red", getImage("redpiece", pieceWidth, pieceWidth));
        PieceImages.pieceImages.put("blue", getImage("bluepiece", pieceWidth, pieceWidth));
    }

    public static Image getPieceImage(String color) {
        return pieceImages.get(color);
    }
}
