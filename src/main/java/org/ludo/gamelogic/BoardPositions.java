package org.ludo.gamelogic;

import java.util.*;

/**
 * Class that generates arrays with board positions that map an area and an index to coordinates on the Ludo board.
 */
public class BoardPositions {
  final private ArrayList<XYCoordinate> gameTrackPositions = new ArrayList<>();
  final private ArrayList<XYCoordinate> yardPositions = new ArrayList<>();
  final private ArrayList<XYCoordinate> homeColumnPositions = new ArrayList<>();
  final private ArrayList<XYCoordinate> goalPositions = new ArrayList<>();

  final private Map<Areas, ArrayList<XYCoordinate>> boardPositionAreas = new HashMap<>();

  private final int scale;
  private final int boardLayoutY;
  private final int boardLayoutX;

  public BoardPositions(int scale, int boardLayoutY, int boardLayoutX) {
    this.scale = scale;
    this.boardLayoutY = boardLayoutY;
    this.boardLayoutX = boardLayoutX;

    generateGameTrackPositions();
    generateHomePositions();
    initBoardPositionAreas();
    generateHomeColumnPositions();
    generateGoalPositions();
  }

  private void initBoardPositionAreas() {
    boardPositionAreas.put(Areas.YARD, yardPositions);
    boardPositionAreas.put(Areas.GAMETRACK, gameTrackPositions);
    boardPositionAreas.put(Areas.HOMECOLUMN, homeColumnPositions);
    boardPositionAreas.put(Areas.GOAL, goalPositions);
  }

  private void generateHomePositions() {
//    starting at green home as index 0
//    green
    generateOneHomePosition(scale * 2, scale * 2);
//    yellow
    generateOneHomePosition(scale * 11, scale * 2);
//    blue
    generateOneHomePosition(scale * 11, scale * 11);
//    red
    generateOneHomePosition(scale * 2, scale * 11);
  }

  private void generateOneHomePosition(int x, int y) {
//    top square
    yardPositions.add(new BoardPosition(boardLayoutX + x + scale, boardLayoutY + y));
//    left square
    yardPositions.add(new BoardPosition(boardLayoutX + x, boardLayoutY + y + scale));
//    bottom square
    yardPositions.add(new BoardPosition(boardLayoutX + x + scale, boardLayoutY + y + scale * 2));
//    right square
    yardPositions.add(new BoardPosition(boardLayoutX + x + scale * 2, boardLayoutY + y + scale));
  }

  private void generateHomeColumnPositions() {
    //first green route
    homeColumnPositions.add(new BoardPosition(boardLayoutX + 13 + scale * 1, boardLayoutY + 13 + scale * 6));

    //Green horizontal homeColumn
    for (int i = 0; i < 5; i++) {
      homeColumnPositions.add(new BoardPosition(boardLayoutX + 13 + scale + scale * i, boardLayoutY + 13 + scale * 7));
    }

    //Yellow single route
    homeColumnPositions.add((new BoardPosition(boardLayoutX + 13 + scale * 8, boardLayoutY + 13 + scale * 1)));

    //Yellow vertical homeColumn
    for (int i = 1; i < 6; i++) {
      homeColumnPositions.add((new BoardPosition(boardLayoutX+ 13 + scale * 7, boardLayoutY + 13 + scale * i)));
    }
    //First blue route
    homeColumnPositions.add(new BoardPosition(boardLayoutX + 13 + scale * 13, boardLayoutY + 13 + scale * 8));

    //Blue horizontal homeColumn
    for (int i = 13; i > 8; i--) {
      homeColumnPositions.add(new BoardPosition(boardLayoutX + 13 + scale * i, boardLayoutY + 13 + scale * 7));
    }
    //First red route
    homeColumnPositions.add(new BoardPosition(boardLayoutX + 13 + scale * 6, boardLayoutY + 13 + scale * 13));

    //Vertical red route
    for (int i = 13; i > 8; i--) {
      homeColumnPositions.add(new BoardPosition(boardLayoutX + 13 + scale * 7, boardLayoutY + 13 + scale * i));
    }


  }

