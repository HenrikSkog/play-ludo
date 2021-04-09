package org.ludo.utils.gameSaving;

import java.util.ArrayList;
import java.util.Arrays;

public class SerializedPlayer {
	private SerializedPiece[] pieces;
	private String name;
	private int colorIndex;

	public ArrayList<SerializedPiece> getPieces() {
		return (ArrayList<SerializedPiece>) Arrays.asList(pieces);
	}

	public String getName() {
		return name;
	}

	public int getColorIndex() {
		return colorIndex;
	}
}
