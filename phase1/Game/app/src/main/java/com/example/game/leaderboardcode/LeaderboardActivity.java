package com.example.game.leaderboardcode;

import android.content.Intent;
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

import java.io.IOException;

public class LeaderboardActivity extends AppCompatActivity implements LeaderboardInterface{
    private Spinner gameSpinner;
    private TableLayout scoreTable;
    private LeaderboardPresenter leaderboardPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leaderboard_layout);

        gameSpinner = findViewById(R.id.gameList);
        scoreTable = findViewById(R.id.scoreTable);

        leaderboardPresenter = new LeaderboardPresenter(this, new LeaderboardManager(getDataDir()));

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
            String[] splitData = data.split(" ");
            TableRow row = new TableRow(this);
            row.setLayoutParams(params);
            row.setWeightSum((float) 0.33);
            for (int i = 0; i < 3; i++) {
                TextView temp = new TextView(this);
                temp.setText(splitData[i]);
                row.addView(temp);
            }
            scoreTable.addView(row);
        }
    }

    public String[] getGames() {
        return new String[0];
    }

    public Games getCurrentGame() {
        return Games.valueOf(gameSpinner.getSelectedItem().toString());
    }
}
