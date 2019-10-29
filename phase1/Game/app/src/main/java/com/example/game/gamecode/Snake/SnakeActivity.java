package com.example.game.gamecode.Snake;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.game.R;
import com.example.game.gamecode.GameActivity;
import com.example.game.gamecode.GameView;

public class SnakeActivity extends GameActivity {


    Button LeftButton;
    Button RightButton;
    SnakeHead snakehead;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.snake_layout);


        LeftButton = findViewById(R.id.LeftButton);
        RightButton = findViewById(R.id.RightButton);


        //add the gameview as a second layout:
        gameView = this.setView();
        FrameLayout frameLayout =  findViewById(
                R.id.snake_game

        );
        frameLayout.addView(
                gameView
        );

        View.OnClickListener LeftListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snakehead.turn(TurnDirection.LEFT);
            }
        };

        LeftButton.setOnClickListener(LeftListener);


        View.OnClickListener RightListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snakehead.turn(TurnDirection.RIGHT);
            }
        };

        RightButton.setOnClickListener(RightListener);
    }

    protected GameView setView() {
        return null;
    }

}
