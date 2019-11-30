package com.example.game.gamecode.Asteroids;

class ColoredSprite<T> {
  private T sprite;
  private int color;

  ColoredSprite(T sprite, int color) {
    this.sprite = sprite;
    this.color = color;
  }

  T getSprite() {
    return sprite;
  }

  int getColor() {
    return color;
  }
}
