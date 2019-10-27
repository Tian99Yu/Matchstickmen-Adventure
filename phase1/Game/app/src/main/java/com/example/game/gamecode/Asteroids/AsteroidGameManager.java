package com.example.game.gamecode.Asteroids;

import com.example.game.gamecode.GameBackend;
import com.example.game.gamecode.GameObject;

public class AsteroidGameManager extends GameBackend {

  @Override
  public void update() {
    for (GameObject asteroidGameObject : gameObjects) {
      ((AsteroidGameObject) asteroidGameObject).move();
    }
    //TODO implement game mechanics
  }

  /** Sets the target direction based on user input. */
  public void setTargetDirection(double angle) {

  }

  /** Sets the thruster state based on user input. */
  public void setThrusterActive(boolean active) {

  }

  /** Sets the firing state based on user input. */
  public void setFireActive(boolean active) {

  }
}
