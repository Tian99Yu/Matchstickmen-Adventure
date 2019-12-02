package com.group0540.matchstickmenadventures.gamecode.asteroids;

import com.group0540.matchstickmenadventures.gamecode.GameBackend;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AsteroidGameManager extends GameBackend<AsteroidGameObject>
    implements LivesManager, WeaponPowerupUnlocker {
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
  /** Number of powerups collected by player. */
  private int powerupsCollected;

  public AsteroidGameManager(int playAreaWidth, int playAreaHeight) {
    this.playAreaWidth = playAreaWidth;
    this.playAreaHeight = playAreaHeight;
    spawnPlayer();
    spawnAsteroids();
  }

  /** Spawns the player. */
  private void spawnPlayer() {
    player =
        new Ship(
            playAreaWidth / 2,
            playAreaHeight / 2,
            0,
            0,
            3 * Math.PI / 2,
            50,
            300,
            8,
            800,
            ProjectileLauncherFactory.getProjectileLauncher(
                AsteroidCustomizations.weaponOption == 0
                    ? ProjectileLauncherType.STANDARD_CANNON
                    : ProjectileLauncherType.STANDARD_SHOTGUN),
            ProjectileLauncherFactory.getProjectileLauncher(ProjectileLauncherType.POWERUP_CANNON));
    gameObjects.add(player);
  }

  /** Spawns asteroids. */
  private void spawnAsteroids() {
    int asteroidStartCount = (int) (Math.random() * 3) + 3;
    for (int i = 0; i < asteroidStartCount; i++) {
      double newX, newY;
      do {
        newX = Math.random() * playAreaWidth;
        newY = Math.random() * playAreaHeight;
      } while ((newX - playAreaWidth / 2) * (newX - playAreaWidth / 2)
              + (newY - playAreaHeight / 2) * (newY - playAreaHeight / 2)
          <= 300 * 300); // while spawning coordinates are too close to ship
      gameObjects.add(
          new Asteroid(
              newX,
              newY,
              Math.random() * 100 + 50,
              Math.random() * 100 + 50,
              Math.random() * 2 * Math.PI,
              Math.random() * 100 + 50,
              1,
              2));
    }
    int asteroidSpawnerCount = (int) (Math.random() * 2) + 2;
    for (int i = 0; i < asteroidSpawnerCount; i++) {
      double newX, newY;
      do {
        newX = Math.random() * playAreaWidth;
        newY = Math.random() * playAreaHeight;
      } while ((newX - playAreaWidth / 2) * (newX - playAreaWidth / 2)
              + (newY - playAreaHeight / 2) * (newY - playAreaHeight / 2)
          <= 300 * 300);
      gameObjects.add(
          new AsteroidSpawner(
              newX,
              newY,
              0,
              0,
              Math.random() * 2 * Math.PI,
              70,
              150,
              4,
              200,
              3,
              0,
              AsteroidLauncherFactory.getAsteroidLauncher(
                  AsteroidLauncherType.STANDARD_ASTEROID_LAUNCHER),
              player));
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

  /** Updates the positions of asteroid game objects. */
  private void moveAsteroidGameObjects() {
    for (AsteroidGameObject asteroidGameObject : gameObjects) {
      (asteroidGameObject).move();
    }
  }

  /** Handles when objects move outside the play area. */
  private void handleObjectsOutOfScreen() {
    for (AsteroidGameObject asteroidGameObject : gameObjects) {
      if ((asteroidGameObject).x < -outOfScreenOffset) {
        (asteroidGameObject).x = playAreaWidth + outOfScreenOffset;
      } else if ((asteroidGameObject).x > playAreaWidth + outOfScreenOffset) {
        (asteroidGameObject).x = -outOfScreenOffset;
      }
      if ((asteroidGameObject).y < -outOfScreenOffset) {
        (asteroidGameObject).y = playAreaHeight + outOfScreenOffset;
      } else if ((asteroidGameObject).y > playAreaHeight + outOfScreenOffset) {
        (asteroidGameObject).y = -outOfScreenOffset;
      }
    }
  }

  /** Detects and resolves colisions between objects. */
  private void handleCollisions() {
    // collision handling
    for (int i = 0; i < gameObjects.size(); i++) {
      AsteroidGameObject first = gameObjects.get(i);
      for (int j = i + 1; j < gameObjects.size(); j++) {
        AsteroidGameObject second = gameObjects.get(j);
        if (first.isColliding(second)) {
          first.resolveCollision(second);
          second.resolveCollision(first);
        }
      }
    }
  }

  /** Deals with when asteroid game objects are destroyed. */
  private void handleDestruction() {
    List<AsteroidGameObject> newObjects = new ArrayList<>();
    // destruction handling
    Iterator iter = gameObjects.iterator();
    while (iter.hasNext()) {
      AsteroidGameObject asteroidGameObject = (AsteroidGameObject) iter.next();
      if (asteroidGameObject.isDestroyed()) {
        if (asteroidGameObject instanceof Ship) {
          // player died
          decrementLives();
          ((Ship) asteroidGameObject).reset();
        } else if (asteroidGameObject instanceof Projectile) {
          iter.remove();
        } else if (asteroidGameObject instanceof Asteroid) {
          currentScore += ((Asteroid) asteroidGameObject).getValue();
          newObjects.addAll(((Asteroid) asteroidGameObject).split(1, this, this));
          if (asteroidGameObject instanceof PowerupAsteroid) {
            powerupsCollected++;
          }
          asteroidsDestroyed++;
          iter.remove();
        }
      }
    }
    gameObjects.addAll(newObjects);
  }

  /** Adds the recently fired objects into the game. */
  private void attemptFire() {
    gameObjects.addAll(player.attemptFireMainArmament());
    List<Asteroid> newAsteroids = new ArrayList<>();
    for (AsteroidGameObject asteroidGameObject : gameObjects) {
      if (asteroidGameObject instanceof AsteroidSpawner) {
        newAsteroids.addAll(((AsteroidSpawner) asteroidGameObject).attemptSpawnAsteroid());
      }
    }
    gameObjects.addAll(newAsteroids);
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
    player.setWeaponActive(active);
  }

  @Override
  public boolean isGameOver() {
    int asteroidCount = 0;
    for (AsteroidGameObject asteroidGameObject : gameObjects) {
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

  public int getPowerupsCollected() {
    return powerupsCollected;
  }

  public int getLives() {
    return lives;
  }

  @Override
  public void incrementLives() {
    lives++;
  }

  @Override
  public void decrementLives() {
    lives--;
  }

  @Override
  public void unlockPlayerWeaponPowerup() {
    player.setPowerupTime(200);
  }
}
