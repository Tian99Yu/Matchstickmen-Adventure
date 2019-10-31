package com.example.game.gamecode.MatchstickMen;

import android.content.Context;

import com.example.game.gamecode.GameThread;
import com.example.game.gamecode.GameView;

public class MatchstickMenView extends GameView {
    public MatchstickMenView(Context context) {
        super(context);
        thread = new GameThread(getHolder(), this);
        game = new MatchstickMenBackend(); //change it after you know the size of the canvas
        ((MatchstickMenBackend) game).createObjects();
        thread.setUpdateInterval(10000000);
    }
    @Override
    public void update() {
        super.update();
        if (((MatchstickMenBackend) game).isOver()) {
            thread.setRunning(false);
        }
    }
}
