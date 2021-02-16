package org.ludo.gameLogic;

import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.ArrayList;

public class GameTurn {
    private Player player;
    private int tries;
    private int dieResult;
    private GameState gameState;


    public GameTurn(Player player, GameState gameState) {
        this.player = player;
        this.gameState = gameState;
        setTries();
        indicatePlayerTurn();
    }

    public void startTurn() {
        Die.getDieBtn().setOnMouseClicked(event -> handleDieRoll());
    }

    public void endTurn() {
        removePieceListeners();
    }

    private void indicatePlayerTurn() {
        for (int i = 0; i < GameInitialState.getPlayerLabels().length; i++) {
            if (i == player.getIndex())
                GameInitialState.getPlayerLabels()[i].setFont(Font.font(null, FontWeight.BOLD, 16));
            else
                GameInitialState.getPlayerLabels()[i].setFont(Font.font(null, FontWeight.NORMAL, 14));
        }
    }

    private void handleDieRoll() {
        this.dieResult = Die.roll();
        if (dieResult != 6)
            tries -= 1;

//      player has not managed to move out of yard, no pieces can be moved on turn
        if (tries == 0 && player.getPiecesInYard().size() == 4) {
            endTurn();
        } else {
            letUserPickPieceToMove();
        }
    }

    private void letUserPickPieceToMove() {
        for (Piece piece : player.getPieces()) {
            piece.setOnMouseClicked(event -> {
                movePiece(piece);
//              player should only be able to interact once with pieces each turn
                removePieceListeners();
                if (tries == 0)
                    endTurn();
            });
        }
    }

    public void movePiece(Piece piece) {
        if (dieResult == 6 && piece.getArea() == "yard") {
            piece.movePieceOutOfYard();
            tries = 1;
        } else if (piece.getArea() == "gameTrack") {
            piece.movePieceOnGameTrack(dieResult);
        }
    }

    private void removePieceListeners() {
        for (Piece piece : player.getPieces()) {
            piece.setOnMouseClicked(null);
        }
    }


    public void setTries() {
        if (player.getPiecesInYard().size() == 4)
            this.tries = 3;
        else
            this.tries = 1;
    }
}
