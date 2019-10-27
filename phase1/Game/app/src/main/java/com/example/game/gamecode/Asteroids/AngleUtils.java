package com.example.game.gamecode.Asteroids;

public class AngleUtils {
  private AngleUtils() {}

  /**
   * Restricts angle to be between 0 and 2 pi
   *
   * @param angle to normalize
   * @return a new angle that is some multiple of 2 pi away from angle but between 0 and 2 pi
   */
  public static double normalize(double angle) {
    return angle - Math.floor(angle / (2 * Math.PI)) * 2 * Math.PI;
  }

  /**
   *Returns the signed difference between two angles between - pi and pi.
   *
   * @param firstAngle
   * @param secondAngle
   * @return the difference between two angles with the magnitude representing the smallest angle
   * between the two angles
   */
  public static double signedAngularDifference(double firstAngle, double secondAngle) {
    return (firstAngle - secondAngle + Math.PI) % (2*Math.PI) - Math.PI;
  }
}
