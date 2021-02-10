package org.ludo.gameLogic;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import org.ludo.App;
import org.ludo.gameRendering.Renderable;

import java.util.ArrayList;

public class Board implements Renderable {
  Image backgroundBoardImage;


  public Board() {
    this.backgroundBoardImage = new Image(App.class.getResource("assets/board.png").toExternalForm(), 400, 400, false, false);
  }

  @Override
  public void render(GraphicsContext gc) {
    gc.drawImage(backgroundBoardImage, 0, 0);
  }
}
