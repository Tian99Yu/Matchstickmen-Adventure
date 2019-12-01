package com.group0540.matchstickmenadventures;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.matchstickmenadventures.R;
import com.group0540.matchstickmenadventures.gamecode.asteroids.AsteroidsActivity;
import com.group0540.matchstickmenadventures.gamecode.GameView;
import com.group0540.matchstickmenadventures.gamecode.matchstickmen.MatchstickMenActivitySinglePlayer;
import com.group0540.matchstickmenadventures.gamecode.snake.SnakeActivity;
import com.group0540.matchstickmenadventures.leaderboardcode.LeaderboardActivity;
import com.group0540.matchstickmenadventures.leaderboardcode.LeaderboardManager;
import com.group0540.matchstickmenadventures.logincode.LoginActivity;
import com.group0540.matchstickmenadventures.logincode.LoginManager;
import com.group0540.matchstickmenadventures.settingscode.Customizable;
import com.group0540.matchstickmenadventures.settingscode.SettingsActivity;
import com.group0540.matchstickmenadventures.settingscode.SettingsManager;
import java.io.InputStream;

public class MainMenuScreen extends AppCompatActivity implements Customizable {
  private LeaderboardManager leaderboardManager;
  private SettingsManager settingsManager;
  private LoginManager loginManager;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main_menu_screen);

    final Button launchGame1 = findViewById(R.id.game1);
    final Button launchGame2 = findViewById(R.id.game2);
    final Button launchGame3 = findViewById(R.id.game3);
    final ImageButton openLeaderboard = findViewById(R.id.leaderboardButton);
    final ImageButton openSettings = findViewById(R.id.settingsButton);
    final ImageButton loginButton = findViewById(R.id.loginButton);

    InputStream defaultSettingsStream = getResources().openRawResource(R.raw.defaultsettings);

    Intent intent = getIntent();
    if (intent.getExtras() == null) {
      leaderboardManager = new LeaderboardManager(getDataDir());
      settingsManager = new SettingsManager(getDataDir(), defaultSettingsStream);
      loginManager = new LoginManager(getDataDir());
    } else {
      settingsManager = (SettingsManager) intent.getSerializableExtra("settingsManager");
      leaderboardManager = (LeaderboardManager) intent.getSerializableExtra("leaderboardManager");
      loginManager = (LoginManager) intent.getSerializableExtra("loginManager");
    }

    if (!loginManager.isLoggedIn()) {
      launchGame1.setVisibility(View.INVISIBLE);
      launchGame2.setVisibility(View.INVISIBLE);
      launchGame3.setVisibility(View.INVISIBLE);
      findViewById(R.id.loginPrompt).setVisibility(View.VISIBLE);
    }

    setTheme(settingsManager.getSetting("theme"));

    launchGame1.setOnClickListener(
        new GameView.OnClickListener() {
          public void onClick(View view) {
            Intent mainIntent = new Intent(MainMenuScreen.this, SnakeActivity.class);
            sendToIntent(mainIntent);
            MainMenuScreen.this.startActivity(mainIntent);
          }
        });

    launchGame2.setOnClickListener(
        new GameView.OnClickListener() {
          public void onClick(View view) {
            Intent mainIntent = new Intent(MainMenuScreen.this, AsteroidsActivity.class);
            sendToIntent(mainIntent);
            MainMenuScreen.this.startActivity(mainIntent);
          }
        });

    launchGame3.setOnClickListener(
        new GameView.OnClickListener() {
          public void onClick(View view) {
            Intent mainIntent = new Intent(MainMenuScreen.this, MatchstickMenActivitySinglePlayer.class);
            sendToIntent(mainIntent);
            MainMenuScreen.this.startActivity(mainIntent);
          }
        });

    openLeaderboard.setOnClickListener(
        new View.OnClickListener() {
          public void onClick(View view) {
            Intent mainIntent = new Intent(MainMenuScreen.this, LeaderboardActivity.class);
            sendToIntent(mainIntent);
            MainMenuScreen.this.startActivity(mainIntent);
          }
        });

    openSettings.setOnClickListener(
        new View.OnClickListener() {
          public void onClick(View view) {
            Intent mainIntent = new Intent(MainMenuScreen.this, SettingsActivity.class);
            sendToIntent(mainIntent);
            MainMenuScreen.this.startActivity(mainIntent);
          }
        });

    loginButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent mainIntent = new Intent(MainMenuScreen.this, LoginActivity.class);
        sendToIntent(mainIntent);
        MainMenuScreen.this.startActivity(mainIntent);
      }
    });
  }

  @Override
  protected void onPostCreate(Bundle savedInstanceState) {
    super.onPostCreate(savedInstanceState);
  }

  private void sendToIntent(Intent intent) {
    intent.putExtra("leaderboardManager", leaderboardManager);
    intent.putExtra("settingsManager", settingsManager);
    intent.putExtra("loginManager", loginManager);
  }

  @Override
  public void setTheme(String theme) {
    int backgroundColor;
    int textColor;
    int buttonTint = Color.parseColor("#FF218F");

    View mainMenuContainer = findViewById(R.id.mainMenuContainer);
    TextView loginInstructions = findViewById(R.id.loginPrompt);
    Button game1 = findViewById(R.id.game1);
    Button game2 = findViewById(R.id.game2);
    Button game3 = findViewById(R.id.game3);
    game3.setBackgroundTintList(ColorStateList.valueOf(Color.RED));

    if (theme.equals("dark")) {
      backgroundColor = Color.parseColor("#001C27");
      textColor = Color.WHITE;
    } else {
      backgroundColor = Color.parseColor("#FF006F9C");
      textColor = Color.BLACK;
    }
    mainMenuContainer.setBackgroundColor(backgroundColor);
    loginInstructions.setTextColor(textColor);
    game1.setBackgroundTintList(ColorStateList.valueOf(buttonTint));
    game2.setBackgroundTintList(ColorStateList.valueOf(buttonTint));
    game3.setBackgroundTintList(ColorStateList.valueOf(buttonTint));
  }
}
