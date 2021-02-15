package org.ludo.gameLogic;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

public class BoardPosition {
    private int x;
    private int y;

    private Color color;

    public BoardPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "BoardPosition{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
