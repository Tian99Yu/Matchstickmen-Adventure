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

public abstract class GameView extends SurfaceView
    implements SurfaceHolder.Callback, Drawer<Canvas, Bitmap> {
  protected GameThread thread;
  public GameBackend gameBackend;
  private ImageButton pauseButton;
  private GamePresenter<Canvas, Bitmap> presenter;
  private float density;

  public GameView(Context context) {
    super(context);
    getHolder().addCallback(this);
    setFocusable(true);
    density = context.getResources().getDisplayMetrics().density;
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

  @Override
  public HashMap<Class<? extends GameObject>, Bitmap> getClassToSprite() {
    return null;
  }

  @Override
  public HashMap<Class<? extends GameObject>, Integer> getClassToColor() {
    return null;
  }

  @Override
  public float getDensity() {
    return density;
  }

  @Override
  public void drawSprite(
      Canvas drawingSurface,
      Bitmap sprite,
      int color,
      double x,
      double y,
      double angle,
      double width,
      double height) {
    Paint paint = new Paint();
    ColorFilter filter = new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN);
    paint.setColorFilter(filter);
    Matrix matrix = new Matrix();
    matrix.preRotate((float) Math.toDegrees(angle), sprite.getWidth() / 2, sprite.getHeight() / 2);
    matrix.postScale(
        (float) (width / (float) sprite.getWidth()),
        (float) (height / (float) sprite.getHeight()),
        sprite.getWidth() / 2,
        sprite.getHeight() / 2);
    matrix.postTranslate(
        (float) (x - sprite.getWidth() / 2.0), (float) (y - sprite.getHeight() / 2.0));
    drawingSurface.drawBitmap(sprite, matrix, paint);
  }

  @Override
  public void drawSolidBackground(Canvas drawingSurface, int color) {
    Paint backgroundPaint = new Paint();
    backgroundPaint.setStyle(Paint.Style.FILL);
    backgroundPaint.setColor(color);
    drawingSurface.drawRect(drawingSurface.getClipBounds(), backgroundPaint);
  }

  @Override
  public void drawText(Canvas drawingSurface, String text, int color, float x, float y, float fontSize, int rowOffset) {
    Paint paint = new Paint();
    paint.setColor(Color.WHITE);
    paint.setTextSize(fontSize);
    paint.setTextAlign(Paint.Align.RIGHT);
    drawingSurface.drawText(text, x, y + rowOffset*paint.getTextSize(), paint);
  }

  public void setPresenter(GamePresenter<Canvas, Bitmap> presenter) {
    this.presenter = presenter;
  }
}
