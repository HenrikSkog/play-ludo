package org.ludo.utils.gameSaving;

import org.ludo.gameLogic.*;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class LudoSaveHandler implements LudoFileHandler {
    private String getDirPath() {
        Path path = Paths.get(System.getProperty("user.home") + "/tdt4100_project_sorknes_skog");
        File file = new File(String.valueOf(path));
        if(!file.exists()) {
            file.mkdir();
        }
        return String.valueOf(path);
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
        try (Scanner scanner = new Scanner(new File(getFilePath(filename)))) {
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
        int playerTurn = Integer.parseInt(lines.get(0));

        ArrayList<Player> players = new ArrayList<>();
        lines.subList(1, lines.size()).forEach(line -> {
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
        game.loadState(players, playerTurn);
        return game;
    }
}
