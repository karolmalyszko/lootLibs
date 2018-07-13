package io.loot.lootapp.circlechart;

import android.graphics.Path;

interface Renderer {
    Path buildPath(float animationProgress, float animationSeek);
}
