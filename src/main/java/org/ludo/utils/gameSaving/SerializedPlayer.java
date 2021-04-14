package org.ludo.utils.gameSaving;

import java.util.ArrayList;
import java.util.Arrays;

public class SerializedPlayer {
	private SerializedPiece[] pieces;
	private String name;
	private int colorIndex;

	public ArrayList<SerializedPiece> getPieces() {
		return new ArrayList<>(Arrays.asList(pieces));
	}

	public String getName() {
		return name;
	}

	public int getColorIndex() {
		return colorIndex;
	}

	@Override
	public String toString() {
		return "SerializedPlayer{" +
				"pieces=" + Arrays.toString(pieces) +
				", name='" + name + '\'' +
				", colorIndex=" + colorIndex +
				'}';
	}
}
