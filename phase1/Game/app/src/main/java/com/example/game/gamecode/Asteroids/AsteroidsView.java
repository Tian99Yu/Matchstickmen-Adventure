package com.example.game.gamecode.Asteroids;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;

import com.example.game.R;
import com.example.game.gamecode.GameThread;
import com.example.game.gamecode.GameView;

class AsteroidsView extends GameView {

  public AsteroidsView(Context context, int playAreaWidth, int playAreaHeight) {
    super(context);
    thread = new GameThread(getHolder(), this);
    gameBackend = new AsteroidGameManager(playAreaWidth, playAreaHeight);
  }
}
