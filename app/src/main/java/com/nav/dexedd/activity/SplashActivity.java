package com.nav.dexedd.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import com.nav.dexedd.R;
import com.nav.dexedd.fragment.DatabaseInitFragment;

/**
 * Splash activity, it just loads the database for the first time so an initialization is simulated, and thus
 * the user can see it visually.
 *
 * @author Eduardo Naveda
 * @since 0.0.1
 */
public class SplashActivity extends ActionBarActivity implements DatabaseInitFragment.DatabaseInitCallbackable {

    private static final String TAG = SplashActivity.class.getSimpleName();

    private static final String DATABASE_INIT_FRAGMENT = "database_init_fragment";

    private DatabaseInitFragment databaseInitFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        FragmentManager fragmentManager = getSupportFragmentManager();
        databaseInitFragment = (DatabaseInitFragment) fragmentManager.findFragmentByTag(DATABASE_INIT_FRAGMENT);

        if (databaseInitFragment == null) {
            databaseInitFragment = new DatabaseInitFragment();
            fragmentManager.beginTransaction().add(databaseInitFragment, DATABASE_INIT_FRAGMENT).commit();
        }
    }


    @Override
    public void onPostExecute() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
