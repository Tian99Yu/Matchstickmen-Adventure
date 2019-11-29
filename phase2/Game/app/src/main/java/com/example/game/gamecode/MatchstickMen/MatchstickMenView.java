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
import android.graphics.Matrix;
import android.graphics.Paint;

public class MatchstickMenView extends GameView<MatchstickMenObject> implements MatchstickMenDrawer<Canvas> {
    //    private MatchstickMenActivity matchstickMenActivity;
    private int color;
    private String character;
    private int level;
    private int setUpInterval;

    /**
     * The background color this view.
     */
    private int backgroundColor = Color.BLACK;


    public MatchstickMenView(Context context) {
        super(context);
        thread = new GameThread(getHolder(), this, null);
        gameBackend = new MatchstickMenBackend(); //change it after you know the size of the canvas


//        this.matchstickMenActivity = matchstickMenActivity;

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
     * Draw the MatchstickMenObject.
     * @param drawingSurface the surface to be drawn onto
     * @param man the man to be drawn
     */
    @Override
    public void drawMan(Canvas drawingSurface, MatchstickMenObject man) {

        Paint paint = new Paint();
        paint.setColorFilter(new LightingColorFilter(0xff000000, 0xffffffff));
        Bitmap manBmp = BitmapFactory.decodeResource(getContext().getResources(), man.getSourceId());
        Matrix matrix = new Matrix();
        matrix.postScale(0.25f, 0.25f);
        Bitmap resizedMan = Bitmap.createBitmap(manBmp, 0, 0, manBmp.getWidth(), manBmp.getHeight(), matrix, true);
        drawingSurface.drawBitmap(resizedMan, man.x, man.y, paint);
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    /**
     * Update this view.
     */
    @Override
    public void update() {
        ((MatchstickMenPresenter) getPresenter()).update();
    }
}
