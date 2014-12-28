package com.nav.dexedd.text;

import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

/**
 * A clickable span with overriden draw state update (no underline or color).
 *
 * @author Eduardo Naveda
 * @since 0.0.1
 */
public class CleanClickableSpan extends ClickableSpan{

    @Override
    public void onClick(View widget) {}

    @Override
    public void updateDrawState(TextPaint ds) {
        ds.setUnderlineText(false);
    }
}
