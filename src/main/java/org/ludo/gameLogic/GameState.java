package org.ludo.gameLogic;

import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class GameState {
    private ArrayList<Player> players = new ArrayList<>();
    private int currentTurnPlayer = 0;

    private ArrayList<GameTurn> gameTurns = new ArrayList<>();
    private GameTurn currentGameTurn;


    public void intializeGameState(String ... playerNames) {
        setPlayers(playerNames);
        var currentGameturn = new GameTurn(players.get(currentTurnPlayer), this);
    }

    private void nextTurn() {
        currentTurnPlayer = (currentTurnPlayer == players.size() - 1) ? 0 : currentTurnPlayer + 1;
        indicatePlayerTurn();
        if (players.get(currentTurnPlayer).getPiecesInYard().size() == 4)
            currentTurnTries = 3;
        else
            currentTurnTries = 1;
    }

    public void renderPieces() {
        getPlayers().stream().forEach(player -> player.renderPieces());
    }

    public void setPlayers(String[] playerNames) throws IllegalArgumentException {
        if(playerNames.length > 4) {
            throw new IllegalArgumentException("There cannot be more than 4 players");
        }

        int playerIndex = 0;
        for(String playerName : playerNames) {

            var player = new Player(playerName, Board.getColorByOrder(playerIndex));

            players.add(player);

            playerIndex += 1;
        }
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }
}
