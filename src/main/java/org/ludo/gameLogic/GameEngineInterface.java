package org.ludo.gameLogic;

import java.util.ArrayList;

public interface GameEngineInterface {
  ArrayList<Player> getPlayers();
  int getCurrentPlayerTurn();
  void saveGame();
}