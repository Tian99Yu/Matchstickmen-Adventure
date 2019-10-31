package com.example.game.gamecode.MatchstickMen;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.game.gamecode.GameObject;

import java.util.Random;

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


    public void draw(Canvas canvas) {
        Random random = new Random();
        int range = random.nextInt(canvas.getHeight() * canvas.getWidth());
        int i = 0;
        while (i < range) {
            int x = random.nextInt(canvas.getWidth());
            int y = random.nextInt(canvas.getHeight());
            canvas.drawCircle(x, y, 100, paint);
            int increment = random.nextInt(range);
            i += increment;
        }
    }


}
