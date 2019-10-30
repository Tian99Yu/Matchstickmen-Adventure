package com.example.game.gamecode.MatchstickMen;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.game.gamecode.GameObject;

public class MatchstickMenObject extends GameObject {
    /** The x-coordinate of this snake object */
    public int x;

    /** The y-coordinate of this snake object */
    public int y;

    /** The color of this snake object */
    private Paint paint = new Paint();

    /**
     * Constructs a new MatchstickMen object.
     *
     * @param x the initial x coordinate of this MatchstickMen object
     * @param y the initial y coordinate of this MatchstickMen object
     */
    MatchstickMenObject(int x, int y) {
        this.x = x;
        this.y = y;
        this.paint.setColor(Color.WHITE);
    }

    @Override
    public void draw(Canvas canvas) {

    }
}
