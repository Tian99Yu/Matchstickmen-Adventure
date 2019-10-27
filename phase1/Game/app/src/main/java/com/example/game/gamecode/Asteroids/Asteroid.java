package com.example.game.gamecode.Asteroids;

import android.graphics.Canvas;

public class Asteroid extends AsteroidGameObject {
  /** Number of hitpoints this asteroid has. */
  int hp;

  public Asteroid(double x, double y, double vX, double vY, double angle, double collisionRadius, int hp) {
    super(x, y, vX, vY, angle, collisionRadius);
    this.hp = hp;
  }

  @Override
  public void draw(Canvas canvas) {

  }
}
