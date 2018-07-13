/*
Copyright 2016 Tomer Goldstein

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package com.tomergoldst.tooltips;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;

/**
 * Created by Tomer on 01/07/2016.
 */
class ToolTipBackgroundConstructor {

    /**
     * Select which background will be assign to the tip view
     */
    static void setBackground(View tipView, ToolTip toolTip) {

        // show tool tip without arrow. no need to continue
        if (toolTip.hideArrow()) {
            setToolTipNoArrowBackground(tipView, toolTip);
            return;
        }

        // show tool tip according to requested position
        switch (toolTip.getPosition()) {
            case ToolTip.POSITION_ABOVE:
                setToolTipAboveBackground(tipView, toolTip);
                break;
            case ToolTip.POSITION_BELOW:
                setToolTipBelowBackground(tipView, toolTip);
                break;
            case ToolTip.POSITION_LEFT_TO:
                setToolTipLeftToBackground(tipView, toolTip);
                break;
            case ToolTip.POSITION_RIGHT_TO:
                setToolTipRightToBackground(tipView, toolTip);
                break;
        }

    }

    private static void setToolTipAboveBackground(View tipView, ToolTip toolTip) {
        switch (toolTip.getAlign()) {
            case ToolTip.ALIGN_CENTER:
                setTipBackground(tipView, getCorrectResId(tipView, toolTip, R.drawable.tooltip_arrow_down), toolTip.getBackgroundColor());
                break;
            case ToolTip.ALIGN_LEFT:
                setTipBackground(tipView,
                        getCorrectResId(tipView, toolTip, !UiUtils.isRtl() ?
                                R.drawable.tooltip_arrow_down_left :
                                R.drawable.tooltip_arrow_down_right)
                        , toolTip.getBackgroundColor());
                break;
            case ToolTip.ALIGN_RIGHT:
                setTipBackground(tipView,
                        getCorrectResId(tipView, toolTip, !UiUtils.isRtl() ?
                                R.drawable.tooltip_arrow_down_right :
                                R.drawable.tooltip_arrow_down_left)
                        , toolTip.getBackgroundColor());
                break;
        }
    }

    private static void setToolTipBelowBackground(View tipView, ToolTip toolTip) {

        switch (toolTip.getAlign()) {
            case ToolTip.ALIGN_CENTER:
                setTipBackground(tipView, getCorrectResId(tipView, toolTip, R.drawable.tooltip_arrow_up), toolTip.getBackgroundColor());
                break;
            case ToolTip.ALIGN_LEFT:
                setTipBackground(tipView,
                        getCorrectResId(tipView, toolTip, !UiUtils.isRtl() ?
                                R.drawable.tooltip_arrow_up_left :
                                R.drawable.tooltip_arrow_up_right)
                        , toolTip.getBackgroundColor());
                break;
            case ToolTip.ALIGN_RIGHT:
                setTipBackground(tipView,
                        getCorrectResId(tipView, toolTip, !UiUtils.isRtl() ?
                                R.drawable.tooltip_arrow_up_right :
                                R.drawable.tooltip_arrow_up_left)
                        , toolTip.getBackgroundColor());
                break;
        }

    }

    private static void setToolTipLeftToBackground(View tipView, ToolTip toolTip) {
        setTipBackground(tipView, getCorrectResId(tipView, toolTip, !UiUtils.isRtl() ?
                        R.drawable.tooltip_arrow_right : R.drawable.tooltip_arrow_left),
                toolTip.getBackgroundColor());
    }

    private static void setToolTipRightToBackground(View tipView, ToolTip toolTip) {
        setTipBackground(tipView, getCorrectResId(tipView, toolTip, !UiUtils.isRtl() ?
                        R.drawable.tooltip_arrow_left : R.drawable.tooltip_arrow_right),
                toolTip.getBackgroundColor());
    }

    private static void setToolTipNoArrowBackground(View tipView, ToolTip toolTip) {
        setTipBackground(tipView, getCorrectResId(tipView, toolTip, R.drawable.tooltip_no_arrow), toolTip.getBackgroundColor());
    }

    private static void setTipBackground(View tipView, int drawableRes, int color){
        Drawable paintedDrawable = getTintedDrawable(tipView.getContext(),
                drawableRes, color);
        setViewBackground(tipView, paintedDrawable);
    }

    private static void setViewBackground(View view, Drawable drawable){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            view.setBackground(drawable);
        } else {
            view.setBackgroundDrawable(drawable);
        }
    }

    private static Drawable getTintedDrawable(Context context, int drawableRes, int color){
        Drawable drawable;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            drawable = context.getResources().getDrawable(drawableRes, null);
            if (drawable != null) {
                drawable.setTint(color);
            }
        } else {
            drawable = context.getResources().getDrawable(drawableRes);
            if (drawable != null) {
                drawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
            }
        }

        return drawable;
    }

    private static int getCorrectResId(View view, ToolTip toolTip, int resId) {
        if (toolTip.getArrowStyle() == ToolTip.STANDARD_ARROW || view == null || view.getContext() == null) {
            return resId;
        }
        String name = view.getResources().getResourceName(resId);
        if (toolTip.getArrowStyle() == ToolTip.CONVERSATION_ARROW) {
            name+="_conv";
        }
        return view.getResources().getIdentifier(name, "drawable", view.getContext().getPackageName());
    }
}
