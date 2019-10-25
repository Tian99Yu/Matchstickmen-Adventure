package com.example.game.gamecode;

import android.content.Context;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.ImageButton;

public abstract class GameView extends SurfaceView implements SurfaceHolder.Callback {
    protected GameThread thread;
    protected GameBackend game;

    public GameView(Context context) {
        super(context);
        getHolder().addCallback(this);
        setFocusable(true);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        boolean retry = true;
        while (retry) {
            try {
                thread.setRunning(true);
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            retry = false;
        }
    }

    public void update() {
        game.update();
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (canvas != null) {
            game.draw(canvas);
        }
    }

    public void toggleRunning(ImageButton toggleRunningButton) {
        toggleRunningButton.setBackgroundResource(thread.isRunning() ?
                android.R.drawable.ic_media_play : android.R.drawable.ic_media_pause);
        thread.setRunning(!thread.isRunning());
    }
}
