package com.rengwuxian.materialedittext;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntDef;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.text.emoji.EmojiCompat;
import android.support.text.emoji.widget.EditTextAttributeHelper;
import android.support.text.emoji.widget.EmojiAppCompatEditText;
import android.support.text.emoji.widget.EmojiEditTextHelper;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.TransformationMethod;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ActionMode;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;

import com.nineoldandroids.animation.ArgbEvaluator;
import com.nineoldandroids.animation.ObjectAnimator;
import com.rengwuxian.materialedittext.validation.METLengthChecker;
import com.rengwuxian.materialedittext.validation.METValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * EditText in Material Design
 * <p/>
 * author:rengwuxian
 * <p/>
 *
 * enhanced with emoji capability by using {@link EmojiEditTextHelper}.
 * When used on devices running API 18 or below, this widget acts as a regular
 * {@link AppCompatEditText}.
 *
 * @attr ref android.support.text.emoji.R.styleable#EmojiEditText_maxEmojiCount
 */

public class MaterialEmojiEditText extends MaterialEditText {

    private EmojiEditTextHelper mEmojiEditTextHelper;

    /**
     * Prevent calling {@link #init(AttributeSet, int)} multiple times in case super() constructors
     * call other constructors.
     */
    private boolean mInitialized;

    public MaterialEmojiEditText(Context context) {
        super(context);
        init(null /*attrs*/, 0 /*defStyleAttr*/);
    }

    public MaterialEmojiEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, android.support.v7.appcompat.R.attr.editTextStyle);
    }

    public MaterialEmojiEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr);
    }

    @SuppressLint("RestrictedApi")
    private void init(@Nullable AttributeSet attrs, int defStyleAttr) {
        if (!mInitialized) {
            mInitialized = true;
            final EditTextAttributeHelper attrHelper = new EditTextAttributeHelper(this, attrs,
                    defStyleAttr, 0);
            setMaxEmojiCount(attrHelper.getMaxEmojiCount());
            setKeyListener(super.getKeyListener());
        }
    }

    @Override
    public void setKeyListener(android.text.method.KeyListener input) {
        super.setKeyListener(getEmojiEditTextHelper().getKeyListener(input));
    }

    @Override
    public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
        InputConnection inputConnection = super.onCreateInputConnection(outAttrs);
        return getEmojiEditTextHelper().onCreateInputConnection(inputConnection, outAttrs);
    }

    /**
     * Set the maximum number of EmojiSpans to be added to a CharSequence. The number of spans in a
     * CharSequence affects the performance of the EditText insert/delete operations. Insert/delete
     * operations slow down as the number of spans increases.
     *
     * @param maxEmojiCount maximum number of EmojiSpans to be added to a single CharSequence,
     *                      should be equal or greater than 0
     *
     * @see EmojiCompat#process(CharSequence, int, int, int)
     *
     * @attr ref android.support.text.emoji.R.styleable#EmojiEditText_maxEmojiCount
     */
    public void setMaxEmojiCount(@IntRange(from = 0) int maxEmojiCount) {
        getEmojiEditTextHelper().setMaxEmojiCount(maxEmojiCount);
    }

    /**
     * Returns the maximum number of EmojiSpans to be added to a CharSequence.
     *
     * @see #setMaxEmojiCount(int)
     * @see EmojiCompat#process(CharSequence, int, int, int)
     *
     * @attr ref android.support.text.emoji.R.styleable#EmojiEditText_maxEmojiCount
     */
    public int getMaxEmojiCount() {
        return getEmojiEditTextHelper().getMaxEmojiCount();
    }

    private EmojiEditTextHelper getEmojiEditTextHelper() {
        if (mEmojiEditTextHelper == null) {
            mEmojiEditTextHelper = new EmojiEditTextHelper(this);
        }
        return mEmojiEditTextHelper;
    }
}