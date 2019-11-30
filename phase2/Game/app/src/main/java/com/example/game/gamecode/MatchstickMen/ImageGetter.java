package com.example.game.gamecode.MatchstickMen;

interface ImageGetter<K> {
  /**
   * Return the image for this matchstick men type
   *
   * @param type the type of a matchstick men
   * @return the image for this matchstick men type
   */
  K getImage(MatchstickMenType type);
}
