package com.example.game.gamecode.Asteroids;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.game.gamecode.GameBackend;
import com.example.game.gamecode.GameObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AsteroidGameManager extends GameBackend {
  /** Screen width. */
  private int playAreaWidth;
  /** Screen height. */
  private int playAreaHeight;
  /** number of pixels objects can be outside of screen. */
  private static final int outOfScreenOffset = 5;
  /** number of lives player has left. */
  private int lives = 3;
  /** The player's ship. */
  private Ship player;
  /** The players current score. */
  private int currentScore;
  /** Number of asteroids destroyed. */
  private int asteroidsDestroyed;
  /** Number of projectiles fired by player. */
  private int projectilesFired;

  public AsteroidGameManager(int playAreaWidth, int playAreaHeight) {
    this.playAreaWidth = playAreaWidth;
    this.playAreaHeight = playAreaHeight;
    player =
        new Ship(
            playAreaWidth / 2,
            playAreaHeight / 2,
            0,
            0,
            3 * Math.PI / 2,
            30,
            300,
            8,
            800,
            WeaponFactory.getWeapon(
                AsteroidCustomizations.weaponOption == 0
                    ? WeaponType.STANDARD_CANNON
                    : WeaponType.STANDARD_SHOTGUN));
    gameObjects.add(player);
    int asteroidStartCount = (int) (Math.random() * 3) + 5;
    for (int i = 0; i < asteroidStartCount; i++) {
      Asteroid newAsteroid;
      double newX, newY;
      do {
        newX = Math.random() * playAreaWidth;
        newY = Math.random() * playAreaHeight;
      } while ((newX - playAreaWidth / 2) * (newX - playAreaWidth / 2)
              + (newY - playAreaHeight / 2) * (newY - playAreaHeight / 2)
          <= 300 * 300); // while spawning coordinates are too close to ship
      newAsteroid =
          new Asteroid(
              newX,
              newY,
              Math.random() * 100 + 50,
              Math.random() * 100 + 50,
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
        ((AsteroidGameObject) asteroidGameObject).x = playAreaWidth + outOfScreenOffset;
      } else if (((AsteroidGameObject) asteroidGameObject).x > playAreaWidth + outOfScreenOffset) {
        ((AsteroidGameObject) asteroidGameObject).x = -outOfScreenOffset;
      }
      if (((AsteroidGameObject) asteroidGameObject).y < -outOfScreenOffset) {
        ((AsteroidGameObject) asteroidGameObject).y = playAreaHeight + outOfScreenOffset;
      } else if (((AsteroidGameObject) asteroidGameObject).y > playAreaHeight + outOfScreenOffset) {
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
          first.resolveCollision(second);
          second.resolveCollision(first);
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
          currentScore += ((Asteroid) asteroidGameObject).getValue();
          newObjects.addAll(((Asteroid) asteroidGameObject).split(1));
          asteroidsDestroyed++;
          iter.remove();
        }
      }
    }
    gameObjects.addAll(newObjects);
  }

  private void attemptFire() {
    List<Projectile> projectiles = player.attemptFireMainArmament();
    projectilesFired += projectiles.size();
    gameObjects.addAll(projectiles);
  }

  /** Sets the target direction based on user input. */
  public void setTargetDirection(double targetDirection) {
    player.setTargetAngle(targetDirection);
  }

  /** Sets the thruster state based on user input. */
  public void setThrusterActive(boolean thrusterActive) {
    player.setThrusterActive(thrusterActive);
  }

  /** Sets the firing state based on user input. */
  public void setFireActive(boolean active) {
    player.setMainArmamentActive(active);
  }

  @Override
  public boolean isGameOver() {
    int asteroidCount = 0;
    for (GameObject asteroidGameObject : gameObjects) {
      if (asteroidGameObject instanceof Asteroid) {
        asteroidCount++;
      }
    }
    return lives <= 0 || asteroidCount == 0;
  }

  @Override
  public int getCurrentScore() {
    return currentScore;
  }

  public int getAsteroidsDestroyed() {
    return asteroidsDestroyed;
  }

  public int getProjectilesFired() {
    return projectilesFired;
  }

  public int getLives() {
    return lives;
  }
}
