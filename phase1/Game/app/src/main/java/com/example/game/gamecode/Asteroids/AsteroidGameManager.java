package com.example.game.gamecode.Asteroids;

import android.content.res.Resources;

import com.example.game.gamecode.GameBackend;
import com.example.game.gamecode.GameObject;

public class AsteroidGameManager extends GameBackend {
  /** Screen width. */
  private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
  /** Screen height. */
  private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
  /** number of lives player has left. */
  private int lives = 3;
  /** The player's ship. */
  private Ship player;

  public AsteroidGameManager() {
    WeaponFactory weaponFactory = new WeaponFactory();
    player =
        new Ship(
            screenWidth / 2,
            screenHeight / 2,
            0,
            0,
            Math.PI / 2,
            10,
            weaponFactory.getWeapon(WeaponType.DEFAULT_CANNON));
    gameObjects.add(player);
  }

  @Override
  public void update() {
    for (GameObject asteroidGameObject : gameObjects) {
      ((AsteroidGameObject) asteroidGameObject).move();
    }
    // TODO implement game mechanics
  }

  /** Sets the target direction based on user input. */
  public void setTargetDirection(double targetDirection) {
    player.setTargetDirection(targetDirection);
  }

  /** Sets the thruster state based on user input. */
  public void setThrusterActive(boolean thrusterActive) {
    player.setThrusterActive(thrusterActive);
  }

  /** Sets the firing state based on user input. */
  public void setFireActive(boolean active) {
    player.setMainArmamentActive(active);
  }
}
