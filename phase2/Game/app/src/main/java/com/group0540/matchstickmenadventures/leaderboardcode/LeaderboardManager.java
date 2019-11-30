package com.group0540.matchstickmenadventures.leaderboardcode;

import com.group0540.matchstickmenadventures.Games;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * A LeaderboardManager, which handles the parsing, writing to, and saving from of save files.
 */
public class LeaderboardManager implements Serializable {
  // String representation of the leaderboard data.
  private String leaderboardString;
  private final File saveFile;

  /**
   * Creates a LeaderboardManager with a corresponding location on disk.
   * @param saveDirectory the directory to store data in
   */
  public LeaderboardManager(File saveDirectory) {
    saveFile = new File(saveDirectory, "save_data.json");
    loadData();
  }

  /**
   * Loads the JSON representation of the data on disk and converts it to its String representation.
   * Creates the file if it does not already exist.
   */
  private void loadData() {
    JsonObject data = new JsonObject();
    if (saveFile.exists()) {
      try {
        data = JsonParser.parseReader(new FileReader(saveFile)).getAsJsonObject();
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      }
    }
    leaderboardString = data.toString();
    writeDataToFile();
  }

  /**
   * Writes data saveFile, the location on disk.
   */
  private void writeDataToFile() {
    JsonObject data = convertStringToJsonObject(leaderboardString);
    try {
      BufferedWriter bw = new BufferedWriter(new FileWriter(saveFile));
      bw.write(data.toString());
      bw.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Converts from serializable type to JsonObject for easier manipulation and accessing of data.
   * @param jsonString String representation of JsonObject
   * @return JsonObject
   */
  private JsonObject convertStringToJsonObject(String jsonString) {
    return JsonParser.parseString(jsonString).getAsJsonObject();
  }

  /**
   * Saves game data. The number of statistics and values to save should be equal.
   * @param game the game for which this statistic is being recorded
   * @param username the player's username
   * @param statistics statistics to save
   * @param values the corresponding scores to save
   */
  public void saveData(Games game, String username, String[] statistics, String[] values) {
    JsonObject data = convertStringToJsonObject(leaderboardString);
    JsonArray statData;
    if (data.get(game.toString()) != null) {
      statData = (JsonArray) data.get(game.toString());
    } else {
      statData = new JsonArray();
      data.add(game.toString(), statData);
    }
    JsonObject save = new JsonObject();
    save.addProperty("username", username);
    for (int i = 0; i < Math.min(statistics.length, values.length); i++) {
      save.addProperty(statistics[i], values[i]);
    }
    statData.add(save);
    leaderboardString = data.toString();

    writeDataToFile();
  }

  /**
   * Returns a list of games for which statistics are being tracked.
   * @return array representation of the first level of leaderboardString, comprising game names.
   */
  String[] getGames() {
    JsonObject data = convertStringToJsonObject(leaderboardString);
    return data.keySet().toArray(new String[0]);
  }

  String[][] getGameStatistics(final Games game) {
    JsonObject data = convertStringToJsonObject(leaderboardString);
    JsonArray jsonArray = (JsonArray) data.get(game.toString());
    List<JsonObject> gameData = new ArrayList<>();
    for (int i = 0; i < jsonArray.size(); i++ ) {
      gameData.add((JsonObject) jsonArray.get(i));
    }
    String[][] ret = new String[Math.min(10, jsonArray.size())][];
    Collections.sort(gameData, new Comparator<JsonObject>() {
      @Override
      public int compare(JsonObject first, JsonObject second) {
        if (game == Games.ASTEROIDS) {
          return -first.get("Score").getAsInt() + second.get("Score").getAsInt();
        } else if (game == Games.MATCHSTICKMEN) {
          return -first.get("Count").getAsInt() + second.get("Count").getAsInt();
        } else {
          return -first.get("Length").getAsInt() + second.get("Length").getAsInt();
        }
      }
    });
    for (int i = 0; i < Math.min(gameData.size(), 10); i++) {
      String[] stat = new String[2];
      stat[0] = gameData.get(i).get("username").toString().replaceAll("\"", "");
      String value;
      if (game == Games.ASTEROIDS) {
        value = "Score";
      } else if (game == Games.MATCHSTICKMEN) {
        value = "Count";
      } else {
        value = "Length";
      }
      stat[1] = gameData.get(i).get(value).toString().replaceAll("\"", "");
      ret[i] = stat;
    }

    return ret;
  }
}
