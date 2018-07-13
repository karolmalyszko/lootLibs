package io.loot.lootapp.circlechart;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

public class Chart extends View {

    static final int ANIMATION_MODE_LINEAR = 0;
    static final int ANIMATION_MODE_OVERDRAW = 1;
    static final int DEFAULT_VIEW_RADIUS = 0;
    static final int DEFAULT_MIN_VALUE = 0;
    static final int DEFAULT_MAX_VALUE = 100;
    static final int START_ANGLE = -90;
    static final int ANIMATION_DURATION = 2500;
    static final float INITIAL_ANIMATION_PROGRESS = 0.0f;
    static final float MAXIMUM_SWEEP_ANGLE = 360f;
    static final int DESIGN_MODE_SWEEP_ANGLE = 216;
    private RectF drawingArea;
    private Paint backStrokePaint;
    private Paint valueDesignPaint;
    private int backStrokeColor;
    private int valueStrokeColor;
    private float strokeSize;
    private float minValue = DEFAULT_MIN_VALUE;
    private float maxValue = DEFAULT_MAX_VALUE;
    private ChartValue chartValue;
    private ChartValue backgroundValue;
    private float animationProgress = INITIAL_ANIMATION_PROGRESS;
    private float backgroundAnimationProgress = INITIAL_ANIMATION_PROGRESS;
    private float maxSweepAngle = MAXIMUM_SWEEP_ANGLE;
    private AnimationMode animationMode = AnimationMode.LINEAR;
    private int PADDING = 70;
    private int BLUR = 3;
    private int fontSize = 20;
    private Context context;
    private boolean mIsAnimationInProgress;
    private AnimatorSet animatorSet;

    public Chart(Context context) {
        super(context);
        this.context = context;
        initializeView(null);
    }

    public Chart(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initializeView(attrs);
    }

