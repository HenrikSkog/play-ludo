package org.ludo.gameLogic;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import org.ludo.App;
import org.ludo.gameRendering.Renderable;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class Board {
  final public static ArrayList<BoardPosition> betweenColorsBoardPositions = new ArrayList<>();
  final private static int scale = 25;

  @Override
  public abstract String toString();

  static {
    generateBetweenColorPositions();
  }


  private static void generateBetweenColorPositions() {
//    starting at green home as index 0.
//    Appending long right
    for (int i = 0; i < 5; i++) {
      betweenColorsBoardPositions.add(new BoardPosition(13+scale+scale*i, 13+scale*6));
    }

//    Long up
    for (int i = 5; i > 0; i--) {
      betweenColorsBoardPositions.add(new BoardPosition(13+scale*6, 13+scale*i));
    }

//    Short right
    for (int i = 0; i < 3; i++) {
      betweenColorsBoardPositions.add(new BoardPosition(13+scale*6+scale*i, 0+13));
    }

//    Long down
    for (int i = 1; i < 6; i++) {
      betweenColorsBoardPositions.add(new BoardPosition(13+scale*8, scale*i+13));
    }

//    Long right
    for (int i = 0; i < 5; i++) {
      betweenColorsBoardPositions.add(new BoardPosition(13+scale*9+scale*i, scale*6+13));
    }

//    Short down
    for (int i = 0; i < 3; i++) {
      betweenColorsBoardPositions.add(new BoardPosition(13+scale*14, scale*6+scale*i+13));
    }

//    Long left
    for (int i = 5; i >= 1; i--) {
      betweenColorsBoardPositions.add(new BoardPosition(13+scale*8+scale*i, scale*8+13));
    }

//    Long down
    for (int i = 0; i < 5; i++) {
      betweenColorsBoardPositions.add(new BoardPosition(13+scale*8, scale*9+scale*i+13));
    }

//    Short left
    for (int i = 3; i > 0; i--) {
      betweenColorsBoardPositions.add(new BoardPosition(13+scale*5+scale*i, scale*14+13));
    }

//    Long up
    for (int i = 5; i > 0; i--) {
      betweenColorsBoardPositions.add(new BoardPosition(13+scale*6, scale*8+scale*i+13));
    }

//    Long left
    for (int i = 5; i >= 1; i--) {
      betweenColorsBoardPositions.add(new BoardPosition(13+scale*i, scale*8+13));
    }

//    Short up
    for (int i = 3; i > 0; i--) {
      betweenColorsBoardPositions.add(new BoardPosition(13+0, scale*5+scale*i+13));
    }
  }

  public static String getPositions() {
    return "Board{" + betweenColorsBoardPositions.toString();
  }

  public static double getBoardPositionY(int index) {
    return betweenColorsBoardPositions.get(index).getY();
  }

  public static double getBoardPositionX(int index) {
    return betweenColorsBoardPositions.get(index).getX();
  }
}
