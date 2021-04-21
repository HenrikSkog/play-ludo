package org.ludo.gameLogic;

import org.ludo.gameRendering.DieAnimator;
import org.ludo.gameRendering.GameRenderer;
import org.ludo.utils.gameSaving.LudoSaveHandler;

import java.io.IOException;
import java.util.*;


public class Game {
    private ArrayList<Player> players = new ArrayList<>();
    private final GameRenderer gameRenderer = new GameRenderer(this);
    private PieceMover pieceMover;
    private Die die = new Die();

    //about current turn
    private int currentPlayerTurn;
    private Player currentPlayer;
    private int currentTurnTries;
    private boolean hasThrownDiceInCurrentTurn = false;

    public void initState(String... playerNames) {
        setPlayers(new ArrayList<>(Arrays.asList(playerNames)));
        currentTurnTries = 3;
        currentPlayerTurn = 0;
        currentPlayer = players.get(currentPlayerTurn);
    }

    public void loadState(ArrayList<Player> players, int currentPlayerTurn) {
        this.players = players;
        this.currentPlayerTurn = currentPlayerTurn;
        this.currentPlayer = players.get(currentPlayerTurn);
        this.currentTurnTries = (currentPlayer.hasAllPiecesInYard()) ? 3 : 1;
    }

    public void start() {
        pieceMover = new PieceMover(players);
        pieceMover.subscribeToPieceMoveAlerts(gameRenderer);
        gameRenderer.initGraphics();
        enableDieRoll();
    }

    private void handleDieRoll() {
        hasThrownDiceInCurrentTurn = true;

        var dieResult = die.roll();
        var dieAnimator = new DieAnimator(gameRenderer.getDieText(), gameRenderer.getDieBtn(), die.getLastRoll(), dieResult);

        var timer = new Timer();

        var task = new TimerTask() {
            @Override
            public void run() {
                if (currentPlayer.hasAllPiecesInYard()) {
                    handleDieRollWhenAllInYard(dieResult);
                } else {
                    handleDieRollWhenOutOfYard(dieResult);
                }
            }
            // all pieces in yard and no tries left -> only go to next player and dont enable move to player
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
//    if dieResult not 6 and piece in yard, dont add listener to it
            if (!(piece.getBoardArea().equals(Areas.YARD) && dieResult != 6)) {
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
        if (currentPlayer.hasAllPiecesInYard())
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

    public void setPlayers(ArrayList<String> playerNames) throws IllegalArgumentException {
        if (playerNames.size() > 4) {
            throw new IllegalArgumentException("There cannot be more than 4 players");
        }

        int playerIndex = 0;
        for (String playerName : playerNames) {
            if (!playerName.equals("")) {
                var player = new Player(playerName, playerIndex);
                player.initializePieces();
                players.add(player);
            }
            playerIndex += 1;
        }
    }

    public int getCurrentPlayerTurn() {
        return currentPlayerTurn;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void saveGame() throws IOException {
        if (hasThrownDiceInCurrentTurn) {
            throw new IllegalStateException("You can't save in the middle of a turn!");
        }
        var gamesaver = new LudoSaveHandler();
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