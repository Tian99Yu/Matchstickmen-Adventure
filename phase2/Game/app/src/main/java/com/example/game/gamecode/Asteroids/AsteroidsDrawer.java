package com.example.game.gamecode.Asteroids;

import com.example.game.gamecode.GameObject;

import java.util.HashMap;

interface AsteroidsDrawer<T, S> {
  /** Returns width of screen. */
  int getWidth();

  /** Returns height of screen. */
  int getHeight();

  /** Returns the density of the screen. */
  float getDensity();

  /**
   * Draws the sprite at x, y rotated by angle and scaled to be of size width times height onto
   * drawingSurface.
   *
   * @param drawingSurface the surface to be drawn on.
   * @param sprite the image to be drawn.
   * @param color the color of the image.
   * @param x the x coordinate.
   * @param y the y coordinate.
   * @param angle the angle to rotate the image.
   * @param width the width to scale the image to.
   * @param height the height to scale the image to.
   */
  void drawSprite(
      T drawingSurface,
      S sprite,
      int color,
      double x,
      double y,
      double angle,
      double width,
      double height);

  /**
   * Draws a solid background with color color onto drawingSurface.
   *
   * @param drawingSurface the surface to be drawn on.
   * @param color the color of the background.
   */
  void drawSolidBackground(T drawingSurface, int color);

  /**
   * Draws text with color color onto the drawingSurface at x, y offset by rowOffset number of rows.
   *
   * @param drawingSurface the surface to be drawn on.
   * @param text the text to display.
   * @param color the color of the text.
   * @param x the x coordinate.
   * @param y the y coordinate.
   * @param fontSize the size of the font.
   * @param rowOffset the number of rows to offset the text down by.
   */
  void drawText(
      T drawingSurface, String text, int color, float x, float y, float fontSize, int rowOffset);
}
