package com.nav.dexedd.app;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.*;
import android.widget.GridView;

import com.nav.dexedd.R;
import com.nav.dexedd.adapter.DexAdapter;
import com.nav.dexedd.model.Pokemon;
import com.nav.dexedd.persistence.access.Dex;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;

import java.util.List;

/**
 * Main Dexedd! activity.
 *
 * @author Eduardo Naveda
 * @since 0.0.1
 */
public class MainActivity extends ActionBarActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    private static final String TAG = MainActivity.class.getSimpleName();

    private static final String DEX_FRAGMENT = "dex_fragment";
    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment navigationDrawerFragment;

    /**
     * Main dex fragment.
     */
    private Fragment dexFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            dexFragment = getSupportFragmentManager().findFragmentByTag(DEX_FRAGMENT);
        }

        setContentView(R.layout.activity_main);

        Toolbar dexToolBar = (Toolbar) findViewById(R.id.dexedd_tool_bar);
        setSupportActionBar(dexToolBar);

        navigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager()
                .findFragmentById(R.id.navigation_drawer);
        title = getTitle();

        navigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(title);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!navigationDrawerFragment.isDrawerOpen()) {
            getMenuInflater().inflate(R.menu.dex, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        switch (position) {
            case 0:
                if (dexFragment == null) {
                    dexFragment = DexFragment.newInstance(position);
                }
                fragmentManager.beginTransaction().replace(R.id.container, dexFragment, DEX_FRAGMENT).commit();
                break;
            case 1:
                // Todo change to About fragment
                fragmentManager.beginTransaction().replace(R.id.container, PlaceholderFragment.newInstance(position))
                        .commit();
                break;
        }
    }

    @Override
    public void onSectionAttached(int number) {
        switch (number) {
            case 0:
                title = getString(R.string.title_dex);
                break;
            case 1:
                title = getString(R.string.title_about);
                break;
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";

        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_about, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((NavigationDrawerFragment.NavigationDrawerCallbacks) activity)
                    .onSectionAttached(getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

    /**
     * Main dex fragment.
     */
    public static class DexFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";

        private static final String GRID_POSITION = "grid_position";

        private GridView dexGrid;
        private List<Pokemon> pokemon;

        public static DexFragment newInstance(int sectionNumber) {
            DexFragment fragment = new DexFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public DexFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_dex, container, false);

            // Get full cell size
            int dexCellWidth = getResources().getDimensionPixelSize(R.dimen.dex_cell_width);
            int dexCellSpacing = getResources().getDimensionPixelSize(R.dimen.dex_cell_spacing);

            // Get display width
            DisplayMetrics displaymetrics = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
            int width = displaymetrics.widthPixels;

            // Calculate number of columns relative to screen size
            dexGrid = (GridView) rootView.findViewById(R.id.dex_grid);
            int numColumns = (int) Math.floor(width / (dexCellWidth + dexCellSpacing));

            Dex dex = Dex.create(getActivity().getApplicationContext());
            pokemon = dex.listPokemon(); // Todo add dummies to list to fill remaining spaces pokemon.size()/numColums -

            DexAdapter adapter = DexAdapter.createDexAdapter(getActivity(), R.layout.dex_cell, pokemon);

            // Set number of columns and appropriate column size
            dexGrid.setNumColumns(numColumns);
            dexGrid.setColumnWidth(width / numColumns);
            dexGrid.setAdapter(adapter);

            //Set scroll pause listeners
            PauseOnScrollListener pauseOnScrollListener = new PauseOnScrollListener(ImageLoader.getInstance(), false,
                                                                                    true);
            dexGrid.setOnScrollListener(pauseOnScrollListener);

            return rootView;
        }

        @Override
        public void onSaveInstanceState(Bundle outState) {
            super.onSaveInstanceState(outState);
            outState.putInt(GRID_POSITION, dexGrid.getFirstVisiblePosition());
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            if (savedInstanceState != null) {
                final Integer gridPosition = savedInstanceState.getInt(GRID_POSITION);
                dexGrid.setSelection(gridPosition);
            }
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((NavigationDrawerFragment.NavigationDrawerCallbacks) activity)
                    .onSectionAttached(getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}