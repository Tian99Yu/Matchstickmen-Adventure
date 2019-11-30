package com.example.game.logincode;

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
  private File saveFile;
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

  private JsonObject convertStringToJsonObject(String jsonString) {
    return JsonParser.parseString(jsonString).getAsJsonObject();
  }

  void login(String username, String password, LoginManager.OnLoginFinishedListener listener) {
    if (!isCredentialValid(username)) {
      listener.onUsernameError();
      return;
    } else if (!isCredentialValid(password)) {
      listener.onPasswordError();
      return;
    }
    JsonObject data = convertStringToJsonObject(loginString);
    if (data.has(username)) {
      if (data.get(username).getAsString().equals(password)) {
        this.username = username;
        isLoggedIn = true;
        data.addProperty("_lastused", username);
        writeDataToFile();
        listener.onSuccess();
      } else {
        isLoggedIn = false;
        listener.onFail();
      }
    } else {
      isLoggedIn = true;
      this.username = username;
      data.addProperty(username, password);
      data.addProperty("_lastused", username);
      loginString = data.toString();
      writeDataToFile();
      listener.onAccountCreated();
    }
  }

  public boolean isLoggedIn() {
    return isLoggedIn;
  }

  private boolean isCredentialValid(String credential) {
    // Matches alphanumeric strings of length 5-12, inclusive
    return credential.matches("^[a-zA-Z0-9]{5,12}$");
  }

  public String getUsername() {
    return username;
  }
}
