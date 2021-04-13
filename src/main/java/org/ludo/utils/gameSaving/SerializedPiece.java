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

	@Override
	public String toString() {
		return "SerializedPiece{" +
				"posIndex=" + posIndex +
				", colorIndex=" + colorIndex +
				", boardArea='" + boardArea + '\'' +
				", initialPosIndex=" + initialPosIndex +
				'}';
	}
}
