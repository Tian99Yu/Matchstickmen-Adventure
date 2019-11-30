package com.group0540.matchstickmenadventures.leaderboardcode;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.group0540.matchstickmenadventures.Games;
import com.group0540.matchstickmenadventures.MainMenuScreen;
import com.example.matchstickmenadventures.R;
import com.group0540.matchstickmenadventures.settingscode.Customizable;
import com.group0540.matchstickmenadventures.settingscode.SettingsManager;

public class LeaderboardActivity extends AppCompatActivity implements LeaderboardView, Customizable {
    private Spinner gameSpinner;
    private TableLayout scoreTable;
    private LeaderboardPresenter leaderboardPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leaderboard_layout);

        gameSpinner = findViewById(R.id.gameList);
        scoreTable = findViewById(R.id.scoreTable);

        SettingsManager settingsManager = (SettingsManager) getIntent().getSerializableExtra("settingsManager");
        LeaderboardManager leaderboardManager = (LeaderboardManager) getIntent().getSerializableExtra("leaderboardManager");
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
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, games);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gameSpinner.setAdapter(spinnerArrayAdapter);
    }

    public void showScores() {
        TableRow.LayoutParams textParams = new TableRow.LayoutParams(
                0,
                TableRow.LayoutParams.WRAP_CONTENT,
                0.5f);
        TableLayout.LayoutParams rowParams = new TableLayout.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT,
                1f);

        String[][] scoreData = leaderboardPresenter.getScoreData(getCurrentGame());
        scoreTable.removeAllViews();
        for (String[] data : scoreData) {
          TableRow row = new TableRow(this);
          row.setLayoutParams(rowParams);
          TextView nameView = new TextView(this);
          TextView scoreView = new TextView(this);
          nameView.setText(data[0]);
          nameView.setTextSize(24f);
          nameView.setLayoutParams(textParams);
          scoreView.setText(data[1]);
          scoreView.setTextSize(24f);
          scoreView.setLayoutParams(textParams);
          scoreView.setGravity(Gravity.END);
          row.addView(nameView);
          row.addView(scoreView);
          scoreTable.addView(row);
        }
    }

    public Games getCurrentGame() {
        return Games.valueOf(gameSpinner.getSelectedItem().toString());
    }

    @Override
    public void setTheme(String theme) {
        View leaderboardContainer = findViewById(R.id.leaderboardLayout);
        TextView leaderboardTitle = findViewById(R.id.leaderboardTitle);

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
