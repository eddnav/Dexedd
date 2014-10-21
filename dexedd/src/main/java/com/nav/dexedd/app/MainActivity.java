package com.nav.dexedd.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.*;
import android.widget.*;

import com.nav.dexedd.R;
import com.nav.dexedd.component.ui.TypeTagView;
import com.nav.dexedd.model.Pokemon;
import com.nav.dexedd.model.Type;
import com.nav.dexedd.persistence.access.Dex;
import com.nav.dexedd.util.DexStringUtil;
import com.nav.dexedd.util.TypeUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;

import java.lang.reflect.Field;
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

        Toolbar dexToolBar = (Toolbar) findViewById(R.id.dex_toolbar);
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
     * Main Dex fragment.
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

            // Get full face grid cell size
            int dexGridCellWidth = getResources().getDimensionPixelSize(R.dimen.dex_grid_cell_width);
            int dexGridCellSpacing = getResources().getDimensionPixelSize(R.dimen.dex_grid_cell_spacing);

            // Get display width
            DisplayMetrics displaymetrics = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
            int width = displaymetrics.widthPixels;

            // Calculate number of columns relative to screen size
            dexGrid = (GridView) rootView.findViewById(R.id.dex_grid);
            int numColumns = (int) Math.floor(width / (dexGridCellWidth + dexGridCellSpacing));

            Dex dex = Dex.create(getActivity().getApplicationContext());
            pokemon = dex.listPokemon(); // Todo add dummies to list to fill remaining spaces pokemon.size()/numColums -

            DexAdapter adapter = new DexAdapter(getActivity().getApplicationContext(), R.layout.dex_grid_cell, pokemon);

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
        public void onActivityCreated(@Nullable Bundle savedInstanceState) {
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

        @Override
        public void onDestroy() {
            super.onDestroy();
        }

        /**
         * Adapter for the main Dex view.
         */
        public class DexAdapter extends ArrayAdapter<Pokemon> {

            private DisplayImageOptions displayImageOptions = new DisplayImageOptions.Builder()
                    .showImageOnLoading(R.drawable.pokeball).showImageForEmptyUri(R.drawable.pokeball)
                    .showImageOnFail(R.drawable.pokeball).cacheInMemory(true)
                    .displayer(new FadeInBitmapDisplayer(400, true, false, false)).build();

            private int resource;

            public DexAdapter(Context context, int resource, List<Pokemon> pokemon) {
                super(context, resource, pokemon);
                this.resource = resource;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                final Pokemon pokemon = getItem(position);

                View row = convertView;

                if (row == null) {
                    Holder holder = new Holder();
                    row = LayoutInflater.from(getContext()).inflate(resource, parent, false);
                    holder.primaryType = (TypeTagView) row.findViewById(R.id.primary_type);
                    holder.secondaryType = (TypeTagView) row.findViewById(R.id.secondary_type);
                    holder.image = (ImageView) row.findViewById(R.id.image);
                    holder.panel = (RelativeLayout) row.findViewById(R.id.panel);
                    holder.dexNumber = (TextView) row.findViewById(R.id.dexNumber);
                    holder.name = (TextView) row.findViewById(R.id.name);
                    holder.catchButton = (ImageButton) row.findViewById(R.id.catch_button);
                    row.setTag(holder);
                }

                Holder holder = (Holder) row.getTag();
                final Integer id = pokemon.getId();
                final Type primaryType = pokemon.getPrimaryType();
                final Type secondaryType = pokemon.getSecondaryType();
                final String dexNumber = DexStringUtil.getFormattedDexNumber(pokemon.getDexNumber());
                final String name = pokemon.getName();
                final Boolean catched = pokemon.getCatched();

                View.OnClickListener toEntry = new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getActivity(), DexEntryActivity.class);
                        intent.putExtra(DexEntryActivity.POKEMON_ID, id);
                        intent.putExtra(DexEntryActivity.DEX_ENTRY_TYPE, primaryType.getId());
                        startActivity(intent);
                    }
                };

                holder.primaryType.setType(TypeUtil.Type.getTypeByValue(primaryType.getId()));

                if (secondaryType != null) {
                    holder.secondaryType.setType(TypeUtil.Type.getTypeByValue(secondaryType.getId()));
                } else {
                    holder.secondaryType.setType(TypeUtil.Type.NONE);
                }

                try {
                    Class res = R.drawable.class;
                    Field field = res.getField("b" + dexNumber.substring(1, dexNumber.length()));
                    int drawableId = field.getInt(null);
                    ImageLoader.getInstance()
                            .displayImage("drawable://" + drawableId, holder.image, displayImageOptions);
                } catch (IllegalAccessException | NoSuchFieldException e) {
                    ImageLoader.getInstance()
                            .displayImage("drawable://" + R.drawable.pokeball, holder.image, displayImageOptions);
                }

                holder.image.setOnClickListener(toEntry);
                holder.panel.setOnClickListener(toEntry);

                holder.name.setText(name);
                holder.dexNumber.setText(dexNumber);

                if (catched) {
                    holder.catchButton.setBackgroundResource(R.drawable.ic_catched);
                } else {
                    holder.catchButton.setBackgroundResource(R.drawable.ic_uncatched);
                }

                holder.catchButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (pokemon.getCatched()) {
                            Dex.setCatched(getContext().getApplicationContext(), id, false);
                            pokemon.setCatched(false);
                            view.setBackgroundResource(R.drawable.ic_uncatched);
                        } else {
                            Dex.setCatched(getContext().getApplicationContext(), id, true);
                            pokemon.setCatched(true);
                            view.setBackgroundResource(R.drawable.ic_catched);
                        }
                    }
                });

                return row;
            }

            private class Holder {
                TypeTagView primaryType;
                TypeTagView secondaryType;
                ImageView image;
                RelativeLayout panel;
                TextView name;
                TextView dexNumber;
                ImageButton catchButton;
            }
        }
    }
}