package me.philio.pinentry;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

public class EditTextCentered extends AppCompatTextView {

    private final Paint mPaint = new Paint();

    private final Rect mBounds = new Rect();

    public EditTextCentered(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EditTextCentered(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        String text = getText().toString();
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(getTextSize());
        mPaint.setColor(getCurrentTextColor());
        drawCenter(canvas, mPaint, text);
    }

    private void drawCenter(Canvas canvas, Paint paint, String text) {
        canvas.getClipBounds(mBounds);
        int cHeight = mBounds.height();
        int cWidth = mBounds.width();
        paint.setTextAlign(Paint.Align.LEFT);
        paint.getTextBounds(text, 0, text.length(), mBounds);
        float x = cWidth / 2f - mBounds.width() / 2f - mBounds.left;
        float y = cHeight / 2f + mBounds.height() / 2f - mBounds.bottom;
        canvas.drawText(text, x, y, paint);
    }
}
