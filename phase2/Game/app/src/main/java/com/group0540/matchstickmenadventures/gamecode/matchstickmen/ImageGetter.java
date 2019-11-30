package com.group0540.matchstickmenadventures.gamecode.matchstickmen;

interface ImageGetter<K> {
  /**
   * Return the image for this matchstick men type
   *
   * @param type the type of a matchstick men
   * @return the image for this matchstick men type
   */
  K getImage(MatchstickMenType type);
}
