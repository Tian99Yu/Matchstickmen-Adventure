package com.example.game.gamecode.MatchstickMen;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.game.R;

import java.util.Random;

public class MatchstickMenObject {
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
     * The id of the source image of this MatchstickMenObject.
     */
    private int sourceId;

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

    /**
     * Gets the type of this MatchstickMenObject.
     *
     * @return the type of this MatchstickMenObject
     */
    public MatchstickMenType getManType() {
        return manType;
    }

    /**
     * Sets the type of this MatchstickMenObject.
     *
     * @param manType the type of this MatchstickMenObject
     */
    public void setManType(MatchstickMenType manType) {
        this.manType = manType;
    }

    /**
     * Gets the source id of this MatchstickMenObject.
     *
     * @return the source id of this MatchstickMenObject
     */
    public int getSourceId() {
        return sourceId;
    }

    /**
     * Sets the source id of this MatchstickMenObject.
     *
     * @param sourceId the source id of this MatchstickMenObject
     */
    void setSourceId(int sourceId) {
        this.sourceId = sourceId;
    }
}
