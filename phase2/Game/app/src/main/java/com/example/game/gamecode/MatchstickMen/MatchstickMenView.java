package com.example.game.gamecode.MatchstickMen;

import android.content.Context;

import com.example.game.gamecode.GameThread;
import com.example.game.gamecode.GameView;
import com.example.game.leaderboardcode.Saver;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.LightingColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;

public class MatchstickMenView extends GameView<MatchstickMenObject>
    implements MatchstickMenDrawer<Canvas, Bitmap> {
  private int setUpInterval;
  /** The customizations of this matchstick men game */
  MatchstickMenCustomization matchstickMenCustomization;

  public MatchstickMenView(Context context) {
    super(context);
    thread = new GameThread(getHolder(), this, (Saver) context);
    gameBackend = new MatchstickMenBackend();

    ImageGetter<Bitmap> imageGetter = getBitmapGetter(getContext());

    setPresenter(new MatchstickMenPresenter<>(this, this.gameBackend, imageGetter));
  }

  public void setDifficulty() {
    int setUpInterval = (10 - (matchstickMenCustomization.getDifficulty()*2));
    thread.setUpdateInterval(setUpInterval);
  }

  /**
   * Draw the background of this game on canvas.
   *
   * @param drawingSurface the canvas that the game in running on.
   */
  @Override
  public void drawBackground(Canvas drawingSurface) {
    Paint paint = new Paint();
    paint.setColor(matchstickMenCustomization.getTheme());
    paint.setStyle(Paint.Style.FILL);
    drawingSurface.drawPaint(paint);
  }

  /**
   * Draw the MatchstickMenObject.
   *
   * @param drawingSurface the surface to be drawn onto
   * @param image the type of matchstick men to be drawn
   */
  @Override
  public void drawImage(Canvas drawingSurface, Bitmap image, int x, int y) {

    Paint paint = new Paint();
    paint.setColorFilter(new LightingColorFilter(0xff000000, 0xffffffff));
    Matrix matrix = new Matrix();
    matrix.postScale(0.25f, 0.25f);
    Bitmap resizedMan =
            Bitmap.createBitmap(image, 0, 0, image.getWidth(), image.getHeight(), matrix, true);
    drawingSurface.drawBitmap(resizedMan, x, y, paint);
  }


  /** Update this view. */
  @Override
  public void update() {
    ((MatchstickMenPresenter) getPresenter()).update();
  }

  /**
   * Set the customization of the game to matchstick men customization
   *
   * @param matchstickMenCustomization the customization object for this game.
   */
  public void setMatchstickMenCustomization(MatchstickMenCustomization matchstickMenCustomization) {
    this.matchstickMenCustomization = matchstickMenCustomization;
    ((MatchstickMenPresenter) this.getPresenter())
        .setMatchstickMenCustomization(matchstickMenCustomization);
  }

  public BitmapGetter getBitmapGetter(Context context){
    return new BitmapGetter(context);
  }
}
