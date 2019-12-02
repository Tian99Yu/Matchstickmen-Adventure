package com.group0540.matchstickmenadventures.settingscode;
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
import java.nio.charset.StandardCharsets;

/**
 * A SettingsManager, which handles the saving and applying of settings of Customizable and
 * CustomizableGame objects.
 */
public class SettingsManager implements Serializable {
  private final File settingsFile;
  private String settingsString;

  /**
   * Creates a SettingsManager with corresponding location on disk.
   * @param saveDirectory directory to save files in
   * @param defaultSettings a default settings file
   */
  public SettingsManager(File saveDirectory, InputStream defaultSettings) {
    settingsFile = new File(saveDirectory, "settings.json");

    loadSettings(defaultSettings);
  }

  /**
   * Records an option for some setting.
   * @param setting the setting to change
   * @param option the new value
   */
  void changeSetting(String setting, String option) {
    JsonObject settings = convertStringToJsonObject(settingsString);
    settings.add(setting, new JsonPrimitive(option));
    settingsString = settings.toString();
  }

  /**
   * Gets the recorded value for some setting.
   * @param setting the setting of interest
   * @return a String representing the value
   */
  public String getSetting(String setting) {
    JsonObject settings = convertStringToJsonObject(settingsString);
    return settings.get(setting).toString().replaceAll("\"", "");
  }

  /**
   * Loads in saved settings if the file exists; otherwise, creates a new file with default settings
   * @param defaultSettings InputStream containing default settings
   */
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

  /**
   * Saves the String representation of the JsonObject containing all settings to disk.
   */
  void writeSettingsToFile() {
    JsonObject settings = convertStringToJsonObject(settingsString);
    try {
      BufferedWriter bw = new BufferedWriter(new FileWriter(settingsFile));
      bw.write(settings.toString());
      bw.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * @param is the InputStream to be converted.
   * @return a string representation of the InputStream
   */
  private String convertStreamtoString(InputStream is) {
    BufferedReader streamReader = new BufferedReader(
            new InputStreamReader(is, StandardCharsets.UTF_8)
    );
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

  /**
   * Converts the string representation of settings to a JsonObject. Used to make SettingsManager
   * serializable.
   * @param jsonString the string representation of settings
   * @return the corresponding JsonObject
   */
  private JsonObject convertStringToJsonObject(String jsonString) {
    return JsonParser.parseString(jsonString).getAsJsonObject();
  }
}
