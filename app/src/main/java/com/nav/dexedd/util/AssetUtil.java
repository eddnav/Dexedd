package com.nav.dexedd.util;

import android.content.Context;
import android.graphics.drawable.Drawable;

import java.io.IOException;
import java.io.InputStream;

/**
 * Utility methods for Android based assets.
 *
 * @author Eduardo Naveda
 * @since 0.0.1
 */
public class AssetUtil {

    public static Drawable getDrawableAsset(Context context, String path) {

        // Get input stream
        InputStream inputStream = null;
        try {
            inputStream = context.getAssets().open(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Load image as Drawable
        Drawable drawable = Drawable.createFromStream(inputStream, null);

        return drawable;
    }

}
