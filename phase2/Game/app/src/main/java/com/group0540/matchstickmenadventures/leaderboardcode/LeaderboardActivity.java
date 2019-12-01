package com.group0540.matchstickmenadventures.leaderboardcode;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.matchstickmenadventures.R;
import com.google.gson.JsonObject;
import com.group0540.matchstickmenadventures.Games;
import com.group0540.matchstickmenadventures.MainMenuScreen;
import com.group0540.matchstickmenadventures.settingscode.Customizable;
import com.group0540.matchstickmenadventures.settingscode.SettingsManager;

import java.util.List;
import java.util.Set;

public class LeaderboardActivity extends AppCompatActivity implements LeaderboardView, Customizable {
    private Spinner gameSpinner;
    private TableLayout scoreTable;
    private LeaderboardPresenter leaderboardPresenter;
    private LeaderboardManager leaderboardManager;
    private int entryColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leaderboard_layout);

        gameSpinner = findViewById(R.id.gameList);
        scoreTable = findViewById(R.id.scoreTable);

        SettingsManager settingsManager = (SettingsManager) getIntent().getSerializableExtra("settingsManager");
        leaderboardManager = (LeaderboardManager) getIntent().getSerializableExtra("leaderboardManager");
        leaderboardPresenter = new LeaderboardPresenter(this);

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

    public void setGames() {
        String[] games = leaderboardManager.getGames();
        if (games.length == 0) {
            gameSpinner.setVisibility(View.INVISIBLE);
            findViewById(R.id.statisticSortInstruction).setVisibility(View.INVISIBLE);
            findViewById(R.id.noGameHint).setVisibility(View.VISIBLE);

        } else {
            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(
                    this,
                    android.R.layout.simple_spinner_item,
                    leaderboardManager.getGames());
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            gameSpinner.setAdapter(spinnerArrayAdapter);
        }
    }

    public void showScores() {
        scoreTable.removeAllViews();
        List<JsonObject> scoreData = leaderboardManager.getGameStatistics(getCurrentGame());
        buildTable(scoreData);
    }

    public void showScores(String statistic) {
        scoreTable.removeAllViews();
        List<JsonObject> scoreData =
                leaderboardManager.getGameStatistics(getCurrentGame(), statistic);
        buildTable(scoreData);
    }

    private void buildTable(List<JsonObject> scoreData) {
        int stat_count = scoreData.get(0).size() - 1;

        // Row parameters for distribution of TextViews across rows
        TableLayout.LayoutParams rowParams = new TableLayout.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT,
                1f
        );

        // Text parameters for distribution of statistics across rows
        TableRow.LayoutParams textParams = new TableRow.LayoutParams(
                0,
                TableRow.LayoutParams.WRAP_CONTENT,
                (float) 0.7 / stat_count
        );

        // Text parameters for spacing of username across TextViews
        TableRow.LayoutParams nameParams = new TableRow.LayoutParams(
                0,
                TableRow.LayoutParams.WRAP_CONTENT,
                0.3f
        );

        Set<String> stats = null;
        if (scoreData.size() != 0) {
            stats = scoreData.get(0).deepCopy().keySet();
            stats.remove("username");
        }
        TableRow row = new TableRow(this);
        row.setLayoutParams(rowParams);

        TextView name = new TextView(this);
        name.setLayoutParams(nameParams);
        name.setText(getString(R.string.username_stat));
        name.setTextColor(entryColor);
        name.setTypeface(null, Typeface.BOLD);
        row.addView(name);

        for (final String stat : stats) {
            TextView val = new TextView(this);

            val.setText(stat);
            val.setTypeface(null, Typeface.BOLD);
            val.setTextColor(entryColor);
            val.setLayoutParams(textParams);
            val.setClickable(true);

            val.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showScores(stat);
                }
            });
            row.addView(val);
        }
        scoreTable.addView(row);

        for (JsonObject data : scoreData) {
            row = new TableRow(this);
            row.setLayoutParams(rowParams);

            name = new TextView(this);
            name.setText(data.get("username").getAsString());
            name.setTextColor(entryColor);
            name.setLayoutParams(nameParams);
            row.addView(name);

            for (String stat : stats) {
                TextView val = new TextView(this);

                val.setText(data.get(stat).getAsString());
                val.setLayoutParams(textParams);
                val.setTextColor(entryColor);

                row.addView(val);
            }
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
        TextView statisticSortHint = findViewById(R.id.statisticSortInstruction);
        TextView noGameHint = findViewById(R.id.noGameHint);

        int backgroundColor;
        int textColor;
        if (theme.equals("dark")) {
            backgroundColor = Color.parseColor("#001C27");
            textColor = Color.WHITE;
            entryColor = Color.parseColor("#c8c8c8");
        } else {
            backgroundColor = Color.parseColor("#FF006F9C");
            textColor = Color.BLACK;
            entryColor = Color.BLACK;
        }

        leaderboardContainer.setBackgroundColor(backgroundColor);
        leaderboardTitle.setTextColor(textColor);
        statisticSortHint.setBackgroundColor(backgroundColor);
        statisticSortHint.setTextColor(textColor);
        noGameHint.setTextColor(textColor);
    }
}
