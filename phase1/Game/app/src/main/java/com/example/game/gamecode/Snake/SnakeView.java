package com.example.game.gamecode.Snake;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.widget.ImageButton;

import androidx.annotation.MainThread;

import com.example.game.gamecode.GameBackend;
import com.example.game.gamecode.GameThread;
import com.example.game.gamecode.GameView;

public class SnakeView extends GameView {
    private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

    // the hight and width of the snakeObj
    public static float objWidth;
    public static float objHight;

    public SnakeThread thread;
    public SnakeBackend game;
    public SnakeView(Context context) {
        super(context);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        this.thread.setRunning(true);
        this.thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        boolean retry = true;
        while (retry) {
            try {
                this.thread.setRunning(true);
                this.thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            retry = false;
        }
    }

    public void update() {
        this.game.update();
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (canvas != null) {
            this.game.draw(canvas);
        }
    }

    public void toggleRunning(ImageButton toggleRunningButton) {
        toggleRunningButton.setBackgroundResource(this.thread.isRunning() ?
                android.R.drawable.ic_media_play : android.R.drawable.ic_media_pause);
        this.thread.setRunning(!this.thread.isRunning());
    }



}
