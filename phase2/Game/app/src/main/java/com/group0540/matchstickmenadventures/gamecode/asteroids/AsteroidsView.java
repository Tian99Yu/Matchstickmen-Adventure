package com.group0540.matchstickmenadventures.gamecode.asteroids;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;

import com.example.matchstickmenadventures.R;
import com.group0540.matchstickmenadventures.gamecode.GameThread;
import com.group0540.matchstickmenadventures.gamecode.GameView;
import com.group0540.matchstickmenadventures.leaderboardcode.Saver;

import java.util.HashMap;

class AsteroidsView extends GameView<AsteroidGameObject>
    implements AsteroidsDrawer<Canvas, Bitmap> {
  private float density;

  public AsteroidsView(Context context, int playAreaWidth, int playAreaHeight) {
    super(context);
    this.thread = new GameThread(getHolder(), this, (Saver) context);
    this.gameBackend = new AsteroidGameManager(playAreaWidth, playAreaHeight);

    setPresenter(new AsteroidsPresenter<>(this, this.gameBackend, getClassToColoredSprite()));
    density = context.getResources().getDisplayMetrics().density;
  }

  public HashMap<Class<? extends AsteroidGameObject>, ColoredSprite<Bitmap>>
      getClassToColoredSprite() {
    HashMap<Class<? extends AsteroidGameObject>, ColoredSprite<Bitmap>> classToColoredSprite =
        new HashMap<>();
    // create colored sprites
    ColoredSprite<Bitmap> asteroidColoredSprite, shipColoredSprite;
    if (AsteroidCustomizations.themeIndex == 0) {
      asteroidColoredSprite =
          new ColoredSprite<>(
              BitmapFactory.decodeResource(getResources(), R.drawable.asteroid2),
              Color.TRANSPARENT);
      shipColoredSprite =
          new ColoredSprite<>(
              BitmapFactory.decodeResource(getResources(), R.drawable.baldspaceship),
              Color.TRANSPARENT);
    } else {
      asteroidColoredSprite =
          new ColoredSprite<>(
              BitmapFactory.decodeResource(getResources(), R.drawable.asteroid1),
              Color.TRANSPARENT);
      shipColoredSprite =
          new ColoredSprite<>(
              BitmapFactory.decodeResource(getResources(), R.drawable.excitedspaceship),
              Color.TRANSPARENT);
    }
    ColoredSprite<Bitmap> laserColoredSprite =
        new ColoredSprite<>(
            BitmapFactory.decodeResource(getResources(), R.drawable.laser), Color.RED);
    ColoredSprite<Bitmap> explosivePowerupAsteroidSprite =
        new ColoredSprite<>(
            BitmapFactory.decodeResource(getResources(), R.drawable.asteroid), Color.RED);
    ColoredSprite<Bitmap> additionalLifePowerupAsteroidSprite =
        new ColoredSprite<>(
            BitmapFactory.decodeResource(getResources(), R.drawable.asteroid), Color.GREEN);
    ColoredSprite<Bitmap> weaponPowerupAsteroidSprite =
        new ColoredSprite<>(
            BitmapFactory.decodeResource(getResources(), R.drawable.asteroid), Color.MAGENTA);
    ColoredSprite<Bitmap> asteroidSpawnerSprite =
        new ColoredSprite<>(
            BitmapFactory.decodeResource(getResources(), R.drawable.ship), Color.rgb(255, 180, 0));
    // add colored sprites to hashmap
    classToColoredSprite.put(Asteroid.class, asteroidColoredSprite);
    classToColoredSprite.put(Projectile.class, laserColoredSprite);
    classToColoredSprite.put(Ship.class, shipColoredSprite);
    classToColoredSprite.put(ExplosivePowerupAsteroid.class, explosivePowerupAsteroidSprite);
    classToColoredSprite.put(
        AdditionalLifePowerupAsteroid.class, additionalLifePowerupAsteroidSprite);
    classToColoredSprite.put(WeaponPowerupAsteroid.class, weaponPowerupAsteroidSprite);
    classToColoredSprite.put(AsteroidSpawner.class, asteroidSpawnerSprite);
    return classToColoredSprite;
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
    if (color != Color.TRANSPARENT) {
      paint.setColorFilter(filter);
    }
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
  public void drawText(
      Canvas drawingSurface,
      String text,
      int color,
      float x,
      float y,
      float fontSize,
      int rowOffset) {
    Paint paint = new Paint();
    paint.setColor(Color.WHITE);
    paint.setTextSize(fontSize);
    paint.setTextAlign(Paint.Align.RIGHT);
    drawingSurface.drawText(text, x, y + rowOffset * paint.getTextSize(), paint);
  }
}
