package org.ludo.gamelogic;

import org.ludo.filesaving.LudoFileHandler;
import org.ludo.gamerendering.DieAnimator;
import org.ludo.gamerendering.GameRenderer;
import org.ludo.filesaving.LudoSaveHandler;

import java.io.IOException;
import java.util.*;


public class Game {
    private ArrayList<Player> players = new ArrayList<>();
    private final GameRenderer gameRenderer = new GameRenderer(this);
    private PieceMover pieceMover;
    private final DieInterface die = new Die();

    //about current turn
    private int currentPlayerTurn;
    private Player currentPlayer;
    private int currentTurnTries;
    private boolean hasThrownDiceInCurrentTurn = false;

    public void initState(String[] playerNames) {
        setPlayers(playerNames);
        currentTurnTries = 3;
        currentPlayerTurn = 0;
        currentPlayer = players.get(currentPlayerTurn);
    }

    public void loadState(ArrayList<Player> players, int currentPlayerTurn) throws IllegalArgumentException {
        if(players.size() != 4 || currentPlayerTurn < 0 || currentPlayerTurn > 3) {
           throw new IllegalArgumentException("Illegal arguments");
        }

        this.players = players;
        this.currentPlayerTurn = currentPlayerTurn;
        this.currentPlayer = players.get(currentPlayerTurn);
        this.currentTurnTries = (currentPlayer.hasAllPiecesInYardOrGoal()) ? 3 : 1;
    }

    public void start() {
        pieceMover = new PieceMover(players);
        pieceMover.subscribeToPieceMoveAlerts(gameRenderer);
        gameRenderer.initGraphics();
        enableDieRoll();
    }

    private void handleDieRoll() {
        hasThrownDiceInCurrentTurn = true;

        int dieResult = die.roll();
        DieAnimator dieAnimator = new DieAnimator(gameRenderer.getDieText(), gameRenderer.getDieBtn(), die.getLastRoll(), dieResult);

        Timer timer = new Timer();

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if (currentPlayer.hasAllPiecesInYardOrGoal()) {
                    handleDieRollWhenAllInYard(dieResult);
                } else {
                    handleDieRollWhenOutOfYard(dieResult);
                }
            }
        };

        int delay = 1000;
        int flashes = 10;
        int rotations = 4;

        disableDieRoll();
        dieAnimator.animate(delay, rotations, flashes, dieResult);
        timer.schedule(task, delay + 10);
    }

    private void enableMoveToPlayer(int dieResult) {
        for (Piece piece : currentPlayer.getPieces()) {
//    if dieResult not 6 and piece in yard or piece in goal, dont add listener to it
            if (!(piece.getBoardArea().equals(Areas.YARD)  && dieResult != 6) || piece.getBoardArea().equals(Areas.GOAL)) {
                piece.getPieceNode().setOnMouseClicked(event -> {
                    pieceMover.move(piece, dieResult);
                    removePieceListeners();
                    enableDieRoll();
                    if (currentTurnTries == 0)
                        nextPlayer();
                });
            }
        }
    }

    private void nextPlayer() {
        hasThrownDiceInCurrentTurn = false;
        removePieceListeners();
        enableDieRoll();

        currentPlayerTurn = (currentPlayerTurn == (players.size() - 1)) ? 0 : (currentPlayerTurn + 1);
        currentPlayer = players.get(currentPlayerTurn);

        gameRenderer.indicatePlayerTurn();
        if (currentPlayer.hasAllPiecesInYardOrGoal())
            currentTurnTries = 3;
        else
            currentTurnTries = 1;
    }

    private void handleDieRollWhenOutOfYard(int dieResult) {
        if (dieResult != 6) {
            currentTurnTries--;
        }
        enableMoveToPlayer(dieResult);
        disableDieRoll();
    }

    private void handleDieRollWhenAllInYard(int dieResult) {
        currentTurnTries -= 1;

        if (dieResult == 6) {
            enableMoveToPlayer(dieResult);
            disableDieRoll();
            currentTurnTries = 1;
        } else if (currentTurnTries == 0) {
            nextPlayer();
        } else {
            enableDieRoll();
        }
    }

    private void enableDieRoll() {
        gameRenderer.getDieBtn().setOnMouseClicked(event -> handleDieRoll());
    }

    private void disableDieRoll() {
        gameRenderer.getDieBtn().setOnMouseClicked(null);
    }

    private void removePieceListeners() {
        for (Piece piece : currentPlayer.getPieces()) {
            piece.getPieceNode().setOnMouseClicked(null);
        }
    }

    public void setPlayers(String[] playerNames) throws IllegalArgumentException {
        if (playerNames.length != 4) {
            throw new IllegalArgumentException("There has to be 4 players");
        }

        int playerIndex = 0;
        for (String playerName : playerNames) {
            if (playerName.equals("")) {
                throw new IllegalArgumentException("There cannot be empty names");
            }

            Player player = new Player(playerName, playerIndex);
            player.initializePieces();
            players.add(player);
            playerIndex += 1;
        }
    }

    public int getCurrentPlayerTurn() {
        return currentPlayerTurn;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void saveGame() throws IOException, IllegalArgumentException {
        if (hasThrownDiceInCurrentTurn) {
            throw new IllegalStateException("You can't save in the middle of a turn!");
        }
        LudoFileHandler gamesaver = new LudoSaveHandler();
        gamesaver.saveGame(this);
    }

    public GameRenderer getGameRenderer() {
        return gameRenderer;
    }

    @Override
    public String toString() {
        return "Game{" +
                "players=" + players +
                ", gameRenderer=" + gameRenderer +
                ", pieceMover=" + pieceMover +
                ", currentPlayerTurn=" + currentPlayerTurn +
                ", currentPlayer=" + currentPlayer +
                ", currentTurnTries=" + currentTurnTries +
                ", hasThrownDiceInCurrentTurn=" + hasThrownDiceInCurrentTurn +
                '}';
    }
}