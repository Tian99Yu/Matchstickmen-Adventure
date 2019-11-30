package com.group0540.matchstickmenadventures.gamecode;

public abstract class GamePresenter<T, K> {
  /** Backend of game being presented. */
  protected final GameBackend<K> backend;

  public GamePresenter(GameBackend<K> backend) {
    this.backend = backend;
  }

  /**
   * Draws the game onto drawingSurface.
   *
   * @param drawingSurface the surface to be drawn on.
   */
  public abstract void draw(T drawingSurface);
}
