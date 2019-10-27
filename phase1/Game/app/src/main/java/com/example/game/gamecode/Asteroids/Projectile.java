package com.example.game.gamecode.Asteroids;

import android.graphics.Canvas;

public class Projectile extends AsteroidGameObject {
  /** Remaining time until Projectile gets despawned */
  int lifeRemaining = 90;

  @Override
  void move() {
    lifeRemaining--;
    updatePosition();
  }

  @Override
  public void draw(Canvas canvas) {

  }
}
