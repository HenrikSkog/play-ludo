package org.ludo.utils.gameSaving;

import org.ludo.gameLogic.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LudoSaveHandler implements LudoFileHandler {
    private String getDirPath() {
        return String.valueOf(Paths.get("src", "main", "resources", "org", "ludo", "assets", "saveFiles"));
    }

    private String getFilePath(String filename) {
        return getDirPath() + "/" + filename;
    }

    private int numSavedGames() {
        return Objects.requireNonNull(new File(getDirPath()).list()).length;
    }

    @Override
    public ArrayList<String> getSavedGames() {
        return new ArrayList<>(Arrays.asList(Objects.requireNonNull(new File(getDirPath()).list())));
    }

    @Override
    public void saveGame(Game game) throws IOException {
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(getFilePath("gamesave#" + (numSavedGames() + 1) + ".txt")), "utf-8"))) {
            //write color order
            for (String color : game.getColorOrder()) {
                try {
                    writer.write(color + ";");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //write player turn
            try {
                writer.write("\n");
                writer.write(Integer.toString(game.getCurrentPlayerTurn()));
                writer.write("\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
            //write player data
            game.getPlayers().forEach(player -> {
                try {
                    writer.write(player.getName() + ";" + player.getColorIndex() + "-");
                    //write each players pieces
                    player.getPieces().forEach(piece -> {
                        try {
                            writer.write(piece.getInitialPosIndex() + ";" + piece.getBoardArea() + ";" + piece.getPosIndex() + ":");
                        } catch (IOException e2) {
                            e2.printStackTrace();
                        }
                    });
                    writer.write("\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    private List<String> getLinesFromFile(String filename) {
        List<String> lines = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(String.valueOf(Paths.get("src", "main", "resources", "org", "ludo", "assets", "saveFiles", filename))))) {
            while (scanner.hasNext()) {
                lines.add(scanner.next());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return lines;
    }

    @Override
    public Game buildGameFromFile(String filename) {
        List<String> lines = getLinesFromFile(filename);
        String[] colorOrder = lines.get(0).split(";");
        int playerTurn = Integer.parseInt(lines.get(1));

        ArrayList<Player> players = new ArrayList<>();
        lines.subList(2, lines.size() - 1).forEach(line -> {
            String[] player_pieces = line.split("-");
            String[] playerData = player_pieces[0].split(";");
            String[] piecesData = player_pieces[1].split(":");
            Player player = new Player(playerData[0], Integer.parseInt(playerData[1]));
            List<Piece> pieces = Arrays.asList(piecesData).stream().map(pieceData -> {
                String[] pieceDataList = pieceData.split(";");
                return new Piece(
                        Integer.parseInt(playerData[1]),
                        Integer.parseInt(pieceDataList[0]),
                        Integer.parseInt(pieceDataList[2]),
                        Areas.valueOf(pieceDataList[1]));
            }).collect(Collectors.toList());
            player.setPieces(new ArrayList<>(pieces));
            players.add(player);
        });
        var game = new Game();
        game.initState(players, playerTurn, colorOrder);
        return game;
    }

    public static void main(String[] args) {
        var tst = new LudoSaveHandler();
        Game game = tst.buildGameFromFile("gamesave#1.txt");

        System.out.println(game.toString());
    }
}
