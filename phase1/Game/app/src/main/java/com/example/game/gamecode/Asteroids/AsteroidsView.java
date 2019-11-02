package com.example.game.gamecode.Asteroids;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.game.gamecode.GameThread;
import com.example.game.gamecode.GameView;
import com.example.game.leaderboardcode.Saver;

class AsteroidsView extends GameView {
  /** Screen width. */
  private int playAreaWidth;
  /** Screen height. */
  private int playAreaHeight;
  /** density of screen */
  private float density;

  public AsteroidsView(Context context, int playAreaWidth, int playAreaHeight) {
    super(context);
    this.density = context.getResources().getDisplayMetrics().density;
    this.playAreaWidth = playAreaWidth;
    this.playAreaHeight = playAreaHeight;
    thread = new GameThread(getHolder(), this, (Saver) context);
    gameBackend = new AsteroidGameManager(playAreaWidth, playAreaHeight);
  }

  @Override
  public void draw(Canvas canvas) {
    super.draw(canvas);
    Paint paint = new Paint();
    paint.setColor(Color.WHITE);
    paint.setTextSize(24 * density);
    paint.setTextAlign(Paint.Align.RIGHT);
    canvas.drawText(
        "Lives: " + ((AsteroidGameManager) gameBackend).getLives(),
        playAreaWidth - 24 * density,
        paint.getTextSize(),
        paint);
    canvas.drawText(
        "Score: " + gameBackend.getCurrentScore(),
        playAreaWidth - 24 * density,
        2 * paint.getTextSize(),
        paint);
  }
}
