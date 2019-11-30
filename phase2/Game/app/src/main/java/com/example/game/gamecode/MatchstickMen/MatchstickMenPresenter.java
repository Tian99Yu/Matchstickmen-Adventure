package com.example.game.gamecode.MatchstickMen;


import com.example.game.gamecode.GameBackend;
import com.example.game.gamecode.GamePresenter;


public class MatchstickMenPresenter<T, S> extends GamePresenter<T, MatchstickMenObject> {

  /** The MatchstickMenDrawer that handles drawing for the matchstick men game. */
  private MatchstickMenDrawer<T, S> matchstickMenDrawer;

  /** Whether the game backend is initialized */
  private boolean initialized = false;

  /** The customizations of this matchstick men game */
  private MatchstickMenCustomization matchstickMenCustomization;

  /**
   * Get the image to be drawn
   */
  private ImageGetter<S> imageGetter;

  /**
   * Constructor for matchstick men presenter
   *
   * @param matchstickMenDrawer the surface to be drawn on
   * @param backend the game backend of the game to be presented.
   * @param imageGetter the getter of the image to be drawn.
   */
  MatchstickMenPresenter(MatchstickMenDrawer<T, S> matchstickMenDrawer, GameBackend<MatchstickMenObject> backend, ImageGetter<S> imageGetter) {
    super(backend);
    this.matchstickMenDrawer = matchstickMenDrawer;
    this.imageGetter = imageGetter;
  }

    /**
     * Update and refresh the game status.
     */
    void update() {
        if (initialized) {
            backend.update();
        } else {
            int height = matchstickMenDrawer.getHeight();
            int width = matchstickMenDrawer.getWidth();

      MatchstickMenBackend matchstickMenBackend = (MatchstickMenBackend) backend;
      matchstickMenBackend.setGridHeight(height);
      matchstickMenBackend.setGridWidth(width);

      matchstickMenBackend.createObjects();

      this.initialized = true;
    }
  }

  /**
   * Draw the man objects on the drawing surface.
   *
   * @param manObject the man object that is going to be drawn
   * @param drawingSurface the surface to be drawn in
   */
  private void drawMatchstickMenObject(MatchstickMenObject manObject, T drawingSurface) {
    S image;
    if (manObject instanceof ExtraordinaryManObject){
      image = imageGetter.getImage(MatchstickMenType.BALD_MAN);
    } else {
      image = imageGetter.getImage(matchstickMenCustomization.getCharacter());
    }
    matchstickMenDrawer.drawImage(drawingSurface, image, manObject.x, manObject.y);
  }

  /**
   * Draw the game onto the drawing surface.
   *
   * @param drawingSurface the surface to be drawn on.
   */
  @Override
  public void draw(T drawingSurface) {
    matchstickMenDrawer.drawBackground(drawingSurface);

    for (MatchstickMenObject matchstickMenObject : backend) {
      if (matchstickMenObject != null) {
        drawMatchstickMenObject(matchstickMenObject, drawingSurface);
      }
    }
  }

  /**
   * Set the customization of the game to matchstick men customization
   *
   * @param matchstickMenCustomization the customization object for this game.
   */
  void setMatchstickMenCustomization(MatchstickMenCustomization matchstickMenCustomization) {
    this.matchstickMenCustomization = matchstickMenCustomization;
  }
}
