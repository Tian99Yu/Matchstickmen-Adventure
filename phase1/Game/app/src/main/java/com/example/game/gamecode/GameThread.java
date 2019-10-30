package com.example.game.gamecode;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;

/*
Handles the update events for all games.
 */
public class GameThread extends Thread {
    private SurfaceHolder surfaceHolder;    // Container containing the Canvas
    private Canvas canvas;            // Canvas
    private GameView game;
    private boolean isRunning;
    protected int updateInterval;

    public GameThread(SurfaceHolder surfaceHolder, GameView game) {
        this.surfaceHolder = surfaceHolder;
        this.game = game;
    }

    public void setUpdateInterval(int updateInterval) {
        this.updateInterval = updateInterval;
    }

    public int getUpdateInterval(){
        return this.updateInterval;
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
