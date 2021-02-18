package org.ludo.gameLogic;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.HashMap;


//TODO: move currentTurnTries to player? Allows for further encapsulation of for example makeMove to Piece class
public class GameState {
    private ArrayList<Player> players = new ArrayList<>();

    private int currentPlayerTurn = 0;
    private Player currentPlayer;
    private int currentTurnTries = 3;


    public void intializeGameState(HashMap<Color, String> enteredPlayers) {
        setPlayers(enteredPlayers);
        decideStartingPlayer();
        indicatePlayerTurn();
        currentPlayer = players.get(currentPlayerTurn);

        Die.getDieBtn().setOnMouseClicked(event -> handleDieRoll());
    }

    private void handleDieRoll() {
        int dieResult = Die.roll();
        if(dieResult != 6)
            currentTurnTries -= 1;

        if(currentTurnTries == 0 && currentPlayer.onlyHasPiecesInYard()) {
            nextPlayer();
        } else {
            enableMoveToPlayer(currentPlayer.getPieces(), dieResult);
        }
    }

    private void enableMoveToPlayer(ArrayList<Piece> pieces, int dieResult) {
        for (Piece piece: pieces) {
            piece.setOnMouseClicked(event -> {
                makeMove(piece, dieResult);
                removePieceListeners(pieces);
                if(currentTurnTries == 0)
                    nextPlayer();
            });
        }
    }

    public void makeMove(Piece piece, int dieResult) {
        if(dieResult == 6 && piece.getCurrentBoardPositionArea() == "yard") {
            piece.movePieceOutOfYard();
            currentTurnTries = 1;
        } else if(piece.getCurrentBoardPositionArea() == "gameTrack") {
            piece.movePieceOnGameTrack(dieResult);
        }
    }

    private void removePieceListeners(ArrayList<Piece> pieces) {
        for (Piece piece: pieces) {
            piece.setOnMouseClicked(null);
        }
    }

    private void nextPlayer() {
        removePieceListeners(currentPlayer.getPieces());

        currentPlayerTurn = (currentPlayerTurn == players.size()-1) ? 0 : currentPlayerTurn + 1;
        currentPlayer = players.get(currentPlayerTurn);

        indicatePlayerTurn();

        if(players.get(currentPlayerTurn).onlyHasPiecesInYard())
            currentTurnTries = 3;
        else
            currentTurnTries = 1;
    }

    public void renderPieces() {
        getPlayers().stream().forEach(player -> player.renderPieces());

    }

    private void indicatePlayerTurn() {
        for (int i = 0; i < GameInitialState.getPlayerLabels().length; i++) {
            if(i == currentPlayerTurn)
                GameInitialState.getPlayerLabels()[i].setUnderline(true);
            else
                GameInitialState.getPlayerLabels()[i].setUnderline(false);
        }
    }

    public void setPlayers(HashMap<Color, String> enteredPlayers) throws IllegalArgumentException {
        if(enteredPlayers.size() > 4) {
            throw new IllegalArgumentException("There cannot be more than 4 players");
        }

        int playerIndex = 0;
        for(String playerName : playerNames) {
            //TODO: move line under to dedicated class for rendering stuff maybe
            GameInitialState.getPlayerLabels()[playerIndex].setText(playerName);

            var player = new Player(playerName, Board.getColorByOrder(playerIndex));
            players.add(player);

            playerIndex += 1;
        }
    }

    private void decideStartingPlayer() {
        for (Color color: Board.getColorByOrder()) {

        }
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }
}
