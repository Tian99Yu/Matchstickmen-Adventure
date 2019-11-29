package com.example.game.gamecode;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.ImageButton;

import java.util.HashMap;

public abstract class GameView<K> extends SurfaceView implements SurfaceHolder.Callback {
  protected GameThread thread;
  public GameBackend gameBackend;
  private ImageButton pauseButton;
  private GamePresenter<Canvas, K> presenter;

  public GameView(Context context) {
    super(context);
    getHolder().addCallback(this);
    setFocusable(true);
  }

  @Override
  public void surfaceCreated(SurfaceHolder surfaceHolder) {
    if (thread.getState() == Thread.State.NEW) {
      unpause();
      thread.start();
    }
  }

  @Override
  public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
    if (pauseButton == null) {
      unpause();
    }
  }

  @Override
  public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
    boolean retry = true;
    while (retry) {
      pause();
      retry = false;
    }
  }

  public void update() {
    // Should we use the presenter to interact with backend rather than let the view do it?
    gameBackend.update();
  }

  public void draw(Canvas canvas) {
    super.draw(canvas);
    if (presenter != null) {
      presenter.draw(canvas);
    }
  }

  public void setPauseButton(ImageButton pauseButton) {
    this.pauseButton = pauseButton;
  }

  public void togglePause() {
    if (pauseButton != null) {
      pauseButton.setBackgroundResource(
          thread.isPaused() ? android.R.drawable.ic_media_pause : android.R.drawable.ic_media_play);
    }
    thread.setPaused(!thread.isPaused());
  }

  public void pause() {
    if (pauseButton != null) {
      pauseButton.setBackgroundResource(android.R.drawable.ic_media_play);
    }
    thread.setPaused(true);
  }

  public void unpause() {
    if (pauseButton != null) {
      pauseButton.setBackgroundResource(android.R.drawable.ic_media_pause);
    }
    thread.setPaused(false);
  }

  public boolean isPaused() {
    return thread.isPaused();
  }

  public void setPresenter(GamePresenter<Canvas, K> presenter) {
    this.presenter = presenter;
  }

  public GamePresenter<Canvas, K> getPresenter() {
    return presenter;
  }
}
