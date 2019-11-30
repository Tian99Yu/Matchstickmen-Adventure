package com.group0540.matchstickmenadventures.logincode;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;

public class LoginManager implements Serializable {
  private final File saveFile;
  private String loginString;
  private String username;
  private boolean isLoggedIn = false;

  interface OnLoginFinishedListener {
    void onUsernameError();
    void onPasswordError();
    void onSuccess();
    void onFail();
    void onAccountCreated();
  }

  public LoginManager(File saveDirectory) {
    saveFile = new File(saveDirectory, "login_data.json");
    loadData();
  }

  /**
   * Loads in existing data from saveFile.
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
    if (data.has("_lastused")) {
      isLoggedIn = true;
      username = data.get("_lastused").getAsString();
    }
    loginString = data.toString();
    writeDataToFile();
  }

  /**
   * Writes the Json representation of LoginString to file.
   */
  private void writeDataToFile() {
    JsonObject data = convertStringToJsonObject(loginString);
    try {
      BufferedWriter bw = new BufferedWriter(new FileWriter(saveFile));
      bw.write(data.toString());
      bw.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Converts a Json String to JsonObject.
   * @param jsonString String in Json format
   * @return JsonObject
   */
  private JsonObject convertStringToJsonObject(String jsonString) {
    return JsonParser.parseString(jsonString).getAsJsonObject();
  }

  /**
   * Attempts a login.
   *
   * @param username new or existing name
   * @param password corresponding to username
   * @param listener presents response to user depending on result
   */
  void login(String username, String password, LoginManager.OnLoginFinishedListener listener) {
    if (isCredentialInvalid(username)) {
      listener.onUsernameError();
      return;
    } else if (isCredentialInvalid(password)) {
      listener.onPasswordError();
      return;
    }
    JsonObject data = convertStringToJsonObject(loginString);
    if (data.has(username)) { // Existing user check
      if (data.get(username).getAsString().equals(password)) {  // Password correct
        isLoggedIn = true;
        this.username = username;
        addLastUser(data, username);

        listener.onSuccess();
      } else { // Password incorrect
        isLoggedIn = false;
        listener.onFail();
      }
    } else { // New user
      isLoggedIn = true;
      this.username = username;
      data.addProperty(username, password);
      addLastUser(data, username);

      listener.onAccountCreated();
    }
  }

  private void addLastUser(JsonObject data, String username) {
    data.addProperty("_lastused", username);
    loginString = data.toString();
    writeDataToFile();
  }

  public boolean isLoggedIn() {
    return isLoggedIn;
  }

  /**
   * Enforces user credential requirements.
   *
   * @param credential the String to be checked
   * @return false iff credential is matched by the regex
   */
  private boolean isCredentialInvalid(String credential) {
    // Matches alphanumeric strings of length 5-12, inclusive
    return !credential.matches("^[a-zA-Z0-9]{5,12}$");
  }

  /**
   * Gets the username of the currently logged in user.
   *
   * @return username
   */
  public String getUsername() {
    return username;
  }
}
