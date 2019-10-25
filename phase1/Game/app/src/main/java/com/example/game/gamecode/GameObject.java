package com.example.game.gamecode;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

abstract class GameObject extends Drawable {
    public abstract void draw(Canvas canvas);

}
