package com.example.game.gamecode;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.game.MainMenuScreen;
import com.example.game.R;

public abstract class GameActivity extends AppCompatActivity {
    protected GameView gameView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void addRunningButton() {
        final ImageButton toggleRunningButton = findViewById(R.id.toggleRunningButton);
        toggleRunningButton.setOnClickListener(new GameView.OnClickListener() {
            public void onClick(View view) {
                gameView.toggleRunning(toggleRunningButton);
            }
        });
        toggleRunningButton.bringToFront();
    }

    // TODO: Allow for quit-resume functionality.
    //  When switching to the previous intent, store whatever is in this intent and store it in
    //  cache.
    //  Additionally, look into app-switcher events so that the game does not break and restart
    //  when opening the app-switcher menu and returning to the app.

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(GameActivity.this, MainMenuScreen.class);
        gameView.toggleRunning();
        startActivity(intent);
    }

    protected abstract GameView setView();

}
