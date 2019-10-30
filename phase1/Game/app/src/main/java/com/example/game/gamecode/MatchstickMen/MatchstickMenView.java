package com.example.game.gamecode.MatchstickMen;

import android.content.Context;

import com.example.game.gamecode.GameThread;
import com.example.game.gamecode.GameView;

public class MatchstickMenView extends GameView {
    public MatchstickMenView(Context context) {
        super(context);
        thread = new GameThread(getHolder(), this);
        game = new MatchstickMenBackend(250, 250); //change it after you know the size of the canvas
    }
}
