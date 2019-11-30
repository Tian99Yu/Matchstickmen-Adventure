package com.example.game.gamecode.MatchstickMen;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.game.R;

import java.util.HashMap;

public class BitmapGetter implements ImageGetter<Bitmap> {
  /** The indicator of what image to be drawn for matchstick men */
  private HashMap<MatchstickMenType, Bitmap> imageIdicator;

  /**
   * Create a bitmap getter
   *
   * @param context the context this bitmap getter is in.
   */
  BitmapGetter(Context context) {
    this.imageIdicator = new HashMap<>();
    imageIdicator.put(
        MatchstickMenType.BALD_MAN,
        BitmapFactory.decodeResource(context.getResources(), R.drawable.baldman));
    imageIdicator.put(
        MatchstickMenType.HAPPY_MAN,
        BitmapFactory.decodeResource(context.getResources(), R.drawable.happyman));
    imageIdicator.put(
        MatchstickMenType.EXCITED_MAN,
        BitmapFactory.decodeResource(context.getResources(), R.drawable.excitedman));
  }

  /**
   * Return the image for this matchstick men type
   *
   * @param type the type of a matchstick men
   * @return the image for this matchstick men type
   */
  @Override
  public Bitmap getImage(MatchstickMenType type) {
    return imageIdicator.get(type);
  }
}
