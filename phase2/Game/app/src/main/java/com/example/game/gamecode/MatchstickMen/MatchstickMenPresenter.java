package com.example.game.gamecode.MatchstickMen;

import android.graphics.Canvas;
import android.graphics.Color;

import com.example.game.R;
import com.example.game.gamecode.GameBackend;
import com.example.game.gamecode.GamePresenter;

import java.util.Iterator;
import java.util.Random;

public class MatchstickMenPresenter<T> extends GamePresenter<T, MatchstickMenObject> {

    /**
     * The MatchstickMenDrawer that handles drawing for the matchstick men game.
     */
    private MatchstickMenDrawer<T> matchstickMenDrawer;

    /**
     * The color of the matchstick man.
     */
    private int manColor;

    /**
     * Whether the game backend is initialized
     */
    private boolean initialized = false;


    /**
     * Constructor for matchstick men presenter
     *
     * @param matchstickMenDrawer the surface to be drawn on
     * @param backend             the game backend of the game to be presented.
     */
    MatchstickMenPresenter(MatchstickMenDrawer<T> matchstickMenDrawer, GameBackend backend) {
        super(backend);
        this.matchstickMenDrawer = matchstickMenDrawer;
        this.manColor = Color.WHITE;
    }

    /**
     * Update and refresh the game status.
     */
    void update() {
        if (initialized) {
            (this.backend).update();
        } else {
            int height = matchstickMenDrawer.getHeight();
            int width = matchstickMenDrawer.getWidth();

            MatchstickMenBackend matchstickMenBackend = (MatchstickMenBackend) backend;
            matchstickMenBackend.setGridHeight(height);
            matchstickMenBackend.setGridWidth(width);

            matchstickMenBackend.createObjects();

            this.initialized = true;
        }
    }

    /**
     * Draw the man objects on the drawing surface.
     *
     * @param manObject      the man object that is going to be drawn
     * @param drawingSurface the surface to be drawn in
     */
    private void drawMatchstickMenObject(MatchstickMenObject manObject, T drawingSurface) {
        matchstickMenDrawer.drawMan(drawingSurface, manObject);
        ((MatchstickMenBackend) this.backend).setAnswer();
    }

    /**
     * Draw the game onto the drawing surface.
     *
     * @param drawingSurface the surface to be drawn on.
     */
    @Override
    public void draw(T drawingSurface) {
        MatchstickMenBackend matchstickMenBackend = (MatchstickMenBackend) this.backend;

        matchstickMenDrawer.drawBackground(drawingSurface);

        for (MatchstickMenObject matchstickMenObject: backend) {
            if (matchstickMenObject != null) {
                drawMatchstickMenObject(matchstickMenObject, drawingSurface);
            }
        }
    }
}
