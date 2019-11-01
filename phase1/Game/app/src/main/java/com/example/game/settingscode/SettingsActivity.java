package com.example.game.settingscode;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.game.MainMenuScreen;
import com.example.game.R;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_layout);

        addCloseButton();

    }

    private void addCloseButton() {
        ImageButton closeLeaderboard = findViewById(R.id.closeSettings);
        closeLeaderboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, MainMenuScreen.class);
                startActivity(intent);
            }
        });
    }
}
