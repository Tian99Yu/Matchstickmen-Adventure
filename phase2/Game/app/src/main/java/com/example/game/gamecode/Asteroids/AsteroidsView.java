package com.example.game.gamecode.Asteroids;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.game.R;
import com.example.game.gamecode.GameObject;
import com.example.game.gamecode.GameThread;
import com.example.game.gamecode.GameView;
import com.example.game.leaderboardcode.Saver;

import java.util.HashMap;

class AsteroidsView extends GameView {

  public AsteroidsView(Context context, int playAreaWidth, int playAreaHeight) {
    super(context);
    this.thread = new GameThread(getHolder(), this, (Saver) context);
    this.gameBackend = new AsteroidGameManager(playAreaWidth, playAreaHeight);
    setPresenter(new AsteroidsPresenter<>(this, this.gameBackend));
  }


  @Override
  public HashMap<Class<? extends GameObject>, Bitmap> getClassToSprite() {
    HashMap<Class<? extends GameObject>, Bitmap> classToSprites = new HashMap<>();
    classToSprites.put(Asteroid.class, BitmapFactory.decodeResource(getResources(), R.drawable.asteroid));
    classToSprites.put(Projectile.class, BitmapFactory.decodeResource(getResources(), R.drawable.laser));
    classToSprites.put(Ship.class, BitmapFactory.decodeResource(getResources(), R.drawable.ship));
    return classToSprites;
  }

  @Override
  public HashMap<Class<? extends GameObject>, Integer> getClassToColor() {
    HashMap<Class<? extends GameObject>, Integer> classToColor = new HashMap<>();
    classToColor.put(Asteroid.class, AsteroidCustomizations.asteroidColor[AsteroidCustomizations.themeIndex]);
    classToColor.put(Projectile.class, AsteroidCustomizations.projectileColor[AsteroidCustomizations.themeIndex]);
    classToColor.put(Ship.class, AsteroidCustomizations.shipColor[AsteroidCustomizations.themeIndex]);
    return classToColor;
  }
}
