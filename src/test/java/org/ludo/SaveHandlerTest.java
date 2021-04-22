package org.ludo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.ludo.gamelogic.Game;

import java.io.IOException;

public class SaveHandlerTest {

    @Test
    @DisplayName("Test save game")
    public void testSavingGame() throws IOException {
        Game game = new Game();
        game.initState(new String[]{"henrik", "erlend", "skog", "Sorknes"});

        try {

        }
        game.saveGame();

    }
}
