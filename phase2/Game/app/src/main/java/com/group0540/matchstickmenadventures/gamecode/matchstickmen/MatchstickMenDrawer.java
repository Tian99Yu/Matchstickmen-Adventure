package com.group0540.matchstickmenadventures.gamecode.matchstickmen;


/**
 * A drawer that draws the game images onto the display
 *
 * @param <T> the surface that the game will be drawn onto
 */
public interface MatchstickMenDrawer<T, K> {

  /**
   * Draw the background of this game on canvas.
   *
   * @param drawingSurface the canvas that the game in running on.
   */
  void drawBackground(T drawingSurface);

  /** Returns width of screen. */
  int getWidth();

  /** Returns height of screen. */
  int getHeight();

  /**
   * Draw the MatchstickMenObject.
   *
   * @param drawingSurface the surface to be drawn onto
   * @param image the bitmap to be drawn
   */
  void drawImage(T drawingSurface, K image, int x, int y);
}
