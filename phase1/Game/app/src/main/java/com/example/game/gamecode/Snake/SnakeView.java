package com.example.game.gamecode.Snake;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.widget.ImageButton;

import androidx.annotation.MainThread;

import com.example.game.gamecode.GameBackend;
import com.example.game.gamecode.GameThread;
import com.example.game.gamecode.GameView;

public class SnakeView extends GameView {

    public SnakeView(Context context) {
        super(context);
        thread = new GameThread(getHolder(), this);

        int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
        int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
        game = new SnakeBackend(screenHeight, screenWidth);

        ((SnakeBackend) game).createObjects();
    }
}
