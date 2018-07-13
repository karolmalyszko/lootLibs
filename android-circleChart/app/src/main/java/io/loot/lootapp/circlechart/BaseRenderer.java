package io.loot.lootapp.circlechart;

import android.graphics.RectF;

abstract class BaseRenderer {
    private final RectF drawingArea;
    private final ChartValue value;

    ChartValue getValue() {
        return value;
    }

    RectF getDrawingArea() {
        return drawingArea;
    }

    public BaseRenderer(RectF drawingArea, ChartValue value) {
        this.drawingArea = drawingArea;
        this.value = value;
    }
}
