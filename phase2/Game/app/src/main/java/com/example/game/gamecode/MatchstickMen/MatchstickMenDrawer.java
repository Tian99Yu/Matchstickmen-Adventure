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
     * Draw the MatchstickMenObject.
     * @param drawingSurface the surface to be drawn onto
     * @param man the man to be drawn
     */
    void drawMan(T drawingSurface, MatchstickMenObject man);

    /**
     * Returns width of screen.
     */
    int getWidth();

    /**
     * Returns height of screen.
     */
    int getHeight();

}
