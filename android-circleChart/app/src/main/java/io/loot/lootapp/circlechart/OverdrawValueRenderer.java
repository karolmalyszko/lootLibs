package io.loot.lootapp.circlechart;

import android.graphics.Path;
import android.graphics.RectF;

public class OverdrawValueRenderer extends BaseRenderer implements Renderer {

    public OverdrawValueRenderer(RectF drawignArea, ChartValue value) {
        super(drawignArea, value);
    }

    public Path buildPath(float animationProgress, float animationSeek) {
        float startAngle = Chart.START_ANGLE;
        float valueSweepAngle = (getValue().getStartAngle() +  getValue().getSweepAngle());
        valueSweepAngle -= startAngle;
        float sweepAngle = valueSweepAngle * animationProgress;
        Path path = new Path();
        path.addArc(getDrawingArea(), startAngle, sweepAngle);
        return path;
    }
}
