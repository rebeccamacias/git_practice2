package com.example.program4;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Line extends CustomShape {
    private int x2;
    private int y2;
    private int x;
    private int y;

    public Line(int x, int y, int x2, int y2) {
        this.x = x;
        this.y = y;
        this.x2 = x2;
        this.y2 = y2;
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        canvas.drawLine(x, y, x2, y2, paint);
    }

    public int getX2() {
        return this.x2;
    }

    public int getY2() {
        return this.y2;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

}
