package com.mycoaching.mycoaching.Util.Miscellaneous;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.style.LineBackgroundSpan;

/**
 * Created by kevin on 14/06/2018.
 * Version 1.0
 */


/**
 * This class is used in order to display dots on a Material Calendar View
 */
public class CustomMultipleDotSpan implements LineBackgroundSpan {

    private final float radius;
    private int[] color;

    public CustomMultipleDotSpan(float radius, int[] color) {
        this.radius = radius;
        this.color = color;
    }

    @Override
    public void drawBackground(Canvas canvas, Paint paint, int left, int right, int top,
                               int baseline, int bottom, CharSequence charSequence, int start,
                               int end, int lineNum) {

        int total = color.length > 5 ? 5 : color.length;
        int leftMost = (total - 1) * -10;

        for (int i = 0; i < total; i++) {
            int oldColor = paint.getColor();
            if (color[i] != 0) {
                paint.setColor(color[i]);
            }
            canvas.drawCircle((left + right) / 2 - leftMost, bottom + radius, radius, paint);
            paint.setColor(oldColor);
            leftMost = leftMost + 20;
        }
    }
}