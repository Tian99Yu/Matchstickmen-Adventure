package com.example.game.gamecode.Asteroids;

import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.List;

class Asteroid extends AsteroidGameObject {
  /** Number of hitpoints this asteroid has remaining. */
  private int hp;
  /** The level of the asteroid, higher means larger. Asteroids of level 0 will not divide. */
  private int level;

  Asteroid(
      double x,
      double y,
      double vX,
      double vY,
      double angle,
      double collisionRadius,
      int hp,
      int level) {
    super(x, y, vX, vY, angle, collisionRadius);
    this.hp = hp;
    this.level = level;
  }

  /** Reduces hp by damage amount. */
  void hit(int damage) {
    hp -= damage;
  }

  /** Returns smaller asteroids upon destruction. */
  List<Asteroid> split(int newHp) {
    List<Asteroid> smallerAsteroids = new ArrayList<>();
    if (isDestroyed() && level > 0) {
      smallerAsteroids.add(
          new Asteroid(
              x + (int) ((int) (Math.random() * collisionRadius) - collisionRadius / 2),
              y + (int) ((int) (Math.random() * collisionRadius) - collisionRadius / 2),
              vX + 2 * Math.random() - 1,
              vY + 2 * Math.random() - 1,
              Math.random() * 2 * Math.PI,
              collisionRadius / 3,
              newHp,
              level - 1));
    }
    return smallerAsteroids;
  }

  @Override
  boolean isDestroyed() {
    return hp <= 0;
  }

  @Override
  public void draw(Canvas canvas) {}
}
