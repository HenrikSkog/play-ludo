package org.ludo.gameLogic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Player implements Serializable {
  private ArrayList<Piece> pieces = new ArrayList<>();
  private String name;
  private int colorIndex;

  public Player(String name, int colorIndex) {
    setName(name);
    this.colorIndex = colorIndex;
    initializePieces();
  }

  public void initializePieces() {
    for (int i = 0; i < 4; i++) {
      var piece = new Piece(colorIndex, colorIndex * 4 + i);
      piece.setPieceNode(new GameObjectNode(piece));
      pieces.add(piece);
    }
  }

  public List<Piece> getPiecesInYard() {
    return pieces.stream().filter(piece -> piece.getBoardArea() == "yard").collect(Collectors.toList());
  }

  public List<Piece> getPiecesInGameTrack() {
    return pieces.stream().filter(piece -> piece.getBoardArea() == "gameTrack").collect(Collectors.toList());
  }

  public List<Piece> getPiecesInHomeColumn() {
    return pieces.stream().filter(piece -> piece.getBoardArea() == "homeColumn").collect(Collectors.toList());
  }

  public ArrayList<Piece> getPieces() {
    return pieces;
  }

  private void setName(String name) throws IllegalArgumentException {
    if (name.length() < 1) {
      throw new IllegalArgumentException("Name cannot be empty");
    } else {
      this.name = name;
    }
  }

  public boolean hasAllPiecesInYard() {
    if (getPiecesInYard().size() == 4)
      return true;
    return false;
  }

  public int getColorIndex() {
    return colorIndex;
  }

  public HashMap<String, Object> getState() {
    var stateVars = new HashMap<String, Object>();
    stateVars.put("name", name);
    stateVars.put("colorIndex", colorIndex);
    stateVars.put("pieces", getPieces().stream().map(Piece::getState).collect(Collectors.toList()));
    return stateVars;
  }

  @Override
  public String toString() {
    return "Player{" +
        "pieces=" + pieces +
        ", name='" + name + '\'' +
        '}';
  }
}