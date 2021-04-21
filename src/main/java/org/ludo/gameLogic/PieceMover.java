package org.ludo.gameLogic;

import java.util.ArrayList;
import java.util.List;

public class PieceMover {
    private final ArrayList<Player> players;
    private List<PieceMoverObserver> observers = new ArrayList<>();

    public PieceMover(ArrayList<Player> players) {
        this.players = players;
    }

    public void move(Piece piece, int dieResult) {
        Player player = players.get(piece.getColorIndex());

        try {
            switch (piece.getBoardArea()) {
                case YARD:
                    if (dieResult == 6 && player.hasAllPiecesInYard()) {
                        moveOutOfYard(piece, dieResult);
                    } else if (dieResult == 6 && piece.getBoardArea().equals(Areas.YARD)) {
                        moveOutOfYard(piece, dieResult);
                        handleLandingOnAnotherPiece(piece);
                    }
                    break;
                case GAMETRACK:
                    int passVal = willPassHomeColumnEntranceWith(piece, dieResult);
                    if (passVal != -1) {
                        moveToHomeColumn(piece, passVal);
                    } else {
                        moveOnGameTrack(piece, dieResult);
                        handleLandingOnAnotherPiece(piece);
                    }
                    break;
                case HOMECOLUMN:
                    moveOnHomeColumn(piece, dieResult);
                    break;
            }
            alertPieceMoved(piece);
        } catch (Error error) {
            System.out.println("Tried to move" + toString() + "with dieResult " + dieResult);
        }
    }

    private void moveOnGameTrack(Piece piece, int dieResult) throws IllegalArgumentException {
        if(!piece.getBoardArea().equals(Areas.GAMETRACK))
            throw new IllegalArgumentException("Tried to move piece on game track when it was not on game track");
        setPosIndex(piece, (piece.getPosIndex()+ dieResult > 51) ? dieResult - (52 - piece.getPosIndex()) : piece.getPosIndex() + dieResult);
    }

    private void moveToHomeColumn(Piece piece, int dieResult) throws IllegalArgumentException {
        if(!piece.getBoardArea().equals(Areas.GAMETRACK))
            throw new IllegalArgumentException("Tried to move piece from gametrack to home column when it was not on game track");
        setPosIndex(piece, dieResult + piece.getColorIndex()*6);
        setBoardArea(piece, Areas.HOMECOLUMN);
    }

    private void moveOnHomeColumn(Piece piece, int dieResult){
        if(((piece.getPosIndex()) % 6 + dieResult) > 5) {
            moveToGoal(piece);
        } else {
            setPosIndex(piece, piece.getPosIndex() + dieResult);
        }
    }

    private void moveOutOfYard(Piece piece, int dieResult) throws IllegalArgumentException{
        if(!piece.getBoardArea().equals(Areas.YARD) || dieResult != 6)
            throw new IllegalArgumentException("Tried to move piece out of yard with another die result than 6 or piece not on gameTrack");
        setBoardArea(piece, Areas.GAMETRACK);
        setPosIndex(piece, 13*piece.getColorIndex());
    }

    private void moveToYard(Piece piece) throws IllegalStateException{
        if(piece.getBoardArea().equals(Areas.YARD)) {
            throw new IllegalStateException("Piece already in yard!");
        }
        setBoardArea(piece, Areas.YARD);
        setPosIndex(piece, piece.getInitialPosIndex());
    }

    private void moveToGoal(Piece piece) {
        setBoardArea(piece, Areas.GOAL);
        setPosIndex(piece, piece.getInitialPosIndex());
    }

    private int willPassHomeColumnEntranceWith(Piece piece, int dieResult) {
        //find lane
        int colorLane = (piece.getColorIndex() - 1) % 4;

        //java gives remainder and not modulo:
        if(colorLane<0) {
            colorLane += 4;
        }
        int currentLane = (piece.getPosIndex() / 13);
        int currentLaneIndex = piece.getPosIndex() % 13;

        if (currentLane == colorLane && currentLaneIndex + dieResult >= 13) {
            return currentLaneIndex + dieResult - 13;
        }
        return -1;
    }

    private void handleLandingOnAnotherPiece(Piece piece) {
        players.forEach(player -> player.getPieces().forEach(playerPiece -> {
            if (piece.getPosIndex() == playerPiece.getPosIndex() && piece != playerPiece && playerPiece.getBoardArea().equals(Areas.GAMETRACK) && piece.getColorIndex() != player.getColorIndex()) {
                moveToYard(playerPiece);
                alertPieceMoved(playerPiece);
            }
        }));
    }

    private void setPosIndex(Piece piece, int posIndex) throws IllegalArgumentException {
        if(posIndex < 0) throw new IllegalArgumentException("Cannot set index to negative number");
        piece.setPosIndex(posIndex);
    }

    private void setBoardArea(Piece piece, Areas boardArea) throws IllegalArgumentException{
        //if board area is accepted as Areas, it is allowed
        piece.setBoardArea(boardArea);
    }

    private void alertPieceMoved(Piece piece) {
        observers.forEach(pieceMoverObserver -> pieceMoverObserver.handlePieceMoved(piece));
    }

    public void subscribeToPieceMoveAlerts(PieceMoverObserver observer) {
        observers.add(observer);
    }
}
