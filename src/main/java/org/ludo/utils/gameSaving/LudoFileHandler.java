package org.ludo.utils.gameSaving;

import org.ludo.gameLogic.Game;

import java.io.IOException;
import java.util.ArrayList;

public interface LudoFileHandler {
    void saveGame(Game game) throws IOException;
    ArrayList<String> getSavedGames();
    Game buildGameFromFile(String filename) throws IOException;
}