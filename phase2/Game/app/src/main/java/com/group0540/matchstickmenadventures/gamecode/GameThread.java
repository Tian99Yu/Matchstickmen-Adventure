package com.group0540.matchstickmenadventures.gamecode;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

import com.group0540.matchstickmenadventures.leaderboardcode.Saver;

/**
 * Handles updates for all games.
 */
public class GameThread extends Thread {
  private final SurfaceHolder surfaceHolder; // Container containing the Canvas
  private Canvas canvas; // Canvas
  private final GameView gameView;
  private volatile boolean isPaused;
  private volatile boolean isTerminated;
  private int updateInterval;
  private final Saver saver;

  public GameThread(SurfaceHolder surfaceHolder, GameView gameView, Saver saver) {
    this.surfaceHolder = surfaceHolder;
    this.gameView = gameView;
    this.saver = saver;
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
      try {
        if (!isPaused) {
          Thread.sleep(updateInterval);
        } else {
          Thread.sleep(50);
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    if (saver != null) {
      saver.saveScore();
    }
  }

  void setPaused(boolean isPaused) {
    this.isPaused = isPaused;
  }

  boolean isPaused() {
    return this.isPaused;
  }

  void terminateThread() {
    isTerminated = true;
  }
}
