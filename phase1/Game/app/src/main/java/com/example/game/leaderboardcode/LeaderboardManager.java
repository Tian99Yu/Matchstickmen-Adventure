package com.example.game.leaderboardcode;

import com.example.game.Games;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Objects;

public class LeaderboardManager implements Serializable {
    private File saveDirectory;
    private List<String> games = new ArrayList<>();
    private HashMap<Games, String> gameFilename = new HashMap<>();

    public LeaderboardManager(File saveDirectory) {
        this.saveDirectory = saveDirectory;

        for (Games game : Games.values()) {
            String gameName = getGameName(game);
            games.add(gameName);
            gameFilename.put(game, gameName);

            if (!fileExists(game)) {
                File gameFile = new File(saveDirectory, gameName);
                try {
                    gameFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void saveData(Games game, String username, String statistic, String value) throws IOException {
        File gameFile = getGameFile(game);
        String[] data = {username, statistic, value};
        BufferedWriter bw = new BufferedWriter(new FileWriter(gameFile.getAbsolutePath(), true));
        bw.write(String.join(" ", data));
        bw.newLine();
        bw.close();
    }

    String[] getGames() {
        return games.toArray(new String[0]);
    }

    String[] getGameStatistics(Games game) throws IOException {
        File gameFile = getGameFile(game);
        BufferedReader br = new BufferedReader(new FileReader(gameFile.getAbsolutePath()));
        List<String> data = new ArrayList<>();

        String currLine = br.readLine();
        while (currLine != null) {
            String[] sArray = currLine.split(" ");
            data.add(String.join(" ", sArray));

            currLine = br.readLine();
        }

        br.close();
        Collections.sort(data, scoreSorter);
        return data.toArray(new String[0]);
    }

    private String getGameName(Games game) {
        return game.toString();
    }

    private File getGameFile(Games game) {
        return new File(saveDirectory, getGameName(game));
    }

    private boolean fileExists(Games game) {
        String gameName = gameFilename.get(game);
        if (gameName == null) {
            return false;
        }
        File temp = new File(saveDirectory, gameName);
        return temp.exists();
    }

    /**
     * Reads in data for each gameBackend and each statistic and purges all but the top ten statistic data.
     * Writes the updated data back to the file.
     */
    private void retainTopTen() {
        for (Games game : Games.values()) {
            File gameFile = getGameFile(game);
            HashMap<String, List<String>> gameStats = new HashMap<>();

            try {
                BufferedReader br = new BufferedReader(new FileReader(gameFile.getAbsolutePath()));
                String currLine = br.readLine();
                while (currLine != null) {
                    String[] splitLine = currLine.split(" ");
                    if (!gameStats.containsKey(splitLine[0])) {
                        List<String> temp = new ArrayList<>();
                        temp.add(currLine);
                        gameStats.put(splitLine[0], temp);
                    } else {
                        Objects.requireNonNull(gameStats.get(splitLine[0])).add(currLine);
                    }
                    currLine = br.readLine();
                }
                br.close();

                for (String statistic : gameStats.keySet()) {
                    List<String> statisticData = gameStats.get(statistic);
                    if (statisticData != null) {
                        Collections.sort(statisticData, scoreSorter);
                        gameStats.put(statistic, statisticData.subList(0,
                                Math.min(statisticData.size(), 10)));
                    }
                }

                BufferedWriter bw = new BufferedWriter(
                        new FileWriter(gameFile.getAbsolutePath(), false));
                for (String statistic : gameStats.keySet()) {
                    for (String data : Objects.requireNonNull(gameStats.get(statistic))) {
                        bw.write(data);
                        bw.newLine();
                    }
                }
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    transient private Comparator<String> scoreSorter = new Comparator<String>() {
        @Override
        public int compare(String o1, String o2) {
            int first = Integer.valueOf(o1.substring(o1.lastIndexOf(" ") + 1));
            int second = Integer.valueOf(o2.substring(o2.lastIndexOf(" ") + 1));
            return Integer.compare(first, second);
        }
    };
}
