package org.ludo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.ludo.gameLogic.Game;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameTest {
   @Test
   @DisplayName("Game inits state correctly")
   public void testInitState() {
      Game game = new Game();
      String[] names = new String[]{"Henrik", "Erlend", "Sebastian", "Julian"};
      game.initState(names);
       for (int i = 0; i < 4; i++) {
          assertEquals(names[i], game.getPlayers().get(i).getName());
       }
   }
}
