# Tooltips
Simple to use library for android, Enabling to add a tooltip near any view with ease

<img src="https://user-images.githubusercontent.com/99822/38155597-a5e9f4bc-3446-11e8-8db1-4dbd670584f5.gif" width="180" height="320"/> <img src="https://user-images.githubusercontent.com/99822/38155596-a5d9990a-3446-11e8-9b88-5b6ba2f45ac4.gif" width="180" height="320"/>

## What's new
- v 1.0.10 Added several APIs to allow complete customization of the tooltip view.
  * Replaced `setTextColor()` & `setTextSize()` with `setTextAppearance()` API for more control over tool tip view appearance. Also added `setTypeface()` API to allow custom font for tooltips view.
  * Updated `ToolTip.Builder()` API to accept `CharSequence` as tooltip message text to allow broader type of spannable messages.
  * Added `ToolTipsManager.setToolTipAnimator()` API to allow library consumer to customize the tooltip show and hide animation.
- v 1.0.9 fix RTL support for arabic
- v 1.0.8 fix dismiss all method
- v 1.0.7 decrease min sdk to 14
- v 1.0.6 add text size attribute to tooltip builder
- v 1.0.5 remove conflicting application attributes

##### if you are looking for a more robust features check out https://github.com/tomergoldst/hoverview

## Instructions

Add a dependency to your app build.gradle
```groovy
dependencies {
    compile 'com.tomergoldst.android:tooltips:1.0.10'
}
```

Create a `ToolTipsManager` object
```java
ToolTipsManager mToolTipsManager;
mToolTipsManager = new ToolTipsManager();
```
  
Use the `ToolTip.Builder` to construct your tip
```java
ToolTip.Builder builder = new ToolTip.Builder(this, mTextView, mRootLayout, "Tip message", ToolTip.POSITION_ABOVE);
```
`mTextView` here is the view which near it the tip will be shown and `mRootLayout` is the layout where the tip view will be added to.
**The root layout must be** of `RelativeLayout`, `FrameLayout` or similar. `LinearLayout` won't work but you can always wrap your `LinearLayout`
with another layout. Prefer to pass in a layout which is higher in the xml tree as this will give the
tip view more visible space.
 
**OPTIONAL**: Customize your tip with background color, text color, alignment, text gravity, type face and more. 
```java
builder.setAlign(ToolTip.ALIGN_LEFT);
builder.setBackgroundColor(getResources().getColor(R.color.colorOrange));
builder.setGravity(ToolTip.GRAVITY_RIGHT);
builder.setTextAppearance(R.style.TooltipTextAppearance); // from `styles.xml`
builder.setTypeface(mCustomFontTypeface);
```

Here is an example on how you can define your text appearance in your `styles.xml`

```xml
<style name="TooltipTextAppearance">
    <item name="android:textColor">@color/your_custom_color</item>
    <item name="android:textSize">16sp</item>
    <item name="android:textStyle">bold</item>
</style>
```

You can also customize the animation used to show and hide the tooltip view by providing `ToolTipAnimator` implementation and setting it in the `ToolTipsManager`.
```java
mToolTipsManager = new ToolTipsManager(context);
mToolTipsManager.setToolTipAnimator(MyCustomToolTipAnimator());
```

Use `ToolTipManger` to show the tip

**IMPORTANT**: This must be called after the layout has been drawn
You can override the `onWindowFocusChanged()` of an Activity and show there, Start a delayed runnable from `onStart()`, react to user action or any other method that works for you
```java
mToolTipsManager.show(builder.build());
```

Each tip is dismissable by clicking on it, if you want to dismiss a tip from code there are a few options, the most simple way is to do the following
```java
mToolTipsManager.findAndDismiss(mTextView);
```
Where `mTextView` is the same view we asked to position a tip near it

If you want to react when tip has been dismissed, Implement `ToolTipsManager.TipListener` interface and use appropriate `ToolTipsManager` constructor
```java
public class MainActivity extends AppCompatActivity implements ToolTipsManager.TipListener
.
.
@Override
protected void onCreate(Bundle savedInstanceState) {
    mToolTipsManager = new ToolTipsManager(this);
}
.
.
@Override
public void onTipDismissed(View view, int anchorViewId, boolean byUser) {
    Log.d(TAG, "tip near anchor view " + anchorViewId + " dismissed");

    if (anchorViewId == R.id.text_view) {
        // Do something when a tip near view with id "R.id.text_view" has been dismissed
    }
}
```

### License
```
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
```

