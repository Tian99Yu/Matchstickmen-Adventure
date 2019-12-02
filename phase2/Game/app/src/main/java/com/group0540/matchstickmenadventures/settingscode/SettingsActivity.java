package com.group0540.matchstickmenadventures.settingscode;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.group0540.matchstickmenadventures.MainMenuScreen;
import com.example.matchstickmenadventures.R;

import java.io.Serializable;

public class SettingsActivity extends AppCompatActivity implements Serializable, Customizable {
    private SettingsManager settingsManager;

    private RadioGroup colorGroup;
    private RadioGroup characterGroup;
    private RadioGroup difficultyGroup;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        settingsManager = (SettingsManager) getIntent().getSerializableExtra("settingsManager");
        setContentView(R.layout.settings_layout);

        final RadioButton radioButtonDark = findViewById(R.id.darkTheme);
        radioButtonDark.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    setTheme("dark");
                    setColorText(findViewById(R.id.colorSetting), Color.WHITE);
                    TextView title = findViewById(R.id.textView2);
                    title.setTextColor(Color.WHITE);
                } else {
                    setTheme("light");
                    setColorText(findViewById(R.id.colorSetting), Color.BLACK);
                    TextView title = findViewById(R.id.textView2);
                    title.setTextColor(Color.BLACK);
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

    private void setColorText(Object layout, int color) {
        if (layout instanceof RadioButton) {
            ((RadioButton) layout).setTextColor(color);
        } else if (layout instanceof TextView) {
            ((TextView) layout).setTextColor(color);
        } else {
            if (layout instanceof LinearLayout) {
                for (int i = 0; i < ((LinearLayout) layout).getChildCount(); i++) {
                    setColorText(((LinearLayout) layout).getChildAt(i), color);
                }
            } else {
                for (int i = 0; i < ((RadioGroup) layout).getChildCount(); i++) {
                    setColorText(((RadioGroup) layout).getChildAt(i), color);
                }
            }
        }
    }

    @Override
    public void onBackPressed(){
        applySettings();
        Intent intent = new Intent(SettingsActivity.this, MainMenuScreen.class);
        startActivity(intent);
    }
}
