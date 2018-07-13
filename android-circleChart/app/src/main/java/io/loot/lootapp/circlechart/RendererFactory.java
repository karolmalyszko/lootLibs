package io.loot.lootapp.circlechart;

import android.graphics.RectF;

public class RendererFactory {

    public static Renderer getRenderer(AnimationMode mode, ChartValue value, RectF drawingArea) {
        if (mode == AnimationMode.LINEAR) {
            return new LinearValueRenderer(drawingArea, value);
        } else {
            return new OverdrawValueRenderer(drawingArea, value);
        }
    }


}
