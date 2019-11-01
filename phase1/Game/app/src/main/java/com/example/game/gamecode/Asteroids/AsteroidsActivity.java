package com.example.game.gamecode.Asteroids;

import android.annotation.SuppressLint;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.example.game.R;
import com.example.game.gamecode.CustomControls.JoystickView;
import com.example.game.gamecode.GameActivity;
import com.example.game.gamecode.GameView;

public class AsteroidsActivity extends GameActivity {

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.asteroids_layout);

        Asteroid.appearance = BitmapFactory.decodeResource(getResources(), R.drawable.asteroid);

        Ship.appearance = BitmapFactory.decodeResource(getResources(), R.drawable.ship);

        Projectile.appearance = BitmapFactory.decodeResource(getResources(), R.drawable.laser);

        gameView = this.setView();

        FrameLayout frameLayout = findViewById(R.id.frameLayout);
        frameLayout.addView(gameView);

        super.addRunningButton();

        ImageButton fireButton = findViewById(R.id.fireButton);
        ImageButton thrusterButton = findViewById(R.id.thrusterButton);
        final JoystickView joystick = new JoystickView(this);

        ((FrameLayout) findViewById(R.id.joystick)).addView(joystick);

        fireButton.setOnTouchListener(new GameView.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    ((AsteroidGameManager) gameView.gameBackend).setFireActive(true);
                }
                else if (event.getAction() == MotionEvent.ACTION_UP) {
                    ((AsteroidGameManager) gameView.gameBackend).setFireActive(false);
                    return true;
                }
                return false;
            }
        });

        thrusterButton.setOnTouchListener(new GameView.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    ((AsteroidGameManager) gameView.gameBackend).setThrusterActive(true);
                }
                else if (event.getAction() == MotionEvent.ACTION_UP) {
                    ((AsteroidGameManager) gameView.gameBackend).setThrusterActive(false);
                    return true;
                }
                return false;
            }
        });

        joystick.setOnTouchListener(new GameView.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                joystick.onTouchEvent(event);
                ((AsteroidGameManager) gameView.gameBackend).setTargetDirection(joystick.getAngle());
                return true;
            }
        });
    }

    @Override
    protected GameView setView() {
        return new AsteroidsView(this);
    }
}
