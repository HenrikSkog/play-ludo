package org.ludo.filesaving;

import org.ludo.gamelogic.Game;

import java.io.IOException;
import java.util.ArrayList;

public interface LudoFileHandler {
    void saveGame(Game game) throws IOException;
    ArrayList<String> getSavedGames();
    Game buildGameFromFile(String filename) throws IOException;
}