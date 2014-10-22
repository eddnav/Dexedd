package com.nav.dexedd.component.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

public class NotifyingScrollView extends ScrollView {

    public interface OnScrollChangedListener {
        void onScrollChanged(ScrollView scrollView, int l, int t, int oldl, int oldt);
    }

    private OnScrollChangedListener onScrollChangedListener;

    public NotifyingScrollView(Context context) {
        super(context);
    }

    public NotifyingScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NotifyingScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setOnScrollChangedListener(OnScrollChangedListener listener) {
        onScrollChangedListener = listener;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (onScrollChangedListener != null) {
            onScrollChangedListener.onScrollChanged(this, l, t, oldl, oldt);
        }
    }

}
