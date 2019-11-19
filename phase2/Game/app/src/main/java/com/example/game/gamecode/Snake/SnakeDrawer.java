package com.example.game.gamecode.Snake;

interface SnakeDrawer<T> {
  void drawRect(T drawingSurface, float left, float top, float right, float bottom, int color);
}
