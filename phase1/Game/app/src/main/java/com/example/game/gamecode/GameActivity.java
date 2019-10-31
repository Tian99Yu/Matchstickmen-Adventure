package com.example.game.gamecode;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.game.R;

public abstract class GameActivity extends AppCompatActivity {
    protected GameView gameView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // TODO: Look into overriding methods to fix start/stop/pause/resume behaviour

    protected void addRunningButton() {
        final ImageButton toggleRunningButton = findViewById(R.id.toggleRunningButton);
        toggleRunningButton.setOnClickListener(new GameView.OnClickListener() {
            public void onClick(View view) {
                gameView.toggleRunning(toggleRunningButton);
            }
        });
        toggleRunningButton.bringToFront();
    }

    protected abstract GameView setView();

}
