package com.example.game.gamecode;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

/*
Handles the update events for all games.
 */
public class GameThread extends Thread {
  private SurfaceHolder surfaceHolder; // Container containing the Canvas
  private Canvas canvas; // Canvas
  private GameView gameView;
  private volatile boolean isPaused;
  private volatile boolean isTerminated;
  protected int updateInterval;

  public GameThread(SurfaceHolder surfaceHolder, GameView gameView) {
    this.surfaceHolder = surfaceHolder;
    this.gameView = gameView;
  }

  public void setUpdateInterval(int updateInterval) {
    this.updateInterval = updateInterval;
  }

  public int getUpdateInterval() {
    return this.updateInterval;
  }

  public void run() {
    while (!gameView.gameBackend.isGameOver() && !isTerminated) {
      if (!isPaused) {
        this.gameView.update();
        try {
          Thread.sleep(updateInterval);
        } catch (Exception e) {
          e.printStackTrace();
        }
      } else {
        try {
          Thread.sleep(50);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
      try {
        canvas = this.surfaceHolder.lockCanvas();
        synchronized (surfaceHolder) {
          if (canvas != null) {
            this.gameView.draw(canvas);
          }
        }
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        if (canvas != null) {
          try {
            surfaceHolder.unlockCanvasAndPost(canvas);
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
      }
    }
  }

  public void setPaused(boolean isPaused) {
    this.isPaused = isPaused;
  }

  public boolean isPaused() {
    return this.isPaused;
  }

  public void terminateThread() {
    isTerminated = true;
  }

}
