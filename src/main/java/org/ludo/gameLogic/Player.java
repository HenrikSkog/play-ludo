package org.ludo.gameLogic;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Arrays;

public class Player {
  private ArrayList<Piece> pieces;
  private String name;
  private Color color;

  public Player(String name, Color color) {
    setName(name);
    setColor(color);
  }

  /**
   * Sets the players color with validation
   * @param color
   * @throws IllegalArgumentException
   */
  private void setColor(Color color) throws IllegalArgumentException {
    var allowedColors = new Color[]{Color.GREEN, Color.YELLOW, Color.BLUE, Color.RED};

    if (!Arrays.asList(allowedColors).contains(color)) {
      throw new IllegalArgumentException("Color has to be red, blue, yellow or green");
    } else {
      this.color = color;
    }
  }

  public void setPieces() {

  }

  /**
   * Sets the players name with validation
   * @param name
   * @throws IllegalArgumentException
   */
  private void setName(String name) throws IllegalArgumentException{
    if(name.length() < 1) {
      throw new IllegalArgumentException("Name cannot be empty string");
    } else {
      this.name = name;
    }
  }


  //TODO: is this tight coupling, and should therefore be implemented differently?
  public void setNewPieces() {
    for (int i = 0; i < 4; i++) {
//      pieces.add(new Piece(color));
    }
  }
}
