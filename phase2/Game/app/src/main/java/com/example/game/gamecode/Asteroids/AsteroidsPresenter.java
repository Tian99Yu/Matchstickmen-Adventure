package com.example.game.gamecode.Asteroids;

import android.graphics.Color;

import com.example.game.gamecode.GameBackend;
import com.example.game.gamecode.GamePresenter;

import java.util.HashMap;
import java.util.Iterator;

public class AsteroidsPresenter<T, S> extends GamePresenter<T, AsteroidGameObject> {
  /** A mapping between game objects classes and the color of that game object. */
  private HashMap<Class<? extends AsteroidGameObject>, ColoredSprite<S>> classToColoredSprite;
  /** The AsteroidsDrawer that handles drawing for the asteroids game. */
  private AsteroidsDrawer<T, S> asteroidsDrawer;

  AsteroidsPresenter(
      AsteroidsDrawer<T, S> asteroidsDrawer,
      GameBackend<AsteroidGameObject> backend,
      HashMap<Class<? extends AsteroidGameObject>, ColoredSprite<S>> classToColoredSprite) {
    super(backend);
    this.asteroidsDrawer = asteroidsDrawer;
    this.classToColoredSprite = classToColoredSprite;
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

    for (AsteroidGameObject asteroidGameObject: backend) {
      ColoredSprite<S> coloredSprite = classToColoredSprite.get(asteroidGameObject.getClass());
      if (coloredSprite != null) {
        asteroidsDrawer.drawSprite(
                drawingSurface,
                coloredSprite.getSprite(),
                coloredSprite.getColor(),
                asteroidGameObject.x,
                asteroidGameObject.y,
                asteroidGameObject.getAngle(),
                asteroidGameObject.collisionRadius * 2,
                asteroidGameObject.collisionRadius * 2);
      }
    }
  }
}
