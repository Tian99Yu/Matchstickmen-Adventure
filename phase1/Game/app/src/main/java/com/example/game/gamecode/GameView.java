package com.example.game.gamecode;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.ImageButton;

public abstract class GameView extends SurfaceView implements SurfaceHolder.Callback {
    protected GameThread thread;
    public GameBackend gameBackend;

    public GameView(Context context) {
        super(context);
        getHolder().addCallback(this);
        setFocusable(true);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        thread.setUnpaused(true);
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        if (!thread.isUnpaused()) {
            thread.setUnpaused(true);
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        boolean retry = true;
        while (retry) {
            try {
                thread.setUnpaused(true);
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            retry = false;
        }
    }

    public void update() {
        gameBackend.update();
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (canvas != null) {
            gameBackend.draw(canvas);
        }
    }

    public void toggleRunning() {
        thread.setUnpaused(!thread.isUnpaused());
    }

    public void toggleRunning(ImageButton toggleRunningButton) {
        toggleRunningButton.setBackgroundResource(thread.isUnpaused() ?
                android.R.drawable.ic_media_play : android.R.drawable.ic_media_pause);
        thread.setUnpaused(!thread.isUnpaused());
    }
}
