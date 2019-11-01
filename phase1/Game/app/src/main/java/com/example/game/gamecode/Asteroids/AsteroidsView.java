package com.example.game.gamecode.Asteroids;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.game.gamecode.GameThread;
import com.example.game.gamecode.GameView;

class AsteroidsView extends GameView {
  /** Screen width. */
  private int playAreaWidth;
  /** Screen height. */
  private int playAreaHeight;

  public AsteroidsView(Context context, int playAreaWidth, int playAreaHeight) {
    super(context);
    this.playAreaWidth = playAreaWidth;
    this.playAreaHeight = playAreaHeight;
    thread = new GameThread(getHolder(), this);
    gameBackend = new AsteroidGameManager(playAreaWidth, playAreaHeight);
  }

  public void draw(Canvas canvas) {
    super.draw(canvas);
    Paint paint = new Paint();
    paint.setColor(Color.WHITE);
    paint.setTextSize(50);
    paint.setTextAlign(Paint.Align.RIGHT);
    canvas.drawText(
        "Lives: " + ((AsteroidGameManager) gameBackend).getLives(),
        playAreaWidth - 50,
        paint.getTextSize(),
        paint);
    canvas.drawText(
        "Score: " + gameBackend.getCurrentScore(),
        playAreaWidth - 50,
        2 * paint.getTextSize(),
        paint);
  }
}
