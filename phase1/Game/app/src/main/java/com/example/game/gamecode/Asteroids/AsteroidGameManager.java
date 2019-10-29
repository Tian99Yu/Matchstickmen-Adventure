package com.example.game.gamecode.Asteroids;

import android.content.res.Resources;
import android.util.Log;

import com.example.game.gamecode.GameBackend;
import com.example.game.gamecode.GameObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AsteroidGameManager extends GameBackend {
  /** Screen width. */
  private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
  /** Screen height. */
  private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
  /** number of pixels objects can be outside of screen. */
  private static final int outOfScreenOffset = 5;
  /** number of lives player has left. */
  private int lives = 3;
  /** The player's ship. */
  private Ship player;

  public AsteroidGameManager() {
    player =
        new Ship(
            screenWidth / 2,
            screenHeight / 2,
            0,
            0,
            3 * Math.PI / 2,
            30,
            WeaponFactory.getWeapon(WeaponType.DEFAULT_CANNON));
    gameObjects.add(player);
    int asteroidStartCount = (int) (Math.random() * 3) + 5;
    for (int i = 0; i < asteroidStartCount; i++) {
      Asteroid newAsteroid;
      double newX, newY;
      do {
        newX = Math.random() * screenWidth;
        newY = Math.random() * screenHeight;
      } while ((newX - screenWidth / 2) * (newX - screenWidth / 2)
              + (newY - screenHeight / 2) * (newY - screenHeight / 2)
          <= 300 * 300); // while spawning coordinates are too close to ship
      newAsteroid =
          new Asteroid(
              newX,
              newY,
              Math.random() * 3 + 1,
              Math.random() * 3 + 1,
              Math.random() * 2 * Math.PI,
              Math.random() * 100 + 50,
              1,
              2);
      gameObjects.add(newAsteroid);
    }
  }

  @Override
  public void update() {
    moveAsteroidGameObjects();
    handleObjectsOutOfScreen();
    handleCollisions();
    handleDestruction();
    attemptFire();
  }

  private void moveAsteroidGameObjects() {
    for (GameObject asteroidGameObject : gameObjects) {
      ((AsteroidGameObject) asteroidGameObject).move();
    }
  }

  private void handleObjectsOutOfScreen() {
    for (GameObject asteroidGameObject : gameObjects) {
      if (((AsteroidGameObject) asteroidGameObject).x < -outOfScreenOffset) {
        ((AsteroidGameObject) asteroidGameObject).x = screenWidth + outOfScreenOffset;
      } else if (((AsteroidGameObject) asteroidGameObject).x > screenWidth + outOfScreenOffset) {
        ((AsteroidGameObject) asteroidGameObject).x = -outOfScreenOffset;
      }
      if (((AsteroidGameObject) asteroidGameObject).y < -outOfScreenOffset) {
        ((AsteroidGameObject) asteroidGameObject).y = screenHeight + outOfScreenOffset;
      } else if (((AsteroidGameObject) asteroidGameObject).y > screenHeight + outOfScreenOffset) {
        ((AsteroidGameObject) asteroidGameObject).y = -outOfScreenOffset;
      }
    }
  }

  private void handleCollisions() {
    // collision handling
    for (int i = 0; i < gameObjects.size(); i++) {
      AsteroidGameObject first = (AsteroidGameObject) gameObjects.get(i);
      for (int j = i + 1; j < gameObjects.size(); j++) {
        AsteroidGameObject second = (AsteroidGameObject) gameObjects.get(j);
        if (first.isColliding(second)) {
          CollisionHandler.handle(first, second);
        }
      }
    }
  }

  private void handleDestruction() {
    List<AsteroidGameObject> newObjects = new ArrayList<>();
    // destruction handling
    Iterator iter = gameObjects.iterator();
    while (iter.hasNext()) {
      AsteroidGameObject asteroidGameObject = (AsteroidGameObject) iter.next();
      if (asteroidGameObject.isDestroyed()) {
        if (asteroidGameObject instanceof Ship) {
          // player died
          lives--;
          ((Ship) asteroidGameObject).reset();
        } else if (asteroidGameObject instanceof Projectile) {
          iter.remove();
        } else if (asteroidGameObject instanceof Asteroid) {
          newObjects.addAll(((Asteroid) asteroidGameObject).split(1));
          iter.remove();
        }
      }
    }
    gameObjects.addAll(newObjects);
  }

  private void attemptFire() {
    gameObjects.addAll(player.attemptFireMainArmament());
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
