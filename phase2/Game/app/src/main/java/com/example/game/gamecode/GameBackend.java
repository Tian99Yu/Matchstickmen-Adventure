package com.example.game.gamecode;

import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.Iterator;

public abstract class GameBackend {
    protected ArrayList<GameObject> gameObjects = new ArrayList<>();
    public abstract void update();

    public void draw(Canvas canvas) {
        for (GameObject object : gameObjects) {
            object.draw(canvas);
        }
    }

    /**
     * Returns true iff the game is over.
     *
     * @return whether or not the game is over.
     */
    public abstract boolean isGameOver();

    /**
     * Returns the current score the player has earned in the current game.
     *
     * @return the players current score.
     */
    public abstract int getCurrentScore();

    public Iterator<GameObject> getGameObjectsIterator() {
        return gameObjects.iterator();
    }

}
