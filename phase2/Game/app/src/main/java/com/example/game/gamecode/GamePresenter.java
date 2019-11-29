package com.example.game.gamecode;

public abstract class GamePresenter<T, K> {
  /** Backend of game being presented. */
  protected GameBackend<K> backend;

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
