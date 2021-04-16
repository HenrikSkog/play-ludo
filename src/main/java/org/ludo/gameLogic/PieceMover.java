package org.ludo.gameLogic;

import java.util.ArrayList;

public class PieceMover {
    private final ArrayList<Player> players;
    private final BoardPositions boardPositions;

    public PieceMover(ArrayList<Player> players, BoardPositions boardPositions) {
        this.players = players;
        this.boardPositions = boardPositions;
    }

    public void move(Piece piece, int dieResult) {
        Player player = players.get(piece.getColorIndex());

        try {
            switch (piece.getBoardArea()) {
                case "yard":
                    if (dieResult == 6 && player.hasAllPiecesInYard()) {
                        moveToGameTrack(piece, dieResult);
                    } else if (dieResult == 6 && piece.getBoardArea().equals("yard"))
                        moveToGameTrack(piece, dieResult);
                    break;
                case "gameTrack":
                    int passVal = willPassHomeColumnEntranceWith(piece, dieResult);
                    if (passVal != -1) {
                        moveToHomeColumn(piece, passVal);
                    } else {
                        moveOnGameTrack(piece, dieResult);
                        handleLandingOnAnotherPiece(piece);
                    }
                    break;
                case "homeColumn":
                    moveOnHomeColumn(piece, dieResult);
                    break;
            }
        } catch (Error error) {
            System.out.println("Tried to move" + toString() + "with dieResult " + dieResult);
        }
    }

    public void moveOnGameTrack(Piece piece, int dieResult) throws IllegalArgumentException {
        if(!piece.getBoardArea().equals("gameTrack"))
            throw new IllegalArgumentException("Tried to move piece on game track when it was not on game track");
        setPosIndex(piece, (piece.getPosIndex()+ dieResult > 51) ? dieResult - (52 - piece.getPosIndex()) : piece.getPosIndex() + dieResult);
    }

    public void moveToHomeColumn(Piece piece, int dieResult) throws IllegalArgumentException {
        if(!piece.getBoardArea().equals("gameTrack"))
            throw new IllegalArgumentException("Tried to move piece from gametrack to home column when it was not on game track");
        setPosIndex(piece, dieResult + piece.getColorIndex()*6);
        setBoardArea(piece, "homeColumn");
    }

    public void moveOnHomeColumn(Piece piece, int dieResult){
        if(((piece.getPosIndex()) % 6 + dieResult) > 5) {
            moveToGoal(piece);
        } else {
            setPosIndex(piece, piece.getPosIndex() + dieResult);
        }
    }

    public void moveToGameTrack(Piece piece, int dieResult) throws IllegalArgumentException{
        if(!piece.getBoardArea().equals("yard") || dieResult != 6)
            throw new IllegalArgumentException("Tried to move piece out of yard with another die result than 6 or piece not on gameTrack");
        setBoardArea(piece, "gameTrack");
        setPosIndex(piece, 13*piece.getColorIndex());
    }

    public void moveToYard(Piece piece) throws IllegalStateException{
        if(piece.getBoardArea().equals("yard")) {
            throw new IllegalStateException("Piece already in yard!");
        }
        setBoardArea(piece, "yard");
        setPosIndex(piece, piece.getInitialPosIndex());
    }

    public void moveToGoal(Piece piece) {
        setBoardArea(piece, "goal");
        setPosIndex(piece, piece.getInitialPosIndex());
    }

    public int willPassHomeColumnEntranceWith(Piece piece, int dieResult) {
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
            if (piece.getPosIndex() == playerPiece.getPosIndex() && piece != playerPiece && playerPiece.getBoardArea().equals("gameTrack")) {
                moveToYard(playerPiece);
            }
        }));
    }

    public void setPosIndex(Piece piece, int posIndex) throws IllegalArgumentException {
        if(posIndex < 0) throw new IllegalArgumentException("Cannot set index to negative number");
        piece.setPosIndex(posIndex);
    }

    public void setBoardArea(Piece piece, String boardArea) throws IllegalArgumentException{
        if(!boardPositions.getAllowedBoardPositionAreas().contains(boardArea)) {
            throw new IllegalArgumentException("Not an allowed area");
        }
        piece.setBoardArea(boardArea);
    }
}
