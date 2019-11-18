package com.example.game.gamecode.Asteroids;

class AngleUtils {
  private AngleUtils() {}

  /**
   * Restricts angle to be between 0 and 2 pi
   *
   * @param angle to normalize
   * @return a new angle that is some multiple of 2 pi away from angle but between 0 and 2 pi
   */
  static double normalize(double angle) {
    return angle - Math.floor(angle / (2 * Math.PI)) * 2 * Math.PI;
  }

  /**
   * Returns the signed difference between two angles between - pi and pi.
   *
   * @param firstAngle first angle to consider
   * @param secondAngle second angle to consider
   * @return the difference between two angles with the magnitude representing the smallest angle
   *     between the two angles
   */
  static double signedAngularDifference(double firstAngle, double secondAngle) {
    double clockwiseDifference = clockwiseAngularDiffrence(firstAngle, secondAngle);
    double counterclockwiseDifference = clockwiseAngularDiffrence(secondAngle, firstAngle);
    if (clockwiseDifference < counterclockwiseDifference) {
      return clockwiseDifference;
    } else {
      return -counterclockwiseDifference;
    }
  }

  /**
   * Returns the angle between the firstAngle and secondAngle in the clockwise direction from the
   * secondAngle.
   *
   * @param firstAngle first angle to consider
   * @param secondAngle second angle to consider
   * @return the clockwise angular difference from the secondAngle to firstAngle
   */
  static double clockwiseAngularDiffrence(double firstAngle, double secondAngle) {
    double clockwiseDifference = firstAngle - secondAngle;
    if (clockwiseDifference < 0) {
      clockwiseDifference += 2 * Math.PI;
    }
    return clockwiseDifference;
  }
}
