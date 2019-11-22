package com.example.game.gamecode.Asteroids;

public class ColoredSprite<T> {
  private T sprite;
  private int color;

  public ColoredSprite(T sprite, int color) {
    this.sprite = sprite;
    this.color = color;
  }

  public T getSprite() {
    return sprite;
  }

  public int getColor() {
    return color;
  }
}
