package org.ludo.gameLogic;

import javafx.scene.Node;
import javafx.scene.control.Button;
import org.ludo.gameRendering.DieAnimator;
import org.ludo.gameRendering.ObjectRenderer;
import org.ludo.utils.gameSaving.GameSaveHandler;
import org.ludo.utils.gameSaving.SerializedGameState;
import org.ludo.utils.gameSaving.Serializer;

import java.util.*;
import java.util.stream.Collectors;


public class GameEngine implements GameEngineInterface, Serializable {
    private final ArrayList<Player> players = new ArrayList<>();
    private ArrayList<GameListener> gameListeners;

    //about current turn
    private Player turnPlayer;
    private int turnTries;
    private int thrownDice;

    private Button diceButton;
    private ArrayList<Node> pieces;

    //TODO: burde nok flytte dette til en egen klasse, og flytte constructern her ut i egen metode.
    public void loadGameState(SerializedGameState loadedSerializedGameState) {

    }

    public void gameLoop() {
       while(true) {
           //enable dice roll until success
           enableDieRoll();
           if(!successFullDice()) {
              if(turnTries == 0) {
                  nextPlayer();
              } else {
                  turnTries--;
              }
               continue;
           }
           //if it makes it here, the dice throw has been successful
           enableMoveToPlayer();
           //if not success, determine next player and go to it
           //if success, disable dice roll and enable piece move
           //determine next player and go to it
       }
    }

    public void subscribe(GameListener gameListener) {
       gameListeners.add(gameListener);
    }

    private void updateListeners() {
        for (GameListener gamelistener: gameListeners ) {
           gameListener.update(this);
        }
    }

    public void init(String... playerNames) {
        setPlayers(new ArrayList<>(Arrays.asList(playerNames)));
        objectRenderer.indicatePlayerTurn();
        enableDieRoll();

        currentTurnTries = 3;
        currentPlayerTurn = 0;
        currentPlayer = players.get(currentPlayerTurn);
    }

    private void handleDieRoll() {
        System.out.println("tries:" + currentTurnTries);
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
        for (Piece piece : turnPlayer.getPieces()) {
//    if dieResult not 6 and piece in yard, dont add listener to it
            if (!(piece.getBoardArea().equals("yard") && dieResult != 6)) {
                piece.getPieceNode().setOnMouseClicked(event -> {
                    piece.move(dieResult, currentPlayer, players);
                    removePieceListeners();
                    enableDieRoll();
                    if (currentTurnTries == 0)
                        nextPlayer();
                    objectRenderer.reRenderPieces();
                });
            }
        }
    }

    private void nextPlayer() {
        removePieceListeners();
        enableDieRoll();

        currentPlayerTurn = (currentPlayerTurn == (players.size() - 1)) ? 0 : (currentPlayerTurn + 1);
        currentPlayer = players.get(currentPlayerTurn);

        objectRenderer.indicatePlayerTurn();
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

                players.add(new Player(playerName, playerIndex));
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

    public ObjectRenderer getRenderer() {
        return objectRenderer;
    }

    public HashMap<String, Object> getState() {
        HashMap stateVars = new HashMap<String, Object>();
        stateVars.put("currentPlayerTurn", currentPlayerTurn);
        stateVars.put("currentTurnTries", currentTurnTries);
        stateVars.put("players", getPlayers().stream().map(player -> player.getState()).collect(Collectors.toList()));
        return stateVars;
    }

    public void saveGame() {
        var gamesaver = new GameSaveHandler();
        String gameStateJSON = Serializer.serialize(getState());
        gamesaver.saveGame(gameStateJSON);