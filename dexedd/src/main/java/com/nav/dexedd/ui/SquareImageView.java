package com.nav.dexedd.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * An ImageView which retains a square aspect ratio.
 *
 * @author Eduardo Naveda
 * @since 0.0.1
 */
public class SquareImageView extends ImageView {

    public SquareImageView(Context context) {
        super(context);
    }

    public SquareImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth());
    }
}