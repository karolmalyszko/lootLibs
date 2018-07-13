package io.loot.lootapp.circlechart;

import android.graphics.Paint;


public class ChartValue {

    private final float value;
    private final int color;
    private Paint paint;
    private float startAngle;
    private float sweepAngle;

    public ChartValue(float value, int color) {
        this.value = value;
        this.color = color;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
        this.paint.setColor(color);
    }

    public float getValue() {
        return value;
    }

    public int getColor() {
        return color;
    }

    public Paint getPaint() {
        return paint;
    }

    public float getStartAngle() {
        return startAngle;
    }

    public void setStartAngle(float startAngle) {
        this.startAngle = startAngle;
    }

    public float getSweepAngle() {
        return sweepAngle;
    }

    public void setSweepAngle(float sweepAngle) {
        this.sweepAngle = sweepAngle;
    }
}
