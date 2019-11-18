package com.example.game.settingscode;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;

public class SettingsManager implements Serializable {
  private File settingsFile;
  private String settingsString;

  public SettingsManager(File saveDirectory, InputStream defaultSettings) {
    settingsFile = new File(saveDirectory, "settings.json");

    loadSettings(defaultSettings);
  }

  private void loadSettings(InputStream defaultSettings) {
    JsonObject settings = new JsonObject();
    if (settingsFile.exists()) {
      try {
        settings = JsonParser.parseReader(new FileReader(settingsFile)).getAsJsonObject();
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      }
    } else {
      settings = JsonParser.parseString(convertStreamtoString(defaultSettings)).getAsJsonObject();
    }
    settingsString = settings.toString();
    writeSettingsToFile();
  }

  public void writeSettingsToFile() {
    JsonObject settings = convertStringToJsonObject(settingsString);
    try {
      BufferedWriter bw = new BufferedWriter(new FileWriter(settingsFile));
      bw.write(settings.toString());
      bw.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private String convertStreamtoString(InputStream is) {
    BufferedReader streamReader = null;
    try {
      streamReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    StringBuilder stringBuilder = new StringBuilder();

    String str;
    try {
      while ((str = streamReader.readLine()) != null) {
        stringBuilder.append(str);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return stringBuilder.toString();
  }

  private JsonObject convertStringToJsonObject(String jsonString) {
    return JsonParser.parseString(jsonString).getAsJsonObject();
  }

  public void changeSetting(String setting, String option) {
    JsonObject settings = convertStringToJsonObject(settingsString);
    settings.add(setting, new JsonPrimitive(option));
    settingsString = settings.toString();
  }

  public String getSetting(String setting) {
    JsonObject settings = convertStringToJsonObject(settingsString);
    return settings.get(setting).toString().replaceAll("\"", "");
  }
}
