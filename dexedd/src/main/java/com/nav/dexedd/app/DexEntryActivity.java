package com.nav.dexedd.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.*;
import android.widget.ImageView;
import android.widget.TextView;

import com.nav.dexedd.R;
import com.nav.dexedd.component.ui.TypeTagView;
import com.nav.dexedd.model.Pokemon;
import com.nav.dexedd.model.Type;
import com.nav.dexedd.persistence.access.DexEntry;
import com.nav.dexedd.util.DexStringUtil;

import java.lang.reflect.Field;

/**
 * Dex entry activity, manages fragments that show entry information.
 *
 * @author Eduardo Naveda
 * @since 0.0.1
 */
public class DexEntryActivity extends ActionBarActivity {

    private static final String TAG = DexEntryActivity.class.getSimpleName();

    public static final String POKEMON_ID = "pokemon_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dex_entry);

        Toolbar dexToolBar = (Toolbar) findViewById(R.id.dex_toolbar);
        setSupportActionBar(dexToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();

        if (savedInstanceState == null) {
            DexEntryFragment dexEntryFragment = new DexEntryFragment();
            Bundle args = new Bundle();
            if (extras != null) {
                Integer entryId = extras.getInt(POKEMON_ID);
                args.putInt(POKEMON_ID, entryId);

            }
            dexEntryFragment.setArguments(args);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, dexEntryFragment)
                    .commit();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dex_entry, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        } else {
            if (id == R.id.action_settings) {
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    /**
     * Fragment containing a Dex entry.
     */
    public static class DexEntryFragment extends Fragment {

        private ImageView dexEntryPicture;
        private TextView dexEntryName;
        private TextView dexEntryGenus;
        private TextView dexEntryFlavorText;
        private TypeTagView dexEntryPrimaryType;
        private TypeTagView dexEntrySecondaryType;

        public DexEntryFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.fragment_dex_entry, container, false);
            dexEntryPicture = (ImageView) rootView.findViewById(R.id.dex_entry_picture);
            dexEntryName = (TextView) rootView.findViewById(R.id.dex_entry_name);
            dexEntryGenus = (TextView) rootView.findViewById(R.id.dex_entry_genus);
            dexEntryFlavorText = (TextView) rootView.findViewById(R.id.dex_entry_flavor_text);
            dexEntryPrimaryType = (TypeTagView) rootView.findViewById(R.id.primary_type);
            dexEntrySecondaryType = (TypeTagView) rootView.findViewById(R.id.secondary_type);

            if (getArguments() != null) {

                DexEntry dexEntry = DexEntry.create(getActivity().getApplicationContext(),
                                                    getArguments().getInt(DexEntryActivity.POKEMON_ID));
                Pokemon pokemon = dexEntry.getPokemon();

                final String dexNumber = DexStringUtil.getFormattedDexNumber(pokemon.getDexNumber());
                final String name = pokemon.getName();
                final String genus = pokemon.getGenus();
                final String flavorText = pokemon.getFlavorText();
                final Type primaryType = pokemon.getPrimaryType();
                final Type secondaryType = pokemon.getSecondaryType();

                ((ActionBarActivity) getActivity()).getSupportActionBar().setTitle(dexNumber);

                try {
                    Class res = R.drawable.class;
                    Field field = res.getField("b" + dexNumber.substring(1, dexNumber.length()));
                    int drawableId = field.getInt(null);
                    dexEntryPicture.setImageDrawable(getResources().getDrawable(drawableId));
                }
                catch (IllegalAccessException | NoSuchFieldException e) {
                    dexEntryPicture.setImageDrawable(getResources().getDrawable(R.drawable.pokeball));
                }

                dexEntryPrimaryType.setType(TypeTagView.Type.getTypeByValue(primaryType.getId()));

                if (secondaryType != null) {
                    dexEntrySecondaryType.setType(TypeTagView.Type.getTypeByValue(secondaryType.getId()));
                } else {
                    dexEntrySecondaryType.setType(TypeTagView.Type.NONE);
                }

                dexEntryName.setText(name);
                dexEntryGenus.setText(genus + " " + getResources().getString(R.string.pokemon));
                dexEntryFlavorText.setText(flavorText);

                return rootView;
            } else {
                return rootView;
            }
        }
    }
}
