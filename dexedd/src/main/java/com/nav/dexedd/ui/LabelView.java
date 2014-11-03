package com.nav.dexedd.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.nav.dexedd.R;

/**
 * Main text label views with full size underline.
 *
 * @author Eduardo Naveda
 * @since 0.0.1
 */
public class LabelView extends RelativeLayout {

    private TextView label;
    private FrameLayout underline;

    public LabelView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray styledAttributes = context.getTheme().obtainStyledAttributes(attrs, R.styleable.LabelView, 0, 0);

        String text = "";

        try {
            text = styledAttributes.getString(R.styleable.LabelView_text);
        }
        finally {
            styledAttributes.recycle();
        }

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.label_content, this, true);

        label = (TextView) getChildAt(0);
        underline = (FrameLayout) getChildAt(1);
        label.setText(text);
    }

    public void setText(String text) {
        label.setText(text);
    }
}
