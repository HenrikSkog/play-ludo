package org.ludo.gameLogic;

import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Player {
  private ArrayList<Piece> pieces;
  private String name;
  private Color color;

  public Player(String name, Color color) {
    this.name = name;
    this.color = color;
  }

  //TODO: is this tight coupling, and should therefore be implemented differently?
  public void setNewPieces() {
    for (int i = 0; i < 4; i++) {
      pieces.add(new Piece(color));
    }
  }
}
