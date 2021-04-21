package org.ludo.gameLogic;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Player {
    private ArrayList<Piece> pieces = new ArrayList<>();
    private String name;
    private int colorIndex;

    public Player(String name, int colorIndex) {
        setName(name);
        this.colorIndex = colorIndex;
    }

    public void initializePieces() {
        for (int i = 0; i < 4; i++) {
            var piece = new Piece(colorIndex, colorIndex * 4 + i);
            pieces.add(piece);
        }
    }

    public void initializePieceNodes(String color, int scale) {
      if(this.pieces.size() == 0) {
          throw new IllegalStateException("No pieces to initialize nodes for");
      }
      pieces.forEach(piece -> piece.setPieceNode(new PieceNode(piece, color, scale)));
    }

    public List<Piece> getPiecesInYard() {
        return pieces.stream().filter(piece -> piece.getBoardArea().equals(Areas.YARD)).collect(Collectors.toList());
    }

    public ArrayList<Piece> getPieces() {
        return pieces;
    }

    public void setPieces(ArrayList<Piece> pieces) throws IllegalArgumentException {
        if(pieces.stream().anyMatch(piece -> piece.getColorIndex() != this.colorIndex)) throw new IllegalArgumentException("Colors dont match");
        this.pieces = pieces;
    }

    private void setName(String name) throws IllegalArgumentException {
        if (name.length() < 1) {
            throw new IllegalArgumentException("Name cannot be empty");
        } else {
            this.name = name;
        }
    }

    public boolean hasAllPiecesInYard() {
        return getPiecesInYard().size() == 4;
    }

    public int getColorIndex() {
        return colorIndex;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Player{" +
                "pieces=" + pieces +
                ", name='" + name + '\'' +
                ", colorIndex=" + colorIndex +
                '}';
    }
}