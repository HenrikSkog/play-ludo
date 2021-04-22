package org.ludo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.ludo.gamelogic.Die;
import org.ludo.gamelogic.Game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DieTest {
    @Test
    @DisplayName("Test die")
    public void testInitState() {
        Die die = new Die();
        assertEquals(0, die.getLastRoll());

        int res = die.roll();
        assertEquals(res, die.getLastRoll());

        for (int i = 0; i < 100; i++) {
            int res1 = die.roll();
            assertTrue(res1 > 0 && res <= 6);
        }
    }
}
