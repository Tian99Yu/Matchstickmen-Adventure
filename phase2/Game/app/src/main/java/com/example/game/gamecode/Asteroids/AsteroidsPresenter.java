package com.example.game.gamecode.Asteroids;

import android.graphics.Color;

import com.example.game.gamecode.GameBackend;
import com.example.game.gamecode.GameObject;
import com.example.game.gamecode.GamePresenter;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Optional;

public class AsteroidsPresenter<T, S> extends GamePresenter<T> {
  /**
   * A mapping between game objects classes and sprites of type S that can be drawn on drawing
   * surfaces of type T.
   */
  private HashMap<Class<? extends GameObject>, S> classToSprite;
  /**
   * A mapping between game objects classes and the color of that game object.
   */
  private HashMap<Class<? extends GameObject>, Integer> classToColor;
  /** The AsteroidsDrawer that handles drawing for the asteroids game. */
  private AsteroidsDrawer<T, S> asteroidsDrawer;

  AsteroidsPresenter(AsteroidsDrawer<T, S> asteroidsDrawer, GameBackend backend) {
    super(backend);
    this.asteroidsDrawer = asteroidsDrawer;
    this.classToSprite = asteroidsDrawer.getClassToSprite();
    this.classToColor = asteroidsDrawer.getClassToColor();
  }

  @Override
  public void draw(T drawingSurface) {
    asteroidsDrawer.drawSolidBackground(
        drawingSurface, AsteroidCustomizations.backgroundColor[AsteroidCustomizations.themeIndex]);
    asteroidsDrawer.drawText(
        drawingSurface,
        "Lives: " + ((AsteroidGameManager) backend).getLives(),
        Color.WHITE,
        asteroidsDrawer.getWidth() - 24 * asteroidsDrawer.getDensity(),
        0,
        24 * asteroidsDrawer.getDensity(),
        1);
    asteroidsDrawer.drawText(
        drawingSurface,
        "Score: " + backend.getCurrentScore(),
        Color.WHITE,
        asteroidsDrawer.getWidth() - 24 * asteroidsDrawer.getDensity(),
        0,
        24 * asteroidsDrawer.getDensity(),
        2);
    for (Iterator<GameObject> gameObjectIterator = backend.getGameObjectsIterator();
        gameObjectIterator.hasNext(); ) {
      AsteroidGameObject asteroidGameObject = (AsteroidGameObject) gameObjectIterator.next();
      asteroidsDrawer.drawSprite(
          drawingSurface,
          classToSprite.get(asteroidGameObject.getClass()),
          Optional.ofNullable(classToColor.get(asteroidGameObject.getClass())).orElse(0),
          asteroidGameObject.x,
          asteroidGameObject.y,
          asteroidGameObject.getAngle(),
          asteroidGameObject.collisionRadius * 2,
          asteroidGameObject.collisionRadius * 2);
    }
  }
}
