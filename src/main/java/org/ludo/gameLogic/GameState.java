package org.ludo.gameLogic;

import javafx.scene.paint.Color;

import java.util.ArrayList;


//TODO: move currentTurnTries to player? Allows for further encapsulation of for example makeMove to Piece class
public class GameState {
    private ArrayList<Player> players = new ArrayList<>();
    private int currentTurnPlayer = 0;
    private int currentTurnTries = 3;


    public void intializeGameState(String ... playerNames) {
        setPlayers(playerNames);
        indicatePlayerTurn();

        Die.getDieBtn().setOnMouseClicked(event -> handleDieRoll());
    }

    private void handleDieRoll() {
        var currentPlayer = players.get(currentTurnPlayer);

        int dieResult = Die.roll();
        if(dieResult != 6)
            currentTurnTries -= 1;

        if(currentTurnTries == 0 && currentPlayer.getPiecesInYard().size() == 4) {
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
            if (!(willPassHomeColumnEntranceWith(piece, dieResult) == -1)){
                piece.movePieceOnHomeColumn(willPassHomeColumnEntranceWith(piece, dieResult));
            }
            }
            piece.movePieceOnGameTrack(dieResult);
        }

    private void removePieceListeners(ArrayList<Piece> pieces) {
        for (Piece piece: pieces) {
            piece.setOnMouseClicked(null);
        }
    }

    private void nextPlayer() {
        removePieceListeners(players.get(currentTurnPlayer).getPieces());
        currentTurnPlayer = (currentTurnPlayer == players.size()-1) ? 0 : currentTurnPlayer + 1;
        indicatePlayerTurn();
        if(players.get(currentTurnPlayer).getPiecesInYard().size() == 4)
            currentTurnTries = 3;
        else
            currentTurnTries = 1;

    }

    private int willPassHomeColumnEntranceWith(Piece piece, int dieResult) {
        var gameTrackIndex = piece.getIndex();
        var colorIndex = Board.getIndexOfColor(piece.getColor());
        // is piece in proximity
        if ((gameTrackIndex >= colorIndex*13-6 && gameTrackIndex <= colorIndex*13-1) || (piece.getColor()==Color.GREEN && gameTrackIndex >= 46 && gameTrackIndex <= 51)){
            if (gameTrackIndex + dieResult > colorIndex*13) {
                return gameTrackIndex + dieResult - colorIndex * 13;
            }
        }
        return -1;
    }


    public void renderPieces() {
        getPlayers().stream().forEach(player -> player.renderPieces());

    }

    private void indicatePlayerTurn() {
        for (int i = 0; i < GameInitialState.getPlayerLabels().length; i++) {
            if(i == currentTurnPlayer)
                GameInitialState.getPlayerLabels()[i].setUnderline(true);
            else
                GameInitialState.getPlayerLabels()[i].setUnderline(false);
        }
    }

    private void addMouseClick() {
        players.stream().forEach(player ->
                player.pieces.stream().forEach(piece ->
                        piece.setOnMouseClicked(event ->
                                piece.movePieceOutOfYard())));
    }

    public void setPlayers(String[] playerNames) throws IllegalArgumentException {
        if(playerNames.length > 4) {
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

    public ArrayList<Player> getPlayers() {
        return players;
    }
}
