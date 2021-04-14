package org.ludo.utils.gameSaving;

import java.io.IOException;
import java.util.ArrayList;

public interface FileHandler {
    void saveGame(String content);
    ArrayList<String> getSavedGames();
    SerializedGameState loadGameSave(String filename) throws IOException;
}
