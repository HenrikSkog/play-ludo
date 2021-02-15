package org.ludo.utils;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.util.HashMap;

public class PieceImages {
    public static HashMap<Color, Image> pieceImages= new HashMap();
    private static int pieceWidth = 25;

    static {
        PieceImages.pieceImages.put(Color.GREEN, Helper.getImage("greenpiece", pieceWidth, pieceWidth));
        PieceImages.pieceImages.put(Color.YELLOW, Helper.getImage("yellowpiece", pieceWidth, pieceWidth));
        PieceImages.pieceImages.put(Color.RED, Helper.getImage("redpiece", pieceWidth, pieceWidth));
        PieceImages.pieceImages.put(Color.BLUE, Helper.getImage("bluepiece", pieceWidth, pieceWidth));
    }

    public static Image getPieceImage(Color color) {
        return pieceImages.get(color);
    }
}
