package org.ludo.gameLogic;

import com.google.gson.Gson;
import org.ludo.gameRendering.DieAnimator;
import org.ludo.gameRendering.GameRenderer;
import org.ludo.utils.gameSaving.GameSaveHandler;
import org.ludo.utils.gameSaving.SerializedGameState;
import org.ludo.utils.gameSaving.Serializer;

import java.util.*;
import java.util.stream.Collectors;


public class GameEngine implements GameEngineInterface, Serializable {
    private static final GameEngine gameEngine = new GameEngine();

    private final ArrayList<Player> players = new ArrayList<>();
    private final GameRenderer gameRenderer = new GameRenderer(this);
    private final PieceMover pieceMover = new PieceMover();

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

    public void initState(SerializedGameState loadedSerializedGameState) {
        loadedSerializedGameState.getPlayers().forEach(serializedPlayer -> {
            var player = new Player(serializedPlayer.getName(), serializedPlayer.getColorIndex());
            player.initializePieces(serializedPlayer.getPieces());
            player.initializePieceNodes();

            players.add(player);
        });

        currentTurnTries = loadedSerializedGameState.getCurrentTurnTries();
        currentPlayerTurn = loadedSerializedGameState.getCurrentPlayerTurn();
        currentPlayer = players.get(currentPlayerTurn);
        System.out.println(currentPlayer);
    }

    public void start() {
        gameRenderer.indicatePlayerTurn();
        gameRenderer.renderPieces();
        enableDieRoll();
    }

    private void handleDieRoll() {
        System.out.println(currentTurnTries +  currentPlayerTurn);
        hasThrownDiceInCurrentTurn = true;
        var die = new Die(FXMLElements.getDieTextOutput(), FXMLElements.getDieBtn());

        String lastDieRoll = FXMLElements.getDieTextOutput().getText();
        int lastDieRollInt;

        if (lastDieRoll.equals(""))
            lastDieRollInt = 1;
        else
            lastDieRollInt = Integer.parseInt(lastDieRoll);
        var dieResult = die.roll();
        var dieAnimator = new DieAnimator(FXMLElements.getDieTextOutput(), FXMLElements.getDieBtn(), lastDieRollInt, dieResult);

        var timer = new Timer();

        var task = new TimerTask() {
            @Override
            public void run() {
                if (currentPlayer.hasAllPiecesInYard()) {
                    System.out.println("handling in yard");
                    handleDieRollWhenAllInYard(dieResult);
                } else {
                    System.out.println("handling out of yard");
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
            if (!(piece.getBoardArea().equals("yard") && dieResult != 6)) {
                piece.getPieceNode().setOnMouseClicked(event -> {
                    piece.move(dieResult, currentPlayer, players);
                    removePieceListeners();
                    enableDieRoll();
                    if (currentTurnTries == 0)
                        nextPlayer();
                    gameRenderer.reRenderPieces();
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
        System.out.println("forsøk: " + currentTurnTries);
        if (dieResult != 6) {
            currentTurnTries--;
        }
        System.out.println("etter minus: " + currentTurnTries);
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
            System.out.println("Next player");
            nextPlayer();
        } else {
            enableDieRoll();
        }
    }

    private void enableDieRoll() {
        FXMLElements.getDieBtn().setOnMouseClicked(event -> handleDieRoll());
    }

    private void disableDieRoll() {
        FXMLElements.getDieBtn().setOnMouseClicked(null);
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
            //TODO: move line under to dedicated class for rendering stuff maybe
            if (!playerName.equals("")) {
                FXMLElements.getPlayerLabels()[playerIndex].setText(playerName);

                var player = new Player(playerName, playerIndex);
                player.initializePieces();
                player.initializePieceNodes();

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

    public GameRenderer getRenderer() {
        return gameRenderer;
    }

    public HashMap<String, Object> getState() {
        HashMap stateVars = new HashMap<String, Object>();
        stateVars.put("currentPlayerTurn", currentPlayerTurn);
        stateVars.put("currentTurnTries", currentTurnTries);
        stateVars.put("players", getPlayers().stream().map(player -> player.getState()).collect(Collectors.toList()));
        return stateVars;
    }

    public void saveGame() {
        if (hasThrownDiceInCurrentTurn) {
            throw new IllegalStateException("You can't save in the middle of a turn!");
        }
        var gamesaver = new GameSaveHandler();
        String gameStateJSON = Serializer.serialize(getState());
        gamesaver.saveGame(gameStateJSON);
    }
}