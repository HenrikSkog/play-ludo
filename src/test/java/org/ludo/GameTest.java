package org.ludo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.ludo.gameLogic.Game;

public class GameTest {
   private Game game;

   @BeforeEach
   public void init() {
      game = new Game();
      game.initState(new String[]{"Henrik", "Erlend", "Sebastian", "Julian"}, "green", "yellow", "blue", "red");
   }

   @Test
    public void test() {
      System.out.println("hello");
   }

}
