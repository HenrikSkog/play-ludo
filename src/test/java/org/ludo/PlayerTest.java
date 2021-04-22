package org.ludo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.ludo.gamelogic.Areas;
import org.ludo.gamelogic.Piece;
import org.ludo.gamelogic.Player;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PlayerTest {
    private ArrayList<Player> players = new ArrayList<>();
    private ArrayList<Piece> pieces = new ArrayList<>();

    @BeforeEach
    public void init() {
        for (int i = 0; i < 4; i++) {
            Player player = new Player("player", i);
            player.initializePieces();
            players.add(player);
        }
        //list with 4 pieces with different color
        for (int i = 0; i < 4; i++) {
            Piece piece = new Piece(i, 0);
            pieces.add(piece);
        }
    }


        @Test
        @DisplayName("Testing creating a player")
        public void testPlayer () {
            Player player = new Player("Henrik", 2);
            assertEquals("Henrik", player.getName());
            assertEquals(player.getColorIndex(), 2);
        }

        @Test
        @DisplayName("Testing if name is length = 0")
        public void testSetName () {
            assertThrows(IllegalArgumentException.class, () -> {
                Player player = new Player("", 2);
            });
        }

        @Test
        @DisplayName("Test initialize pieces")
        public void testInitPieces (){
        for(Player player : players){
            assertEquals(4, player.getPieces().size());
            for(Piece piece : player.getPieces()){
                assertEquals(player.getColorIndex(), piece.getColorIndex());
                assertEquals(Areas.YARD, piece.getBoardArea());
            }
        }
    }

    @Test
    @DisplayName("Test get pieces in Yard")
    public void testGetPiecesInYard(){
        Player player1 = players.get(0);
        assertEquals(4, player1.getPiecesInYard().size());
    }

    @Test
    @DisplayName("Test setting pieces that dont match with player color")
    public void testSetPieces(){
        assertThrows(IllegalArgumentException.class, () -> {
            Player player = players.get(0);
            player.setPieces(pieces);
        });
    }

    @Test
    @DisplayName("Test for all pieces in yard")
    public void testPiecesinYard(){
        Player player1 = players.get(0);
        assertEquals(true, player1.hasAllPiecesInYardOrGoal());
    }
}