  private void generateGameTrackPositions() {
//    starting at green home as index 0.
//    Appending long right
    for (int i = 0; i < 5; i++) {
      gameTrackPositions.add(new BoardPosition(boardLayoutX + 13 + scale + scale * i, boardLayoutY + 13 + scale * 6));
    }

//    Long up
    for (int i = 5; i > 0; i--) {
      gameTrackPositions.add(new BoardPosition(boardLayoutX + 13 + scale * 6, boardLayoutY + 13 + scale * i));
    }

//    Short right
    for (int i = 0; i < 3; i++) {
      gameTrackPositions.add(new BoardPosition(boardLayoutX + 13 + scale * 6 + scale * i, boardLayoutY + 0 + 13));
    }

//    Long down
    for (int i = 1; i < 6; i++) {
      gameTrackPositions.add(new BoardPosition(boardLayoutX + 13 + scale * 8, boardLayoutY + scale * i + 13));
    }

//    Long right
    for (int i = 0; i < 5; i++) {
      gameTrackPositions.add(new BoardPosition(boardLayoutX + 13 + scale * 9 + scale * i, boardLayoutY + scale * 6 + 13));
    }

//    Short down
    for (int i = 0; i < 3; i++) {
      gameTrackPositions.add(new BoardPosition(boardLayoutX + 13 + scale * 14, boardLayoutY + scale * 6 + scale * i + 13));
    }

//    Long left
    for (int i = 5; i >= 1; i--) {
      gameTrackPositions.add(new BoardPosition(boardLayoutX + 13 + scale * 8 + scale * i, boardLayoutY + scale * 8 + 13));
    }

//    Long down
    for (int i = 0; i < 5; i++) {
      gameTrackPositions.add(new BoardPosition(boardLayoutX + 13 + scale * 8, boardLayoutY + scale * 9 + scale * i + 13));
    }

//    Short left
    for (int i = 3; i > 0; i--) {
      gameTrackPositions.add(new BoardPosition(boardLayoutX + 13 + scale * 5 + scale * i, boardLayoutY + scale * 14 + 13));
    }

//    Long up
    for (int i = 5; i > 0; i--) {
      gameTrackPositions.add(new BoardPosition(boardLayoutX + 13 + scale * 6, boardLayoutY + scale * 8 + scale * i + 13));
    }

//    Long left
    for (int i = 5; i >= 1; i--) {
      gameTrackPositions.add(new BoardPosition(boardLayoutX + 13 + scale * i, boardLayoutY + scale * 8 + 13));
    }

//    Short up
    for (int i = 3; i > 0; i--) {
      gameTrackPositions.add(new BoardPosition(boardLayoutX + 13, boardLayoutY + scale * 5 + scale * i + 13));
    }
  }

  private void generateGoalPositions() {
    for (int i = 0; i < 4; i++) {
      XYCoordinate penultimate = homeColumnPositions.get(i * 6 + 4);
      XYCoordinate last = homeColumnPositions.get(i * 6 + 5);
      int dx = last.getX() - penultimate.getX();
      int dy = last.getY() - penultimate.getY();

      for (int j = 0; j < 4; j++) {
        int x, y;
        if (dx == 0) {
          x = last.getX() - scale / 2 + j * (scale / 3);
          y = last.getY() + dy;
        } else {
          x = last.getX() + dx;
          y = last.getY() - (scale / 2) + j * (scale / 3);
        }
        goalPositions.add(new BoardPosition(x, y));
      }
    }
  }

  public double getBoardPositionY(Areas area, int index) {
    return boardPositionAreas.get(area).get(index).getY();
  }

  public double getBoardPositionX(Areas area, int index) {
    return boardPositionAreas.get(area).get(index).getX();
  }
}
