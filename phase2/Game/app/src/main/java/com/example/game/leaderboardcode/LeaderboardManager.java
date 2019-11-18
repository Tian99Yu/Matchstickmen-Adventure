package com.example.game.leaderboardcode;

import com.example.game.Games;
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
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class LeaderboardManager implements Serializable {
  private File saveFile;
  private String leaderboardString;

  public LeaderboardManager(File saveDirectory) {
    saveFile = new File(saveDirectory, "save_data.json");
    loadData();
  }

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

  private JsonObject convertStringToJsonObject(String jsonString) {
    return JsonParser.parseString(jsonString).getAsJsonObject();
  }

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

  public String[] getGames() {
    JsonObject data = convertStringToJsonObject(leaderboardString);
    return data.keySet().toArray(new String[data.keySet().size()]);
  }

  public String[] getGameStatistics(final Games game) {
    String[] ret = new String[10];
    JsonObject data = convertStringToJsonObject(leaderboardString);
    JsonArray jsonArray = (JsonArray) data.get(game.toString());
    List<JsonObject> gameData = new ArrayList<>();
    for (int i = 0; i < jsonArray.size(); i++ ) {
      gameData.add((JsonObject) jsonArray.get(i));
    }
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
    for (int i = 0; i < Math.min(gameData.size(), 10); i++)
      if (game == Games.ASTEROIDS) {
        ret[i] = gameData.get(i).get("username").toString() + gameData.get(i).get("Score").toString();
      } else if (game == Games.MATCHSTICKMEN) {
        ret[i] = gameData.get(i).get("username").toString() + gameData.get(i).get("Count").toString();
      } else {
        ret[i] = gameData.get(i).get("username").toString() + gameData.get(i).get("Length").toString();
      }
    return ret;
  }
}
