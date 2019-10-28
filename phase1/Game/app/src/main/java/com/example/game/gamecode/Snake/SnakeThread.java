package com.example.game.gamecode.Snake;

import android.view.SurfaceHolder;

import com.example.game.gamecode.GameThread;
import com.example.game.gamecode.GameView;

public class SnakeThread extends GameThread {

    /** Whether the thread is running. */
    private boolean isRunning;


    protected SnakeThread(SurfaceHolder surfaceHolder, GameView game) {
        super(surfaceHolder, game);
    }


}
