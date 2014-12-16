package com.nav.dexedd.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * GridView with new features.
 *
 * @author Eduardo Naveda
 * @since 0.0.1
 */
public class BetterGridView extends GridView {

    public BetterGridView(Context context) {
        super(context);
    }

    public BetterGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BetterGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
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
}
