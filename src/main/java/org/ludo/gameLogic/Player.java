package org.ludo.gameLogic;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Player {
  public ArrayList<Piece> pieces = new ArrayList<>();
  private String name;
  private Color color;

  public Player(String name, Color color) {
    setName(name);
    setColor(color);

    initializePieces();
  }

  //TODO: is this tight coupling, and should therefore be implemented differently?
  public void initializePieces() {
    for (int i = 0; i < 4; i++) {
      int pieceIndex = Board.getIndexOfColor(color)*4+i;

      pieces.add(new Piece(color, pieceIndex));
    }
  }

  public void renderPieces() {
    for (Piece piece: pieces) {
      piece.render();
    }
  }

  public List<Piece> getPiecesInYard() {
    return pieces.stream().filter(piece -> piece.getCurrentBoardPositionArea() == "yard").collect(Collectors.toList());
  }

  public List<Piece> getPiecesInGameTrack() {
    return pieces.stream().filter(piece -> piece.getCurrentBoardPositionArea() == "gameTrack").collect(Collectors.toList());
  }
  public List<Piece> getPiecesInHomeColumn() {
    return pieces.stream().filter(piece -> piece.getCurrentBoardPositionArea() == "homeColumn").collect(Collectors.toList());
  }

  public ArrayList<Piece> getPieces() {
    return pieces;
  }

  /**
   * Sets the players color with validation
   * @param color sets the color
   * @throws IllegalArgumentException if something goes wrong
   */
  private void setColor(Color color) throws IllegalArgumentException {
    if (!Arrays.asList(Board.colorOrder).contains(color)) {
      throw new IllegalArgumentException("Color has to be red, blue, yellow or green");
    } else {
      this.color = color;
    }
  }

  /**
   * Sets the players name with validation
   * @param name
   * @throws IllegalArgumentException
   */
  private void setName(String name) throws IllegalArgumentException {
    if(name.length() < 1) {
      throw new IllegalArgumentException("Name cannot be empty");
    } else {
      this.name = name;
    }
  }

  @Override
  public String toString() {
    return "Player{" +
            "pieces=" + pieces +
            ", name='" + name + '\'' +
            ", color=" + color +
            '}';
  }
}
