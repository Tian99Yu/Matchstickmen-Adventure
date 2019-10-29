package com.example.game.gamecode.Snake;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

import com.example.game.gamecode.GameThread;
import com.example.game.gamecode.GameView;

public class SnakeThread extends GameThread {

    /** Whether the thread is running. */
    private boolean isRunning;
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;
    private SnakeView game;
    protected int updateInterval;


    protected SnakeThread(SurfaceHolder surfaceHolder, GameView game) {
        super(surfaceHolder, game);
    }

    public void run() {
        while (isRunning) {
            canvas = null;
            try {
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    this.game.update();
                    this.game.draw(canvas);
                }
            } catch(Exception e) {
                e.printStackTrace();
            } finally{
                if (canvas != null) {
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            try {
                this.sleep(updateInterval);
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void setRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }

    public boolean isRunning () {
        return this.isRunning;
    }


}
