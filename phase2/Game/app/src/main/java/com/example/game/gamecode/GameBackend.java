package com.example.game.gamecode;

import android.graphics.Canvas;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Iterator;

public abstract class GameBackend<T> implements Iterable<T> {
  protected ArrayList<T> gameObjects = new ArrayList<>();

  public abstract void update();
  /**
   * Returns true iff the game is over.
   *
   * @return whether or not the game is over.
   */
  public abstract boolean isGameOver();

  /**
   * Returns the current score the player has earned in the current game.
   *
   * @return the players current score.
   */
  public abstract int getCurrentScore();


  @NonNull
  @Override
  public Iterator<T> iterator() {
    return gameObjects.iterator();
  }
}
