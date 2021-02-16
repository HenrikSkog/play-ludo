package org.ludo.gameLogic;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public abstract class Board {


  final public static ArrayList<BoardPosition> gameTrackPositions = new ArrayList<>();
  final public static ArrayList<BoardPosition> yardPositions = new ArrayList<>();

  final private static int scale = 25;

  final private static Map<String, ArrayList<BoardPosition>> boardPositionAreas = new HashMap<>();

  final public static Color[] colorOrder = {Color.GREEN, Color.YELLOW, Color.BLUE, Color.RED};
  private static ArrayList<String> allowedBoardPositionAreas = new ArrayList<>();

  static {
    generateBetweenColorPositions();
    generateHomePositions();
    initBoardPositionAreas();
  }

  private static void initBoardPositionAreas() {
    boardPositionAreas.put("yard", yardPositions);
    boardPositionAreas.put("gameTrack", gameTrackPositions);
    boardPositionAreas.put("homeColumn", gameTrackPositions);


    allowedBoardPositionAreas.add("yard");
    allowedBoardPositionAreas.add("gameTrack");
  }

  private static void generateHomePositions() {
//    starting at green home as index 0
//    green
    generateOneHomePosition(scale*2, scale*2);
//    yellow
    generateOneHomePosition(scale*11, scale*2);
//    blue
    generateOneHomePosition(scale*11, scale*11);
//    red
    generateOneHomePosition(scale*2, scale*11);
  }

  private static void generateOneHomePosition(int x, int y) {
//    top square
    yardPositions.add(new BoardPosition(x + scale, y));
//    left square
    yardPositions.add(new BoardPosition(x, y + scale));
//    bottom square
    yardPositions.add(new BoardPosition(x + scale, y+scale*2));
//    right square
    yardPositions.add(new BoardPosition(x + scale*2, y+scale));
  }


  private static void generateBetweenColorPositions() {
//    starting at green home as index 0.
//    Appending long right
    for (int i = 0; i < 5; i++) {
      gameTrackPositions.add(new BoardPosition(13+scale+scale*i, 13+scale*6));
    }

//    Long up
    for (int i = 5; i > 0; i--) {
      gameTrackPositions.add(new BoardPosition(13+scale*6, 13+scale*i));
    }

//    Short right
    for (int i = 0; i < 3; i++) {
      gameTrackPositions.add(new BoardPosition(13+scale*6+scale*i, 0+13));
    }

//    Long down
    for (int i = 1; i < 6; i++) {
      gameTrackPositions.add(new BoardPosition(13+scale*8, scale*i+13));
    }

//    Long right
    for (int i = 0; i < 5; i++) {
      gameTrackPositions.add(new BoardPosition(13+scale*9+scale*i, scale*6+13));
    }

//    Short down
    for (int i = 0; i < 3; i++) {
      gameTrackPositions.add(new BoardPosition(13+scale*14, scale*6+scale*i+13));
    }

//    Long left
    for (int i = 5; i >= 1; i--) {
      gameTrackPositions.add(new BoardPosition(13+scale*8+scale*i, scale*8+13));
    }

//    Long down
    for (int i = 0; i < 5; i++) {
      gameTrackPositions.add(new BoardPosition(13+scale*8, scale*9+scale*i+13));
    }

//    Short left
    for (int i = 3; i > 0; i--) {
      gameTrackPositions.add(new BoardPosition(13+scale*5+scale*i, scale*14+13));
    }

//    Long up
    for (int i = 5; i > 0; i--) {
      gameTrackPositions.add(new BoardPosition(13+scale*6, scale*8+scale*i+13));
    }

//    Long left
    for (int i = 5; i >= 1; i--) {
      gameTrackPositions.add(new BoardPosition(13+scale*i, scale*8+13));
    }

//    Short up
    for (int i = 3; i > 0; i--) {
      gameTrackPositions.add(new BoardPosition(13+0, scale*5+scale*i+13));
    }
  }

  public static double getBoardPositionY(String area, int index) {
    return boardPositionAreas.get(area).get(index).getY();
  }

  public static double getBoardPositionX(String area, int index) {
    return boardPositionAreas.get(area).get(index).getX();
  }

  public static Color getColorByOrder(int index) {
    return colorOrder[index];
  }

  public static int getIndexOfColor(Color color) {
    return Arrays.asList(colorOrder).indexOf(color);
  }

  public static ArrayList<String> getAllowedBoardPositionAreas() {
    return allowedBoardPositionAreas;
  }
}
