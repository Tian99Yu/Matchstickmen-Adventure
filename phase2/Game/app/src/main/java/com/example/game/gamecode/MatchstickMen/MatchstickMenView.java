package com.example.game.gamecode.MatchstickMen;

import android.content.Context;

import com.example.game.gamecode.GameThread;
import com.example.game.gamecode.GameView;
import com.example.game.R;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;

public class MatchstickMenView extends GameView implements MatchstickMenDrawer<Canvas> {
    private MatchstickMenActivity matchstickMenActivity;
    private int color;
    private String character;
    private int level;
    private int setUpInterval;

    /**
     * The background color this view.
     */
    private int backgroundColor = Color.BLACK;


    public MatchstickMenView(Context context, MatchstickMenActivity matchstickMenActivity) {
        super(context);
        thread = new GameThread(getHolder(), this, null);
        gameBackend = new MatchstickMenBackend(); //change it after you know the size of the canvas


        this.matchstickMenActivity = matchstickMenActivity;
        this.color = matchstickMenActivity.getColor();
        this.level = matchstickMenActivity.getLevel();
        this.character = matchstickMenActivity.getCharacter();
        switch (level){
            case 0:
                setUpInterval = 10000000;
                break;
            case 1:
                setUpInterval = 7000000;
                break;
            case 2:
                setUpInterval = 5000000;
                break;
            default:
                setUpInterval = 10000000;
        }
        thread.setUpdateInterval(setUpInterval);
        ((MatchstickMenBackend)this.gameBackend).inject(color, level, character);
        ((MatchstickMenBackend) gameBackend).createObjects();

        setPresenter(new MatchstickMenPresenter<Canvas>(this, this.gameBackend));

    }


    /**
     * Draw the background of this game on canvas.
     *
     * @param drawingSurface the canvas that the game in running on.
     */
    @Override
    public void drawBackground(Canvas drawingSurface) {
        Paint paint = new Paint();
        paint.setColor(backgroundColor);
        //
        paint.setStyle(Paint.Style.FILL);
        drawingSurface.drawPaint(paint);
    }

    /**
     * Draw a rectangle on the drawing surface a rectangle determined by left, top, right, and bottom.
     *
     * @param drawingSurface the surface to be drawn in
     * @param left           the x coordinate of the left side of the rectangle
     * @param top            the y coordinate of the top side of the rectangle
     * @param right          the x coordinate of the right side of the rectangle
     * @param bottom         the y coordinate of the bottom side of the rectangle
     * @param color          the color of the rectangle
     */
    @Override
    public void drawRect(
            Canvas drawingSurface, float left, float top, float right, float bottom, int color) {
        Paint paint = new Paint();
        paint.setColor(color);
        drawingSurface.drawRect(left, top, right, bottom, paint);
    }

    /**
     * Draw a circle on the drawing surface a circle determined by x, y and radius.
     *
     * @param drawingSurface the surface to be drawn in
     * @param x              the x coordinate of the center of the circle
     * @param y              the y coordinate of the center of the circle
     * @param radius         the radius of the circle
     * @param color          the color of the circle
     */
    public void drawCircle(Canvas drawingSurface, float x, float y, float radius, int color) {
        Paint paint = new Paint();
        paint.setColor(color);
        drawingSurface.drawCircle(x, y, radius, paint);
    }

    public void drawMan(Canvas drawingSurface, float x, float y, int manResource) {
        Paint paint = new Paint();
        paint.setColorFilter(new LightingColorFilter(0xff000000, 0xffffffff));
        Bitmap man1 = BitmapFactory.decodeResource(getContext().getResources(), manResource);
        drawingSurface.drawBitmap(man1, x, y, paint);
    }

    /**
     * Update this view.
     */
    @Override
    public void update() {
        ((MatchstickMenPresenter) getPresenter()).update();
    }
}
