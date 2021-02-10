package org.ludo.gameLogic;

import javafx.scene.paint.Color;

public class Piece {
  private int index;
  private Color color;

  public Piece(Color color) {
    this.color = color;
  }

  public int getIndex() {
    return index;
  }

  public Color getColor() {
    return color;
  }

  public void move(int steps) {
    this.index += steps;
  }
}
