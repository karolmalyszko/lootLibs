package pl.com.bitcraft.pinView;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class PinView extends RelativeLayout {

    private static final int ANIMATION_DURATION = 300;
    private static final float TEXT_SIZE = 20;

    private int mLastLength = 0;
    private ArrayList<TextView> mTextViews = new ArrayList<>();
    private ArrayList<ImageView> mDots = new ArrayList<>();
    private PinViewCompleteListener mListener;
    private PinLockView mPinLockView;
    ScaleAnimation mFadeIn;

    public void setPinViewCompleteListener(PinViewCompleteListener listener) {
        this.mListener = listener;
    }

    private PinLockListener mPinLockListener = new PinLockListener() {
        @Override
        public void onComplete(String pin) {
            mTextViews.get(mTextViews.size() - 1).setText(pin.substring(pin.length() - 1));
            animateEnterText(mTextViews.get(mTextViews.size() - 1), mDots.get(mDots.size() - 1));
            if (mListener != null) {
                mListener.onComplete(pin);
            }
        }

        @Override
        public void onEmpty() {
            setDotsVisibility(0, false);
            clearTextViews(0);
            mLastLength = 0;
        }

        @Override
        public void onPinChange(int pinLength, String intermediatePin) {
            if (mLastLength < pinLength) {
                mTextViews.get(pinLength - 1).setText(intermediatePin.substring(pinLength - 1));
                clearTextViews(pinLength);
                animateEnterText(mTextViews.get(pinLength - 1), mDots.get(pinLength - 1));
            } else {
                clearTextViews(pinLength);
                setDotsVisibility(pinLength, false);
            }
            mLastLength = pinLength;
        }
    };

    private void clear() {
        mPinLockListener.onEmpty();
        mPinLockView.resetPinLockView();
    }

    public PinView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.pin_view, this, true);
        init();
    }

    private void animateEnterText(final TextView textView, final ImageView dotIv) {
        final float startSize = 0; // Size in pixels
        final float endSize = TEXT_SIZE;

        ValueAnimator animator = ValueAnimator.ofFloat(startSize, endSize);
        animator.setDuration(ANIMATION_DURATION);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float animatedValue = (float) valueAnimator.getAnimatedValue();
                textView.setTextSize(animatedValue);
            }
        });

        animator.start();
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                animateDecrease(textView, dotIv);

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    private void animateDecrease(final TextView textView, final ImageView dotIv) {
        final float startSize = TEXT_SIZE; // Size in pixels
        final float endSize = 0;

        ValueAnimator animator = ValueAnimator.ofFloat(startSize, endSize);
        animator.setDuration(ANIMATION_DURATION / 2);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float animatedValue = (float) valueAnimator.getAnimatedValue();
                textView.setTextSize(animatedValue);
            }
        });
        animator.start();
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                animateScale(dotIv);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    private void animateScale(ImageView dotIv) {
        dotIv.setVisibility(View.VISIBLE);
        ScaleAnimation fadeIn = new ScaleAnimation(0f, 1f, 0f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        fadeIn.setDuration(ANIMATION_DURATION / 2);     // animation duration in milliseconds
        fadeIn.setFillAfter(false);    // If fillAfter is true, the transformation that this animation performed will persist when it is finished.
        dotIv.startAnimation(fadeIn);
        if (dotIv.equals(mDots.get(mDots.size()-1))) {
            fadeIn.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    clear();
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        }
    }


    private void init() {
        TextView t1 = (TextView) findViewById(R.id.pin_lock_t1);
        TextView t2 = (TextView) findViewById(R.id.pin_lock_t2);
        TextView t3 = (TextView) findViewById(R.id.pin_lock_t3);
        TextView t4 = (TextView) findViewById(R.id.pin_lock_t4);
        TextView t5 = (TextView) findViewById(R.id.pin_lock_t5);

        mTextViews.add(t1);
        mTextViews.add(t2);
        mTextViews.add(t3);
        mTextViews.add(t4);
        mTextViews.add(t5);

        ImageView dot1 = (ImageView) findViewById(R.id.pin_lock_dot1);
        ImageView dot2 = (ImageView) findViewById(R.id.pin_lock_dot2);
        ImageView dot3 = (ImageView) findViewById(R.id.pin_lock_dot3);
        ImageView dot4 = (ImageView) findViewById(R.id.pin_lock_dot4);
        ImageView dot5 = (ImageView) findViewById(R.id.pin_lock_dot5);

        mDots.add(dot1);
        mDots.add(dot2);
        mDots.add(dot3);
        mDots.add(dot4);
        mDots.add(dot5);

        initPinView();

    }

    private void initPinView() {
        mPinLockView = (PinLockView) findViewById(R.id.pin_lock_view);
        mPinLockView.setPinLockListener(mPinLockListener);
        mPinLockView.setPinLength(5);
        mPinLockView.resetPinLockView();
        mPinLockView.setTextColor(getResources().getColor(R.color.white));
    }

    public void setPinViewColor(int color, Drawable drawable) {
        View divider = findViewById(R.id.pin_lock_divider);
        divider.setBackgroundColor(color);
        mPinLockView.setTextColor(color);
        for (TextView textView : mTextViews) {
                textView.setTextColor(color);
        }
        for (ImageView imageView : mDots) {
            imageView.setImageDrawable(drawable);
        }
    }

    private void postInvalidateTextViews(int index) {
        for (int i = index; i < mDots.size(); i++) {
            mTextViews.get(i).postInvalidate();
        }
    }

    private void postInvalidateDots(int index) {
        for (int i = index; i < mDots.size(); i++) {
            mDots.get(i).postInvalidate();
        }
    }

    private void setDotsVisibility(int index, boolean visible) {
        for (int i = index; i < mDots.size(); i++) {
            mDots.get(i).setVisibility(visible ? View.VISIBLE : View.GONE);
        }
        postInvalidateDots(index);
    }

    private void clearTextViews(int index) {
        for (int i = index; i < mDots.size(); i++) {
            mTextViews.get(i).setText("");
        }
        postInvalidateTextViews(index);
    }


}
