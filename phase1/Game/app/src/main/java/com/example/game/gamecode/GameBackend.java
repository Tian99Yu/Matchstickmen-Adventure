package com.example.game.gamecode;

import android.graphics.Canvas;

import java.util.ArrayList;

public abstract class GameBackend {
    protected ArrayList<GameObject> gameObjects = new ArrayList<>();
    public abstract void update();

    public void draw(Canvas canvas) {
        for (GameObject object : gameObjects) {
            object.draw(canvas);
        }
    }
}
