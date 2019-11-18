package com.example.game.gamecode;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceHolder;

import com.example.game.leaderboardcode.Saver;

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
  private Saver saver;

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
