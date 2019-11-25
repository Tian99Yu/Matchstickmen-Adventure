package com.example.game.gamecode.MatchstickMen;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.game.gamecode.GameObject;

import java.util.Random;

public class MatchstickMenObject extends GameObject {
    /**
     * The x-coordinate of this matchstick men object
     */
    public int x;

    /**
     * The y-coordinate of this matchstick men object
     */
    public int y;

    /**
     * The color of the matchstick man.
     */
    private int color;

    /**
     * The character that will appear on the screen.
     */
    private MatchstickMenType manType;

    /**
     * Constructs a new MatchstickMen object.
     *
     * @param x the initial x coordinate of this MatchstickMen object
     * @param y the initial y coordinate of this MatchstickMen object
     */
    MatchstickMenObject(int x, int y, MatchstickMenType manType) {
        this.x = x;
        this.y = y;
        this.manType = manType;
    }

    public MatchstickMenType getManType() {
        return manType;
    }

    public void setManType(MatchstickMenType manType) {
        this.manType = manType;
    }

    @Override
    public void draw(Canvas canvas) {
    }
}
