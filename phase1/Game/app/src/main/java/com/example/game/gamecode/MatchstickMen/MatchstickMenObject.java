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

    private MatchstickMenBackend backend;

    private int color;
    private String character;

    /**
     * Constructs a new MatchstickMen object.
     *
     * @param x the initial x coordinate of this MatchstickMen object
     * @param y the initial y coordinate of this MatchstickMen object
     */
    MatchstickMenObject(int x, int y, MatchstickMenBackend backend, int color, String character) {
        this.x = x;
        this.y = y;
        this.backend = backend;
        this.color = color;
        this.character = character;
        this.paint.setColor(color);
    }


    public void draw(Canvas canvas) {
        Random random = new Random();

        int range = random.nextInt(canvas.getHeight() * canvas.getWidth());

        int i = 0;
        int sum = 0;
        while (i < range) {
            int x = random.nextInt(canvas.getWidth()-100 + 1) + 50;
            int y = random.nextInt(canvas.getHeight()-100 + 1) + 50;
            if (character.equals("rect")){
            canvas.drawRect(x, y,x-100, y-100, paint);}
            else if(character.equals("circle")){
                canvas.drawCircle(x,y,50, paint);
            }
            int increment = random.nextInt(range);
            i += increment;
            sum++;
        }
        this.backend.setAnswer(sum);
    }


}
