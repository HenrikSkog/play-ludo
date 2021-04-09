package org.ludo.utils.gameSaving;

public class SerializedPiece {
	private int posIndex;
	private int colorIndex;
	private String boardArea;
	private int initialPosIndex;

	public int getPosIndex() {
		return posIndex;
	}

	public int getColorIndex() {
		return colorIndex;
	}

	public String getBoardArea() {
		return boardArea;
	}

	public int getInitialPosIndex() {
		return initialPosIndex;
	}
}
