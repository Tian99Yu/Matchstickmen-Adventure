package com.example.game.settingscode;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.game.MainMenuScreen;
import com.example.game.R;
import com.example.game.leaderboardcode.LeaderboardManager;

import java.io.Serializable;

public class SettingsActivity extends AppCompatActivity implements Serializable, Customizable {
    private LeaderboardManager leaderboardManager;
    private SettingsManager settingsManager;
    private String username;

    private RadioGroup colorGroup;
    private RadioGroup characterGroup;
    private RadioGroup difficultyGroup;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        username = (String) getIntent().getSerializableExtra("username");
        settingsManager = (SettingsManager) getIntent().getSerializableExtra("settingsManager");
        leaderboardManager = (LeaderboardManager) getIntent().getSerializableExtra("leaderboardManager");
        setContentView(R.layout.settings_layout);

        final RadioButton radioButtonDark = findViewById(R.id.darkTheme);
        RadioButton radioButtonLight = findViewById(R.id.lightTheme);
        radioButtonDark.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    setTheme("dark");
                }else{
                    setTheme("light");
                }
            }
        });

        addCloseButton();
        markSettings();

    }

    private void addCloseButton() {
        ImageButton closeSettings = findViewById(R.id.closeSettings);
        closeSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                applySettings();
                Intent intent = new Intent(SettingsActivity.this, MainMenuScreen.class);
                startActivity(intent);
            }
        });
    }

    private void applySettings() {
        String checkedColor = ((TextView) findViewById(colorGroup.getCheckedRadioButtonId())).getText().toString().toLowerCase();
        String checkedDifficulty = ((TextView) findViewById(difficultyGroup.getCheckedRadioButtonId())).getText().toString().toLowerCase();
        String checkedCharacter = ((TextView) findViewById(characterGroup.getCheckedRadioButtonId())).getText().toString();

        settingsManager.changeSetting("theme", checkedColor);
        settingsManager.changeSetting("difficulty", checkedDifficulty);
        if (checkedCharacter.equals("Character 1")) {
            settingsManager.changeSetting("character", "one");
        } else {
            settingsManager.changeSetting("character", "two");
        }
        settingsManager.writeSettingsToFile();
    }

    private void markSettings() {
        colorGroup = findViewById(R.id.colorSchemeGroup);
        characterGroup = findViewById(R.id.characterGroup);
        difficultyGroup = findViewById(R.id.difficultyGroup);

        String color = settingsManager.getSetting("theme");
        if (color.equals("dark")) {
            colorGroup.check(R.id.darkTheme);
        } else {
            colorGroup.check(R.id.lightTheme);
        }

        String character = settingsManager.getSetting("character");
        if (character.equals("one")) {
            characterGroup.check(R.id.firstCharacter);
        } else {
            characterGroup.check(R.id.secondCharacter);
        }

        String difficulty = settingsManager.getSetting("difficulty");
        if (difficulty.equals("easy")) {
            difficultyGroup.check(R.id.easy);
        } else if (difficulty.equals("medium")) {
            difficultyGroup.check(R.id.medium);
        } else {
            difficultyGroup.check(R.id.hard);
        }
    }

    @Override
    public void setTheme(String theme) {
        View settingsContainer = findViewById(R.id.settingsLayout);
        int color;
        if (theme.equals("dark")) {
            color = Color.parseColor("#001C27");
        } else {
            color = Color.parseColor("#FF006F9C");
        }
        settingsContainer.setBackgroundColor(color);
    }
}
