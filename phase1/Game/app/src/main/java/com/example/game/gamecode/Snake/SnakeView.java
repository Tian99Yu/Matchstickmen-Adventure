package com.example.game.gamecode.Snake;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.MainThread;

import com.example.game.R;
import com.example.game.gamecode.GameBackend;
import com.example.game.gamecode.GameThread;
import com.example.game.gamecode.GameView;

public class SnakeView extends GameView {

  public SnakeView(Context context) {
    super(context);
    thread = new GameThread(getHolder(), this);
    thread.setUpdateInterval(100);

    // int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels - 104;
    // int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels - 16;

    int screenHeight = 1650;
    int screenWidth = 960;
    game = new SnakeBackend(screenHeight, screenWidth);

    ((SnakeBackend) game).createObjects();
  }

  @Override
  public void update() {
    super.update();
    if (((SnakeBackend) game).isLost()) {
      thread.setRunning(false);
    }
  }
}