    public Chart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initializeView(attrs);
    }

    public float getValue() {
        if (chartValue != null) {
            return chartValue.getValue();
        }
        return 0f;
    }

    public float getMinValue() {
        return minValue;
    }

    public void setMinValue(float value) {
        minValue = value;
    }

    public float getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(float value) {
        maxValue = value;
    }

    public void setValue(float value, boolean shouldAnimate) {
        chartValue = new ChartValue(value, valueStrokeColor);
        chartValue.setPaint(buildPaintForValue());
        chartValue.getPaint().setMaskFilter(new BlurMaskFilter(BLUR, BlurMaskFilter.Blur.SOLID));
        chartValue.setStartAngle(START_ANGLE);
        chartValue.setSweepAngle(calculateSweepAngle(value));
        maxSweepAngle = chartValue.getSweepAngle();
        backgroundValue = new ChartValue(value, backStrokeColor);
        backgroundValue.setPaint(buildPaintForValue());
        backgroundValue.setStartAngle(START_ANGLE);
        backgroundValue.setSweepAngle(calculateSweepAngle(100));

        if (shouldAnimate) {
            backgroundAnimationProgress = 1;
            playAnimation();
        } else {
            animationProgress = 1;
            backgroundAnimationProgress = 1;
            if (animatorSet != null) {
                if (value != 0) {
                    animatorSet.end();
                }
            }
            invalidate();
        }
    }

    public void changeValue(float value) {
        float oldValue = 0f;

        if (chartValue != null) {
            oldValue = chartValue.getValue();
            if (value == chartValue.getValue()) {
                return;
            }
        }

        if (mIsAnimationInProgress) {
            animatorSet.cancel();
        }

        chartValue = new ChartValue(value, valueStrokeColor);

        chartValue.setPaint(buildPaintForValue());
        chartValue.getPaint().setMaskFilter(new BlurMaskFilter(BLUR, BlurMaskFilter.Blur.SOLID));
        chartValue.setStartAngle(START_ANGLE);

        float sweepAngle = (value == 0f) ? calculateSweepAngle(1f) : calculateSweepAngle(value);
        chartValue.setSweepAngle(sweepAngle);

        maxSweepAngle = chartValue.getSweepAngle();

        backgroundValue = new ChartValue(value, backStrokeColor);

        backgroundValue.setPaint(buildPaintForValue());
        backgroundValue.setStartAngle(START_ANGLE);
        backgroundValue.setSweepAngle(calculateSweepAngle(100));
        backgroundAnimationProgress = 100;

        if (oldValue != 0f) {
            playReverseAnimation(oldValue, value);
        } else {
            playAnimation();
        }
    }

    public void setAnimationMode(AnimationMode mode) {
        this.animationMode = mode;
    }

    void setAnimationSeek(float value) {
        animationProgress = value;
        if (backgroundAnimationProgress < 100) {
            backgroundAnimationProgress = 1.5f * value;
        }
        invalidate();
    }

    private Paint buildPaintForValue() {
        Paint paint = getPaint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(strokeSize);
        paint.setStrokeCap(Paint.Cap.ROUND);
        return paint;
    }

    private void initializeView(AttributeSet attrs) {
        prepareVariables();
        initializeBackground();
        readAttributes(attrs);
        preparePaints();
    }

    private void prepareVariables() {
        PADDING = dpToPx(PADDING);
        BLUR = dpToPx(BLUR);
    }

    public void setFontSizeInPixels(float size) {
        fontSize = (int) size;
    }

    private void initializeBackground() {
        if (!isInEditMode()) {
            if (getBackground() == null) {
                setBackgroundColor(getContext().getResources().getColor(R.color.default_back_color));
            }
        }
    }

    private void calculateDrawableArea() {
        float drawPadding = (strokeSize / 2) + PADDING;
        float width = getWidth();
        float height = getHeight();
        float left = drawPadding;
        float top = drawPadding;
        float right = width - drawPadding;
        float bottom = height - drawPadding;
        drawingArea = new RectF(left, top, right, bottom);
    }

    private void readAttributes(AttributeSet attrs) {
        Resources resources = getContext().getResources();
        valueStrokeColor = ContextCompat.getColor(getContext(), R.color.default_chart_value_color);
        backStrokeColor = ContextCompat.getColor(getContext(), R.color.default_back_stroke_color);
        strokeSize = resources.getDimension(R.dimen.default_stroke_size);
        if (attrs != null) {
            TypedArray attributes = getContext()
                    .getTheme().obtainStyledAttributes(attrs, R.styleable.Chart, 0, 0);
            strokeSize = attributes
                    .getDimensionPixelSize(R.styleable.Chart_strokeSize, (int) strokeSize);
            valueStrokeColor = attributes
                    .getColor(R.styleable.Chart_valueStrokeColor, valueStrokeColor);
            backStrokeColor = attributes
                    .getColor(R.styleable.Chart_backStrokeColor, backStrokeColor);
            int attrAnimationMode = attributes.getInteger(R.styleable.Chart_animationMode, ANIMATION_MODE_LINEAR);
            if (attrAnimationMode == ANIMATION_MODE_LINEAR) {
                animationMode = AnimationMode.LINEAR;
            } else {
                animationMode = AnimationMode.OVERDRAW;
            }
            attributes.recycle();
        }
    }

    private void preparePaints() {

        backStrokePaint = getPaint();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            setLayerType(LAYER_TYPE_SOFTWARE, backStrokePaint);
        }
        backStrokePaint.setColor(backStrokeColor);
        backStrokePaint.setStyle(Paint.Style.STROKE);
        backStrokePaint.setStrokeCap(Paint.Cap.ROUND);
        backStrokePaint.setStrokeWidth(strokeSize);
        backStrokePaint.setAntiAlias(true);
        valueDesignPaint = getPaint();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            setLayerType(LAYER_TYPE_SOFTWARE, valueDesignPaint);
        }
        valueDesignPaint.setColor(valueStrokeColor);
        valueDesignPaint.setStyle(Paint.Style.STROKE);
        valueDesignPaint.setStrokeCap(Paint.Cap.ROUND);
        valueDesignPaint.setStrokeWidth(strokeSize);
        valueDesignPaint.setAntiAlias(true);
    }

    private Paint getPaint() {
        if (!isInEditMode()) {
            return new Paint(Paint.ANTI_ALIAS_FLAG);
        } else {
            return new Paint();
        }
    }

    private float getViewRadius() {
        if (drawingArea != null) {
            return (drawingArea.width() / 2);
        } else {
            return DEFAULT_VIEW_RADIUS;
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        calculateDrawableArea();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        int size = Math.max(width, height);
        setMeasuredDimension(size, size);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        renderBack(canvas);
        renderValues(canvas);
    }

    private void renderBack(Canvas canvas) {
        float backgroundAnimationSeek = calculateBackgroundAnimationSeek();
        Renderer renderer = RendererFactory.getRenderer(animationMode, backgroundValue, drawingArea);

        Path backgroundPath = renderer.buildPath(backgroundAnimationProgress, backgroundAnimationSeek);
        if (backgroundPath != null) {
            canvas.drawPath(backgroundPath, backgroundValue.getPaint());
        }
    }

    private void renderValues(Canvas canvas) {
        if (!isInEditMode()) {
            renderValue(canvas, chartValue);
        } else {
            renderValue(canvas, null);
        }
    }

    private void renderValue(Canvas canvas, ChartValue value) {
        if (!isInEditMode()) {
            float animationSeek = calculateAnimationSeek();
            Renderer renderer = RendererFactory.getRenderer(animationMode, value, drawingArea);
            Path path = renderer.buildPath(animationProgress, animationSeek);
            if (path != null) {
                canvas.drawPath(path, value.getPaint());
                drawPercentageText(value, canvas);
            }
        } else {
            Path path = new Path();
            path.addArc(drawingArea, START_ANGLE, DESIGN_MODE_SWEEP_ANGLE);
            canvas.drawPath(path, valueDesignPaint);
        }
    }


    private void drawPercentageText(ChartValue value, Canvas canvas) {
        Paint paint = new Paint();
        paint.setTextSize(fontSize);
        paint.setAntiAlias(true);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setColor(valueStrokeColor);

        float valueSweepAngle = (value.getStartAngle() + value.getSweepAngle());
        valueSweepAngle -= value.getStartAngle();
        float sweepAngle = valueSweepAngle * animationProgress;

        double x = Math.cos((sweepAngle + value.getStartAngle()) * (Math.PI / 180)) * ((drawingArea.width() + (PADDING / 1.5f) + (fontSize / 3)) / 2) + getViewRadius() + PADDING;
        double y = Math.sin((sweepAngle + value.getStartAngle()) * (Math.PI / 180)) * ((drawingArea.height() + (PADDING / 1.5f) + (fontSize / 3)) / 2) + getViewRadius() + PADDING;
        double percentage = sweepAngle * 100 / 360;

        String text = String.format("%.0f", percentage);
        canvas.drawText(String.format("%.0f", percentage), (float) x, (float) y + (PADDING / 7), paint);
        drawPecentageTag(canvas, paint, text, x, y);
    }

    private void drawPecentageTag(Canvas canvas, Paint textPaint, String text, double x, double y) {
        Paint paint = new Paint();
        paint.setTextSize(fontSize * 2 / 3);
        paint.setAntiAlias(true);
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setColor(valueStrokeColor);

        Rect bounds = new Rect();
        textPaint.getTextBounds(text, 0, text.length(), bounds);
        canvas.drawText("%", (float) x + (bounds.width() * 4 / 7), (float) y + (PADDING / 7), paint);
    }

    private float calculateAnimationSeek() {
        return ((maxSweepAngle * animationProgress) + START_ANGLE);
    }

    private float calculateBackgroundAnimationSeek() {
        return ((maxSweepAngle * backgroundAnimationProgress) + START_ANGLE);
    }

    private float calculateSweepAngle(float value) {
        float chartValuesWindow = Math.max(minValue, maxValue) - Math.min(minValue, maxValue);
        float chartValuesScale = (360f / chartValuesWindow);
        return (value * chartValuesScale);
    }

    private void playAnimation() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(this, "animationSeek", 0.0f, 1.0f);
        animatorSet = new AnimatorSet();
        animatorSet.setDuration(ANIMATION_DURATION);
        animatorSet.setInterpolator(new DecelerateInterpolator());
        animatorSet.setTarget(this);
        animatorSet.play(animator);
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                mIsAnimationInProgress = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mIsAnimationInProgress = false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                mIsAnimationInProgress = false;
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animatorSet.start();
    }

    private void playReverseAnimation(float oldValue, float newValue) {
        float nominator = animationProgress * oldValue;

        boolean wasValueZero = false;
        if (newValue == 0f) {
            newValue = 1f;
            wasValueZero = true;
        }

        float startValue = (nominator / newValue);

        ObjectAnimator animator = ObjectAnimator.ofFloat(this, "animationSeek", startValue, 1f);

        animatorSet = new AnimatorSet();
        animatorSet.setDuration(ANIMATION_DURATION);
        animatorSet.setInterpolator(new DecelerateInterpolator());
        animatorSet.setTarget(this);
        animatorSet.play(animator);

        final boolean finalWasValueZero = wasValueZero;
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                mIsAnimationInProgress = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mIsAnimationInProgress = false;
                if (finalWasValueZero) {
                    setValue(0f, false);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                mIsAnimationInProgress = false;
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        animatorSet.start();
    }

    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public boolean getIsAnimating() {
        return mIsAnimationInProgress;
    }

}
