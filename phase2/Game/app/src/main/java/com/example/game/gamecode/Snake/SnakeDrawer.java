package com.example.game.gamecode.Snake;

interface SnakeDrawer<T> {
  /**
   * Draw a rectangle on the drawing surface a rectangle determined by left, top, right, and bottom.
   * @param drawingSurface the surface to be drawn in
   * @param left the x coordinate of the left side of the rectangle
   * @param top the y coordinate of the top side of the rectangle
   * @param right the x coordinate of the right side of the rectangle
   * @param bottom the y coordinate of the bottom side of the rectangle
   * @param color the color of the rectangle
   */
  void drawRect(T drawingSurface, float left, float top, float right, float bottom, int color);

  /**
   * Draw the current status of this game on the drawingSurface.
   * @param drawingSurface the canvas to draw this game on.
   */
  void drawUpdate(T drawingSurface);

  /** Returns width of screen. */
  int getWidth();

  /** Returns height of screen. */
  int getHeight();
}
