package org.ludo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.ludo.filesaving.LudoFileHandler;
import org.ludo.filesaving.LudoSaveHandler;
import org.ludo.gamelogic.*;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

public class SaveHandlerTest {

    @Test
    @DisplayName("Testing saving to file and reading from file, and comparing the game-obects")
    public void testSavingGame() throws IOException {
        LudoFileHandler gameSaver = new LudoSaveHandler();

        Game game = new Game();
        String[] names = new String[]{"testnavn1", "testnavn2", "testnavnj3", "testnavn4"};

        game.initState(names);

        game.saveGame();

        int num = gameSaver.getSavedGames().size();
        Game loadedGame = gameSaver.buildGameFromFile("gamesave#" + num + ".txt");
        for (int i = 0; i < 4; i++) {
           assertEquals(names[i], loadedGame.getPlayers().get(i).getName());
            for (int j = 0; j < 4; j++) {
                assertEquals(game.getPlayers().get(i).getPieces().get(j).getInitialPosIndex(), loadedGame.getPlayers().get(i).getPieces().get(j).getInitialPosIndex());
                assertEquals(game.getPlayers().get(i).getPieces().get(j).getBoardArea(), loadedGame.getPlayers().get(i).getPieces().get(j).getBoardArea());
                assertEquals(game.getPlayers().get(i).getPieces().get(j).getPosIndex(), loadedGame.getPlayers().get(i).getPieces().get(j).getPosIndex());
            }
        }
    }
}
