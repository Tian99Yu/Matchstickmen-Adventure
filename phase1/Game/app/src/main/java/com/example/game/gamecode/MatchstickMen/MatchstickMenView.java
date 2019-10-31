package com.example.game.gamecode.MatchstickMen;

import android.content.Context;

import com.example.game.gamecode.GameThread;
import com.example.game.gamecode.GameView;

public class MatchstickMenView extends GameView {
    private MatchstickMenActivity matchstickMenActivity;
    private int color;
    private String character;
    private int level;
    private int setUpInterval;


    public MatchstickMenView(Context context, MatchstickMenActivity matchstickMenActivity) {
        super(context);
        thread = new GameThread(getHolder(), this);
        game = new MatchstickMenBackend(); //change it after you know the size of the canvas


        this.matchstickMenActivity = matchstickMenActivity;
        this.color = matchstickMenActivity.getColor();
        this.level = matchstickMenActivity.getLevel();
        this.character = matchstickMenActivity.getCharacter();
        switch (level){
            case 0:
                setUpInterval = 10000000;
                break;
            case 1:
                setUpInterval = 7000000;
                break;
            case 2:
                setUpInterval = 5000000;
                break;
                default:
                    setUpInterval = 10000000;
        }
        thread.setUpdateInterval(setUpInterval);
        ((MatchstickMenBackend)this.game).inject(color, level, character);
        ((MatchstickMenBackend) game).createObjects();



    }
    @Override
    public void update() {
        super.update();
        if (((MatchstickMenBackend) game).isOver()) {
            thread.setRunning(false);
        }
    }
}
