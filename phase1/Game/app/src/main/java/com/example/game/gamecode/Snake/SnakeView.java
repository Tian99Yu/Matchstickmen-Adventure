package com.example.game.gamecode.Snake;

import android.content.Context;
import android.content.res.Resources;

import androidx.annotation.MainThread;

import com.example.game.gamecode.GameThread;
import com.example.game.gamecode.GameView;

public class SnakeView extends GameView {
    private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

    // the hight and width of the snakeObj
    public static float objWidth;
    public static float objHight;

    public GameThread thread;



    public SnakeView(Context context) {
        super(context);
    }

}
