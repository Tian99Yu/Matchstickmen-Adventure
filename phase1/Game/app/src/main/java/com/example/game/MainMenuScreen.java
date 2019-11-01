package com.example.game;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.game.gamecode.Asteroids.AsteroidsActivity;
import com.example.game.gamecode.GameView;
import com.example.game.gamecode.MatchstickMen.MatchstickMenActivity;
import com.example.game.gamecode.Snake.SnakeActivity;
import com.example.game.leaderboardcode.LeaderboardActivity;
import com.example.game.leaderboardcode.LeaderboardManager;
import com.example.game.settingscode.Customizable;
import com.example.game.settingscode.SettingsActivity;
import com.example.game.settingscode.SettingsManager;
import java.io.InputStream;

public class MainMenuScreen extends AppCompatActivity implements Customizable {
    private String username;
    private LeaderboardManager leaderboardManager;
    private SettingsManager settingsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu_screen);

        final Button launchGame1 = findViewById(R.id.game1);
        final Button launchGame2 = findViewById(R.id.game2);
        final Button launchGame3 = findViewById(R.id.game3);
        final ImageButton openLeaderboard = findViewById(R.id.leaderboardButton);
        final ImageButton openSettings = findViewById(R.id.settingsButton);
        final TextView usernameField = findViewById(R.id.usernameField);

        InputStream defaultSettingsStream = getResources().openRawResource(R.raw.defaultsettings);

        username = usernameField.getText().toString();
        leaderboardManager = new LeaderboardManager(getDataDir());
        settingsManager = new SettingsManager(getDataDir(), defaultSettingsStream);

        setTheme(settingsManager.getSetting("theme"));

        launchGame1.setOnClickListener(new GameView.OnClickListener() {
            public void onClick(View view) {
                Intent mainIntent = new Intent(MainMenuScreen.this, SnakeActivity.class);
                sendToIntent(mainIntent);
                MainMenuScreen.this.startActivity(mainIntent);
            }
        });

        launchGame2.setOnClickListener(new GameView.OnClickListener() {
            public void onClick(View view) {
                Intent mainIntent = new Intent(MainMenuScreen.this, AsteroidsActivity.class);
                sendToIntent(mainIntent);
                MainMenuScreen.this.startActivity(mainIntent);
            }
        });

        launchGame3.setOnClickListener(new GameView.OnClickListener(){
            public void onClick(View view){
                Intent mainIntent = new Intent(MainMenuScreen.this, MatchstickMenActivity.class);
                sendToIntent(mainIntent);
                MainMenuScreen.this.startActivity(mainIntent);
            }
        });

        openLeaderboard.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent mainIntent = new Intent(MainMenuScreen.this, LeaderboardActivity.class);
                sendToIntent(mainIntent);
                MainMenuScreen.this.startActivity(mainIntent);
            }
        });

        openSettings.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent mainIntent = new Intent(MainMenuScreen.this, SettingsActivity.class);
                sendToIntent(mainIntent);
                MainMenuScreen.this.startActivity(mainIntent);
            }
        });

        usernameField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() != 0) { username = editable.toString();
                }
            }
        });
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    private void sendToIntent(Intent intent) {
        intent.putExtra("username", username);
        intent.putExtra("leaderboardManager", leaderboardManager);
        intent.putExtra("settingsManager", settingsManager);
    }

    @Override
    public void setDifficulty(int difficulty) {
    }

    @Override
    public void setCharacter(String character) {
    }

    @Override
    public void setTheme(String theme) {
        View mainMenuContainer = findViewById(R.id.mainMenuContainer);
        int color;
        if (theme.equals("dark")) {
            color = Color.parseColor("#001C27");
        } else {
            color = Color.parseColor("#FF006F9C");
        }
        mainMenuContainer.setBackgroundColor(color);
    }
}
