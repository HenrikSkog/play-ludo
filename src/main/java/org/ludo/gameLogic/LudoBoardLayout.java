package org.ludo.gameLogic;

import java.util.*;

public class LudoBoardLayout implements CoordinateMapper {
    private int scale;

    final public ArrayList<XYCoordinate> gameTrackPositions = new ArrayList<>();
    final public ArrayList<XYCoordinate> yardPositions = new ArrayList<>();
    final public ArrayList<XYCoordinate> homeColumnPositions = new ArrayList<>();
    final public ArrayList<XYCoordinate> goalPositions = new ArrayList<>();

    final private Map<String, ArrayList<XYCoordinate>> boardLayout = new HashMap<>();

    private ArrayList<String> allowedBoardPositionAreas = new ArrayList<>();

    public LudoBoardLayout(int scale) {
        this.scale = scale;
        initBoard();
    }

    private void initBoard() {
        generateGameTrackPositions();
        generateHomePositions();
        generateHomeColumnPositions();
        generateGoalPositions();

        initBoardPositionAreas();
    }

    private void initBoardPositionAreas() {
        boardLayout.put("yard", yardPositions);
        boardLayout.put("gameTrack", gameTrackPositions);
        boardLayout.put("homeColumn", homeColumnPositions);
        boardLayout.put("goal", goalPositions);

        allowedBoardPositionAreas.add("yard");
        allowedBoardPositionAreas.add("gameTrack");
        allowedBoardPositionAreas.add("homeColumn");
        allowedBoardPositionAreas.add("goal");
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
        yardPositions.add(new BoardPosition(x + scale, y));
//    left square
        yardPositions.add(new BoardPosition(x, y + scale));
//    bottom square
        yardPositions.add(new BoardPosition(x + scale, y + scale * 2));
//    right square
        yardPositions.add(new BoardPosition(x + scale * 2, y + scale));
    }

    private void generateHomeColumnPositions() {
        //first green route
        homeColumnPositions.add(new BoardPosition(13 + scale * 1, 13 + scale * 6));

        //Green horizontal homeColumn
        for (int i = 0; i < 5; i++) {
            homeColumnPositions.add(new BoardPosition(13 + scale + scale * i, 13 + scale * 7));
        }
        //Yellow single route
        homeColumnPositions.add((new BoardPosition(13 + scale * 8, 13 + scale * 1)));

        //Yellow vertical homeColumn
        for (int i = 1; i < 6; i++) {
            homeColumnPositions.add((new BoardPosition(13 + scale * 7, 13 + scale * i)));
        }
        //First blue route
        homeColumnPositions.add(new BoardPosition(13 + scale * 13, 13 + scale * 8));

        //Blue horizontal homeColumn
        for (int i = 13; i > 8; i--) {
            homeColumnPositions.add(new BoardPosition(13 + scale * i, 13 + scale * 7));
        }
        //First red route
        homeColumnPositions.add(new BoardPosition(13 + scale * 6, 13 + scale * 13));

        //Vertical red route
        for (int i = 13; i > 8; i--) {
            homeColumnPositions.add(new BoardPosition(13 + scale * 7, 13 + scale * i));
        }


    }

    private void generateGameTrackPositions() {
//    starting at green home as index 0.
//    Appending long right
        for (int i = 0; i < 5; i++) {
            gameTrackPositions.add(new BoardPosition(13 + scale + scale * i, 13 + scale * 6));
        }

//    Long up
        for (int i = 5; i > 0; i--) {
            gameTrackPositions.add(new BoardPosition(13 + scale * 6, 13 + scale * i));
        }

//    Short right
        for (int i = 0; i < 3; i++) {
            gameTrackPositions.add(new BoardPosition(13 + scale * 6 + scale * i, 0 + 13));
        }

//    Long down
        for (int i = 1; i < 6; i++) {
            gameTrackPositions.add(new BoardPosition(13 + scale * 8, scale * i + 13));
        }

//    Long right
        for (int i = 0; i < 5; i++) {
            gameTrackPositions.add(new BoardPosition(13 + scale * 9 + scale * i, scale * 6 + 13));
        }

//    Short down
        for (int i = 0; i < 3; i++) {
            gameTrackPositions.add(new BoardPosition(13 + scale * 14, scale * 6 + scale * i + 13));
        }

//    Long left
        for (int i = 5; i >= 1; i--) {
            gameTrackPositions.add(new BoardPosition(13 + scale * 8 + scale * i, scale * 8 + 13));
        }

//    Long down
        for (int i = 0; i < 5; i++) {
            gameTrackPositions.add(new BoardPosition(13 + scale * 8, scale * 9 + scale * i + 13));
        }

//    Short left
        for (int i = 3; i > 0; i--) {
            gameTrackPositions.add(new BoardPosition(13 + scale * 5 + scale * i, scale * 14 + 13));
        }

//    Long up
        for (int i = 5; i > 0; i--) {
            gameTrackPositions.add(new BoardPosition(13 + scale * 6, scale * 8 + scale * i + 13));
        }

//    Long left
        for (int i = 5; i >= 1; i--) {
            gameTrackPositions.add(new BoardPosition(13 + scale * i, scale * 8 + 13));
        }

//    Short up
        for (int i = 3; i > 0; i--) {
            gameTrackPositions.add(new BoardPosition(13 + 0, scale * 5 + scale * i + 13));
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

    @Override
    public int getY(GameObject gameObject) {
        return boardLayout.get(gameObject.getBoardArea()).get(gameObject.getPosIndex()).getY();
    }

    @Override
    public int getX(GameObject gameObject) {
        return boardLayout.get(gameObject.getBoardArea()).get(gameObject.getPosIndex()).getX();
    }

    public int getBoardPositionsLength(String area) {
        return boardLayout.get(area).size();
    }

    public ArrayList<String> getAllowedBoardPositionAreas() {
        return allowedBoardPositionAreas;
    }

    public int getScale() {
        return scale;
    }
}