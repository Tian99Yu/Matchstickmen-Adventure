package com.example.game.gamecode.Asteroids;

import android.graphics.Color;

import com.example.game.gamecode.Drawer;
import com.example.game.gamecode.GameBackend;
import com.example.game.gamecode.GameObject;
import com.example.game.gamecode.GamePresenter;

import java.util.Iterator;
import java.util.Optional;

public class AsteroidsPresenter<T, S> extends GamePresenter<T, S> {
  AsteroidsPresenter(Drawer<T, S> drawer, GameBackend backend) {
    super(drawer, backend);
  }

  @Override
  public void draw(T drawingSurface) {
    drawer.drawSolidBackground(
        drawingSurface, AsteroidCustomizations.backgroundColor[AsteroidCustomizations.themeIndex]);
    drawer.drawText(
        drawingSurface,
        "Lives: " + ((AsteroidGameManager) backend).getLives(),
        Color.WHITE,
        drawer.getWidth() - 24 * drawer.getDensity(),
        0,
        24 * drawer.getDensity(),
        1);
    drawer.drawText(
        drawingSurface,
        "Score: " + backend.getCurrentScore(),
        Color.WHITE,
        drawer.getWidth() - 24 * drawer.getDensity(),
        0,
        24 * drawer.getDensity(),
        2);
    for (Iterator<GameObject> gameObjectIterator = backend.getGameObjectsIterator();
        gameObjectIterator.hasNext(); ) {
      AsteroidGameObject asteroidGameObject = (AsteroidGameObject) gameObjectIterator.next();
      drawer.drawSprite(
          drawingSurface,
          classToSprite.get(asteroidGameObject.getClass()),
          Optional.ofNullable(classToColor.get(asteroidGameObject.getClass())).orElse(0),
          asteroidGameObject.x,
          asteroidGameObject.y,
          asteroidGameObject.angle,
          asteroidGameObject.collisionRadius * 2,
          asteroidGameObject.collisionRadius * 2);
    }
  }
}
