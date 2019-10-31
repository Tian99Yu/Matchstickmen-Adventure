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
        // TODO: Change run behaviour.
        //  The current implementation uses isRunning, but Thread.run should only be called when it is
        //  instantiated.
        //  https://docs.oracle.com/javase/7/docs/api/java/lang/Thread.html#start()
        //  As a result, trying to set isRunning to false via the pause button and then resuming by
        //  clicking the pause button does nothing as the code has broken out of the while loop and we
        //  should not try to call run again.
        //  Perhaps we should remove isRunning and replace it with another condition within the
        //  synchronized block.
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
