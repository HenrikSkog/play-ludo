package org.ludo.gameLogic;

public interface GameGridInterface {
  int getRowCount();
  // Returns the number of columns in this StringGrid
  int getColumnCount();

  // Returns the String at the given row and column. Throws an IllegalArgumentException if the row or column is out of range
  String getElement(int row, int column);
  // Sets the String at the given row and column. Throws an IllegalArgumentException if the row or column is out of range
  void setElement(int row, int column, String element);
}
