package io.loot.lootapp.circlechart;

import android.graphics.Path;
import android.graphics.RectF;

class LinearValueRenderer extends BaseRenderer implements Renderer {
    public LinearValueRenderer(RectF drawingArea, ChartValue value) {
        super(drawingArea, value);
    }

    @Override
    public Path buildPath(float animationProgress, float animationSeek) {
        Path path = null;
        if (getValue().getStartAngle() <= animationSeek) {
            path = new Path();
            float sweepAngle = calculateSweepAngle(animationSeek, getValue());
            path.addArc(getDrawingArea(), getValue().getStartAngle(), sweepAngle);
        }
        return path;
    }

    private float calculateSweepAngle(float animationSeek, ChartValue value) {
        float result;
        float totalSizeOfValue = value.getStartAngle() + value.getSweepAngle();
        if (totalSizeOfValue > animationSeek) {
            result = animationSeek - value.getStartAngle();
        } else {
            result = value.getSweepAngle();
        }
        return result;
    }
}
