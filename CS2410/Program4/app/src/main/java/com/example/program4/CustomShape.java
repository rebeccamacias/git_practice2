package com.example.program4;

import android.graphics.Canvas;
import android.graphics.Paint;

public abstract class CustomShape {
    protected float x;
    protected float y;

    public abstract void draw(Canvas canvas, Paint paint);
}
