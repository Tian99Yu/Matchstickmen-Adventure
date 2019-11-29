package com.example.game.gamecode.MatchstickMen;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.game.R;

import java.util.Random;

public abstract class MatchstickMenObject {
    /**
     * The x-coordinate of this MatchstickMenObjectv
     */
    public int x;

    /**
     * The y-coordinate of this MatchstickMenObject
     */
    public int y;

    /**
     * Indicates whether this MatchstickMenObject is moving right.
     * */
    private boolean goingRight;

    /**
     * The character that will appear on the screen.
     */
    private MatchstickMenType manType;

    /**
     * The id of the source image of this MatchstickMenObject.
     */
    private int sourceId;

    /**
     * The width of displaying area.
     */
    private int gridWidth;

    /**
     * The height of displaying area.
     */
    private int gridHeight;

    /**
     * Constructs a new MatchstickMen object.
     *
     * @param x the initial x coordinate of this MatchstickMen object
     * @param y the initial y coordinate of this MatchstickMen object
     */
    MatchstickMenObject(int x, int y, int gridWidth, int gridHeight, MatchstickMenType manType, int sourceId) {
        this.x = x;
        this.y = y;
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
        this.manType = manType;
        this.sourceId = sourceId;
        this.goingRight = true;
    }

    /**
     * Gets the width of the displaying area.
     *
     * @return the gridWidth
     */
    public int getGridWidth() {
        return gridWidth;
    }

    /**
     * Gets the height of the displaying area.
     *
     * @return the gridHeight
     */
    public int getGridHeight() {
        return gridHeight;
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

    /**
     * Turns this MatchstickMenObject around, causing it to reverse direction.
     */
    protected void turnAround() {
        goingRight = !goingRight;
    }

    public boolean isGoingRight() {
        return goingRight;
    }

    /**
     * Move this MatchstickMenObject.
     */
    abstract void move();
}
