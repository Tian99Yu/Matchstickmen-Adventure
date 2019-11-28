package com.example.game.gamecode.MatchstickMen;

import android.graphics.Canvas;

/**
 * A drawer that draws the game images onto the display
 *
 * @param <T> the surface that the game will be drawn onto
 */
public interface MatchstickMenDrawer<T> {

    /**
     * Draw the background of this game on canvas.
     *
     * @param drawingSurface the canvas that the game in running on.
     */
    void drawBackground(T drawingSurface);

    /**
     * Draw a rectangle on the drawing surface a rectangle determined by left, top, right, and bottom.
     *
     * @param drawingSurface the surface to be drawn in
     * @param left           the x coordinate of the left side of the rectangle
     * @param top            the y coordinate of the top side of the rectangle
     * @param right          the x coordinate of the right side of the rectangle
     * @param bottom         the y coordinate of the bottom side of the rectangle
     * @param color          the color of the rectangle
     */
    void drawRect(T drawingSurface, float left, float top, float right, float bottom, int color);

    public void drawMan(Canvas drawingSurface, float x, float y, int manResource);

    /**
     * Returns width of screen.
     */
    int getWidth();

    /**
     * Returns height of screen.
     */
    int getHeight();

    /**
     * Draw a circle on the drawing surface a circle determined by x, y and radius.
     *
     * @param drawingSurface the surface to be drawn in
     * @param x              the x coordinate of the center of the circle
     * @param y              the y coordinate of the center of the circle
     * @param radius         the radius of the circle
     * @param color          the color of the circle
     */
    void drawCircle(T drawingSurface, float x, float y, float radius, int color);

}
