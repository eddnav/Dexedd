package com.nav.dexedd.app;

import android.app.Application;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * Application class.
 *
 * @author Eduardo Naveda
 * @since 0.0.1
 */
public class DexApp extends Application {

    private static final String TAG = DexApp.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext()).build();
        ImageLoader.getInstance().init(config);
    }
}
