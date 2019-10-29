package com.example.game;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.game.gamecode.Asteroids.AsteroidsActivity;
import com.example.game.gamecode.GameView;
import com.example.game.gamecode.Snake.SnakeActivity;

public class MainMenuScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu_screen);

        final Button launchGame1 = findViewById(R.id.game1);
        final Button launchGame2 = findViewById(R.id.game2);
        final Button launchGame3 = findViewById(R.id.game3);

        launchGame1.setOnClickListener(new GameView.OnClickListener() {
            public void onClick(View view) {
                Intent mainIntent = new Intent(MainMenuScreen.this, SnakeActivity.class);
                MainMenuScreen.this.startActivity(mainIntent);
            }
        });

        launchGame2.setOnClickListener(new GameView.OnClickListener() {
            public void onClick(View view) {
                Intent mainIntent = new Intent(MainMenuScreen.this, AsteroidsActivity.class);
                MainMenuScreen.this.startActivity(mainIntent);
            }
        });
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }
}
