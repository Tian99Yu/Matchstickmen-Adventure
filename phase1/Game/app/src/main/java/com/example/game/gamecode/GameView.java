package com.example.game.gamecode;

import android.content.Context;
import android.graphics.Canvas;
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
    if (!thread.isAlive()) {
      thread.start();
    }
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
      thread.setUnpaused(false);
      retry = false;
    }
  }

  public void update() {
    gameBackend.update();
  }

  public void draw(Canvas canvas) {
    super.draw(canvas);
    gameBackend.draw(canvas);
  }

  public void togglePause() {
    thread.setUnpaused(!thread.isUnpaused());
  }

  public void togglePause(ImageButton togglePauseButton) {
    togglePauseButton.setBackgroundResource(
        thread.isUnpaused() ? android.R.drawable.ic_media_play : android.R.drawable.ic_media_pause);
    togglePause();
  }
}
