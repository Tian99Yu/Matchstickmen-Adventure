package com.example.game.gamecode.Asteroids;

import android.graphics.Canvas;

public class Ship extends AsteroidGameObject {
  /** Fixed amount of acceleration ship has when thruster is on.*/
  final static double thrust = 1;
  /** Maximum velocity ship can travel. */
  final static double maxVelocity = 1;
  /** Whether or not the thruster is on. */
  boolean thrusterActive = false;


  public boolean isThrusterActive() {
    return thrusterActive;
  }

  public void setThrusterActive(boolean thrusterActive) {
    this.thrusterActive = thrusterActive;
  }

  @Override
  void move() {
    if (thrusterActive) {
      double aX = thrust*Math.cos(angle);
      double aY = thrust*Math.sin(angle);
      double newVX = vX + aX*dt;
      double newVY = vY + aY*dt;
      if (newVX*newVX + newVY*newVY <= maxVelocity*maxVelocity) {
        vX = newVX;
        vY = newVY;
      }
    }
    updatePosition();
  }

  @Override
  public void draw(Canvas canvas) {

  }
}
