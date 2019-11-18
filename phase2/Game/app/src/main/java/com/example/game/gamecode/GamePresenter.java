package com.example.game.gamecode;

import java.util.HashMap;

public abstract class GamePresenter<T, S> {
  /**
   * A mapping between game objects classes and sprites of type S that can be drawn on drawing
   * surfaces of type T.
   */
  protected HashMap<Class<? extends GameObject>, S> classToSprite;
  /**
   * A mapping between game objects classes and the color of that game object.
   */
  protected HashMap<Class<? extends GameObject>, Integer> classToColor;
  /** The drawer that handles drawing. */
  protected Drawer<T, S> drawer;
  /** Backend of game being presented. */
  protected GameBackend backend;

  public GamePresenter(Drawer<T, S> drawer, GameBackend backend) {
    this.drawer = drawer;
    this.backend = backend;
    this.classToSprite = drawer.getClassToSprite();
    this.classToColor = drawer.getClassToColor();
  }

  /**
   * Draws the game onto drawingSurface.
   *
   * @param drawingSurface the surface to be drawn on.
   */
  public abstract void draw(T drawingSurface);
}
