package com.nav.dexedd.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Classic Android extension of ScrollView that exposes the protected OnScrollChanged callback and more utility fixes.
 *
 * @author Eduardo Naveda
 * @since 0.0.1
 */
public class BetterScrollView extends ScrollView {

    public BetterScrollView(Context context) {
        super(context);
    }

    public BetterScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BetterScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public interface OnScrollChangedListener {
        void onScrollChanged(ScrollView scrollView, int l, int t, int oldl, int oldt);
    }

    private OnScrollChangedListener onScrollChangedListener;


    public void setOnScrollChangedListener(OnScrollChangedListener listener) {
        onScrollChangedListener = listener;
    }

    /**
     * Fix the over scroll "bounce glitch" by forcing max over scroll values to 0.
     */
    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY,
                                   int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
        return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, 0,
                                  0, isTouchEvent);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (onScrollChangedListener != null) {
            onScrollChangedListener.onScrollChanged(this, l, t, oldl, oldt);
        }
    }

}
