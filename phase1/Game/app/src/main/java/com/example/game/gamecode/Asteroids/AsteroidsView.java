package com.example.game.gamecode.Asteroids;

import android.content.Context;

import com.example.game.gamecode.GameThread;
import com.example.game.gamecode.GameView;

class AsteroidsView extends GameView {

    public AsteroidsView(Context context) {
        super(context);
        thread = new GameThread(getHolder(), this);
        game = new AsteroidGameManager();
    }
}
