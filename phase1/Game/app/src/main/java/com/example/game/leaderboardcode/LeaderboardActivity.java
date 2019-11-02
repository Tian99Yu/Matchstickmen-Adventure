package com.example.game.leaderboardcode;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.game.Games;
import com.example.game.MainMenuScreen;
import com.example.game.R;
import com.example.game.settingscode.Customizable;
import com.example.game.settingscode.SettingsManager;

import java.io.IOException;

public class LeaderboardActivity extends AppCompatActivity implements LeaderboardInterface, Customizable {
    private Spinner gameSpinner;
    private TableLayout scoreTable;
    private LeaderboardPresenter leaderboardPresenter;
    private LeaderboardManager leaderboardManager;
    private SettingsManager settingsManager;
    private String username;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leaderboard_layout);

        gameSpinner = findViewById(R.id.gameList);
        scoreTable = findViewById(R.id.scoreTable);

        username = (String) getIntent().getSerializableExtra("username");
        settingsManager = (SettingsManager) getIntent().getSerializableExtra("settingsManager");
        leaderboardManager = (LeaderboardManager) getIntent().getSerializableExtra("leaderboardManager");
        leaderboardPresenter = new LeaderboardPresenter(this, leaderboardManager);

        setTheme(settingsManager.getSetting("theme"));

        addCloseButton();
        addSpinnerListener();
    }

    private void addCloseButton() {
        ImageButton closeLeaderboard = findViewById(R.id.closeLeaderboard);
        closeLeaderboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LeaderboardActivity.this, MainMenuScreen.class);
                startActivity(intent);
            }
        });
    }

    private void addSpinnerListener() {
        gameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                leaderboardPresenter.showScores();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void setGames(String[] games) {
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, games);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gameSpinner.setAdapter(spinnerArrayAdapter);
    }

    public void showScores(String game) throws IOException {
        TableLayout.LayoutParams params = new TableLayout.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT, 1);
        String[] scoreData = leaderboardPresenter.getScoreData(getCurrentGame());
        for (String data : scoreData) {
          TableRow row = new TableRow(this);
          row.setLayoutParams(params);
          row.setWeightSum((float) 0.5);
          TextView temp = new TextView(this);
          temp.setText(data);
          row.addView(temp);
          scoreTable.addView(row);
        }
    }

    public String[] getGames() {
        return new String[0];
    }

    public Games getCurrentGame() {
        return Games.valueOf(gameSpinner.getSelectedItem().toString());
    }

    @Override
    public void setTheme(String theme) {
        View leaderboardContainer = findViewById(R.id.leaderboardLayout);
        TextView leaderboardTitle = findViewById(R.id.leaderboardTitle);
        Spinner gameSpinner = findViewById(R.id.gameList);

        int backgroundColor;
        int textColor;
        if (theme.equals("dark")) {
            backgroundColor = Color.parseColor("#001C27");
            textColor = Color.parseColor("#FFFFFF");
        } else {
            backgroundColor = Color.parseColor("#FFFFFF");
            textColor = Color.parseColor("#001C27");
        }

        leaderboardContainer.setBackgroundColor(backgroundColor);
        leaderboardTitle.setTextColor(textColor);

    }
}
