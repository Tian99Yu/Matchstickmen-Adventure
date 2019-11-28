package com.example.game.gamecode.MatchstickMen;

import android.graphics.Canvas;
import android.graphics.Color;

import com.example.game.R;
import com.example.game.gamecode.GameBackend;
import com.example.game.gamecode.GamePresenter;

import java.util.Iterator;
import java.util.Random;

public class MatchstickMenPresenter<T> extends GamePresenter<T> {

    /**
     * The MatchstickMenDrawer that handles drawing for the matchstick men game.
     */
    private MatchstickMenDrawer<T> matchstickMenDrawer;

    /**
     * The color of the matchstick man.
     */
    private int manColor;

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
        (this.backend).update();
    }

    /**
     * Randomly draw the characters on the drawing surface and record the number of characters drawn.
     *
     * @param manObject      the man object that is going to be drawn
     * @param drawingSurface the surface to be drawn in
     */
    private void drawMatchstickMenObject(MatchstickMenObject manObject, T drawingSurface) {
        MatchstickMenType manType = manObject.getManType();

        Random random = new Random();
        int height = matchstickMenDrawer.getHeight();
        int width = matchstickMenDrawer.getWidth();
        int range = random.nextInt((int) width * height);

        int i = 0;
        int sum = 0;
        while (i < range) {
            if (manType == MatchstickMenType.HAPPY_MAN) {
                int x = random.nextInt(width - 200);
                int y = random.nextInt(height - 200);
//                matchstickMenDrawer.drawRect(drawingSurface, x, y, x + 100, y + 100, manColor);
                matchstickMenDrawer.drawMan((Canvas) drawingSurface, x, y, R.drawable.happyman);
            } else if (manType == MatchstickMenType.EXCITED_MAN) {
//                int x = random.nextInt(width - 100 + 1) + 50;
//                int y = random.nextInt(height - 100 + 1) + 50;
//                matchstickMenDrawer.drawCircle(drawingSurface, x, y, 50, manColor);

                int x = random.nextInt(width - 200);
                int y = random.nextInt(height - 200);
                matchstickMenDrawer.drawMan((Canvas) drawingSurface, x, y, R.drawable.excitedman);
            }
            int x = random.nextInt(width - 200);
            int y = random.nextInt(height - 200);
            matchstickMenDrawer.drawMan((Canvas) drawingSurface, x, y, R.drawable.baldman);
            int increment = random.nextInt(range - 1) + 1;
            i += increment;
            sum += 2;
        }
        ((MatchstickMenBackend) this.backend).setAnswer(sum);
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

        Iterator<MatchstickMenObject> gameObjectIterator = matchstickMenBackend.getGameObjectsIterator();
        while (gameObjectIterator.hasNext()) {
            MatchstickMenObject gameObject = gameObjectIterator.next();
            if (gameObject != null) {
                drawMatchstickMenObject(gameObject, drawingSurface);
            }
        }
    }
}
