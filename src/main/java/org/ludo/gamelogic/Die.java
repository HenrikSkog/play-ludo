package org.ludo.gamelogic;

public class Die implements DieInterface {
    private int lastThrow;

    @Override
    public int roll() {
        int result = (int)(Math.random() * 6 + 1);
        this.lastThrow = result;
        return result;
    }

    @Override
    public int getLastRoll() {
        return lastThrow;
    }
}
