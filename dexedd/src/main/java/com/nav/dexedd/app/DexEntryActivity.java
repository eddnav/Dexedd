package com.nav.dexedd.app;

import android.app.AlertDialog;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.*;
import android.widget.*;

import com.nav.dexedd.R;
import com.nav.dexedd.model.Ability;
import com.nav.dexedd.ui.NotifyingScrollView;
import com.nav.dexedd.ui.TypeTagView;
import com.nav.dexedd.model.Pokemon;
import com.nav.dexedd.model.Type;
import com.nav.dexedd.persistence.access.DexEntry;
import com.nav.dexedd.util.ConversionUtil;
import com.nav.dexedd.util.PokemonTextUtil;
import com.nav.dexedd.util.TypeUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Dex entry activity, manages fragments that show entry information.
 *
 * @author Eduardo Naveda
 * @since 0.0.1
 */
public class DexEntryActivity extends ActionBarActivity {

    private static final String TAG = DexEntryActivity.class.getSimpleName();

    public static final String DEX_ENTRY_TYPE_ID = "dex_entry_type_id";
    public static final String POKEMON_ID = "pokemon_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle extras = getIntent().getExtras();
        TypeUtil.Type dexEntryType = null;
        int pokemonId = 0;
        if (extras != null) {
            dexEntryType = TypeUtil.Type.getTypeByValue(extras.getInt(DEX_ENTRY_TYPE_ID));
            pokemonId = extras.getInt(POKEMON_ID);
        }
        // Set the proper theme for this Pokémon's type
        setTheme(TypeUtil.getTypeStyleRes(dexEntryType));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dex_entry);

        Toolbar dexToolBar = (Toolbar) findViewById(R.id.dex_toolbar);
        setSupportActionBar(dexToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            DexEntryFragment dexEntryFragment = new DexEntryFragment();
            Bundle args = new Bundle();
            args.putInt(POKEMON_ID, pokemonId);
            dexEntryFragment.setArguments(args);
            getSupportFragmentManager().beginTransaction().add(R.id.container, dexEntryFragment).commit();
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
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Fragment containing a dex entry.
     */
    public static class DexEntryFragment extends Fragment {

        private static final String TAG = DexEntryFragment.class.getSimpleName();

        private FrameLayout dexEntryHead;
        private ImageView dexEntryImage;
        private FrameLayout dexEntryImageProxy;
        private NotifyingScrollView dexEntryScroller;
        private TextView dexEntryName;
        private TextView dexEntryGenus;
        private TextView dexEntryFlavorText;
        private TypeTagView dexEntryPrimaryType;
        private TypeTagView dexEntrySecondaryType;
        private LinearLayout dexEntryAbilitiesContent;
        private TextView dexEntryHeightMeters;
        private TextView dexEntryHeightFeetInches;
        private TextView dexEntryWeightKilograms;
        private TextView dexEntryWeightPounds;

        /**
         * Alpha level for the toolbar drawable
         */
        private int toolBarDrawableAlpha = 0;

        public DexEntryFragment() {
        }
        
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        @Override
        public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            final ActionBar toolBar = ((ActionBarActivity) getActivity()).getSupportActionBar();

            View rootView = inflater.inflate(R.layout.fragment_dex_entry, container, false);

            dexEntryHead = (FrameLayout) rootView.findViewById(R.id.dex_entry_head);
            dexEntryImage = (ImageView) rootView.findViewById(R.id.dex_entry_image);
            dexEntryImageProxy = (FrameLayout) rootView.findViewById(R.id.dex_entry_image_proxy);
            dexEntryScroller = (NotifyingScrollView) rootView.findViewById(R.id.dex_entry_scroller);
            dexEntryName = (TextView) rootView.findViewById(R.id.dex_entry_name);
            dexEntryGenus = (TextView) rootView.findViewById(R.id.dex_entry_genus);
            dexEntryFlavorText = (TextView) rootView.findViewById(R.id.dex_entry_flavor_text);
            dexEntryPrimaryType = (TypeTagView) rootView.findViewById(R.id.dex_entry_primary_type);
            dexEntrySecondaryType = (TypeTagView) rootView.findViewById(R.id.dex_entry_secondary_type);
            dexEntryAbilitiesContent = (LinearLayout) rootView.findViewById(R.id.dex_entry_abilities_content);
            dexEntryHeightMeters = (TextView) rootView.findViewById(R.id.dex_entry_height_meters);
            dexEntryHeightFeetInches = (TextView) rootView.findViewById(R.id.dex_entry_height_feet_inches);
            dexEntryWeightKilograms = (TextView) rootView.findViewById(R.id.dex_entry_weight_kilograms);
            dexEntryWeightPounds = (TextView) rootView.findViewById(R.id.dex_entry_weight_pounds);

            // Always set the scrolling to the top when creating a new view for this fragment
            dexEntryScroller.post(new Runnable() {
                public void run() {
                    dexEntryScroller.scrollTo(0, 0);
                }
            });

            if (getArguments() != null) {

                final DexEntry dexEntry = DexEntry.create(getActivity().getApplicationContext(),
                                                          getArguments().getInt(DexEntryActivity.POKEMON_ID));
                Pokemon pokemon = dexEntry.getPokemon();

                final String dexNumber = PokemonTextUtil.getFormattedDexNumber(pokemon.getDexNumber());
                final String name = pokemon.getName();
                final String genus = pokemon.getGenus();
                final String flavorText = pokemon.getFlavorText();
                final Type primaryType = pokemon.getPrimaryType();
                final Type secondaryType = pokemon.getSecondaryType();
                final List<Ability> abilities = pokemon.getAbilities();

                //  Setting up the toolbar
                final ColorDrawable toolBarTypeColorDrawable =
                        new ColorDrawable(getResources().getColor(
                                TypeUtil.getTypeColorRes(TypeUtil.Type.getTypeByValue(primaryType.getId()))));

                toolBarTypeColorDrawable.setAlpha(toolBarDrawableAlpha);

                toolBar.setBackgroundDrawable(toolBarTypeColorDrawable);

                // Drawable callback for the toolbar drawable so it can register itself to the
                // toolbar on each invalidation, this is only necessary for API level =< 17
                Drawable.Callback drawableCallback = new Drawable.Callback() {
                    @Override
                    public void invalidateDrawable(Drawable drawable) {
                        toolBar.setBackgroundDrawable(drawable);
                    }

                    @Override
                    public void scheduleDrawable(Drawable who, Runnable what, long when) {
                    }

                    @Override
                    public void unscheduleDrawable(Drawable who, Runnable what) {
                    }
                };

                // Registering the callback
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    toolBarTypeColorDrawable.setCallback(drawableCallback);
                }

                FrameLayout.LayoutParams dexEntryImageLayoutParams = (FrameLayout.LayoutParams) dexEntryImage
                        .getLayoutParams();

                // Normal and max image bottom margins for the main dex entry image these are used to create
                // some sort of parallax scrolling
                final int dexEntryImageMarginBottom = dexEntryImageLayoutParams.bottomMargin;
                final int dexEntryImageMaxMarginBottom = getResources()
                        .getDimensionPixelSize(R.dimen.dex_entry_image_max_bottom_margin);

                // These top padding values are used for the same thing as the margins
                final int dexEntryNamePaddingTop = dexEntryName.getPaddingTop();
                final int dexEntryNameMaxPaddingTop = getResources()
                        .getDimensionPixelSize(R.dimen.dex_entry_name_max_top_padding);

                // Same story with these normal and max scale values
                TypedValue outValue = new TypedValue();
                getResources().getValue(R.dimen.dex_entry_image_min_scale, outValue, true);
                final float dexEntryPicMinScale = outValue.getFloat();

                // An animation is created using the a variant of ScrollView that notifies the changes in its
                // internal scrolling
                NotifyingScrollView.OnScrollChangedListener onScrollChangedListener = new NotifyingScrollView
                        .OnScrollChangedListener() {

                    private boolean isLimitHeightSet = false;
                    private int limitHeight = 0;
                    private Rect boundsRect = new Rect();

                    @Override
                    public void onScrollChanged(ScrollView scrollView, int l, int t, int oldl, int oldt) {

                        // Sets the clamping height
                        if (!isLimitHeightSet) {
                            limitHeight = dexEntryHead.getHeight() - toolBar.getHeight();
                            isLimitHeightSet = true;
                        }

                        // Get the ScrollView touchable bounds on the screen
                        scrollView.getHitRect(boundsRect);

                        // Compute the a ratio relative to the limit height
                        float ratio = (float) Math.min(Math.max(t, 0), limitHeight) / limitHeight;
                        toolBarDrawableAlpha = (int) (ratio * 255);
                        toolBarTypeColorDrawable.setAlpha(toolBarDrawableAlpha);

                        // Calculate new dex image scale relative to the ratio
                        float dexEntryImageNewScale = Math.max(dexEntryPicMinScale, ((1 - ratio / 2) * 1));
                        dexEntryImage.setScaleX(dexEntryImageNewScale);
                        dexEntryImage.setScaleY(dexEntryImageNewScale);

                        // Calculate new image bottom margin relative to the ratio
                        int dexEntryImageNewBottomMargin = Math.max(dexEntryImageMarginBottom,
                                                                    Math.min(dexEntryImageMaxMarginBottom,
                                                                             (int) ((ratio * 1.5) *
                                                                                    dexEntryImageMaxMarginBottom)));
                        ((FrameLayout.LayoutParams) dexEntryImage
                                .getLayoutParams()).bottomMargin = dexEntryImageNewBottomMargin;
                        dexEntryImage.requestLayout(); // Request the layout with new parameters

                        // Same thing as with the margin
                        int dexEntryNameNewTopPadding = Math.max(dexEntryNamePaddingTop,
                                                                 Math.min(dexEntryNameMaxPaddingTop,
                                                                          (int) ((ratio) * dexEntryNameMaxPaddingTop)));
                        dexEntryName.setPadding(dexEntryName.getPaddingLeft(), dexEntryNameNewTopPadding,
                                                dexEntryName.getPaddingRight(), dexEntryName.getPaddingBottom());

                        // Determine if the Pokémon name is on the screen so it's shown on the toolbar instead of the number
                        if (!dexEntryName.getLocalVisibleRect(boundsRect)) {
                            toolBar.setTitle(name);
                        } else {
                            toolBar.setTitle(dexNumber);
                        }
                    }
                };

                // Set that overly complicated listener from above
                dexEntryScroller.setOnScrollChangedListener(onScrollChangedListener);

                toolBar.setTitle(dexNumber);

                dexEntryHead.setBackgroundResource(
                        TypeUtil.getTypeBackgroundRes(TypeUtil.Type.getTypeByValue(primaryType.getId())));

                dexEntryName.setText(name);
                dexEntryGenus.setText(genus + " " + getResources().getString(R.string.pokemon));
                dexEntryFlavorText.setText(flavorText);

                try {
                    // Get input stream
                    InputStream inputStream = getActivity().getAssets()
                            .open("images/pokemon/art/" + dexNumber.substring(1, dexNumber.length()) + ".png");
                    // Load image as Drawable
                    Drawable drawable = Drawable.createFromStream(inputStream, null);
                    // Set image to ImageView
                    dexEntryImage.setImageDrawable(drawable);
                }
                catch (IOException e) {
                    e.printStackTrace();
                }

                // todo change images among the available ones for the species
                dexEntryImageProxy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getActivity(), "Doesn't work yet", Toast.LENGTH_LONG).show();
                    }
                });

                dexEntryPrimaryType.setType(TypeUtil.Type.getTypeByValue(primaryType.getId()));

                if (secondaryType != null) {
                    dexEntrySecondaryType.setType(TypeUtil.Type.getTypeByValue(secondaryType.getId()));
                } else {
                    dexEntrySecondaryType.setType(TypeUtil.Type.NONE);
                }

                // Process and display the abilities for the dex entry Pokémon
                for (final Ability ability : abilities) {
                    View dexEntryAbilityRowView =
                            inflater.inflate(R.layout.dex_entry_ability_row, dexEntryAbilitiesContent, false);
                    dexEntryAbilityRowView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            View abilityDialogView = inflater.inflate(R.layout.ability_dialog, null);
                            AlertDialog.Builder builder =
                                    new AlertDialog.Builder(getActivity()).setView(abilityDialogView);
                            final AlertDialog dialog = builder.create();
                            abilityDialogView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    dialog.dismiss();
                                }
                            });
                            TextView abilityName = (TextView) abilityDialogView.findViewById(R.id.ability_name);
                            TextView abilityEffect = (TextView) abilityDialogView.findViewById(R.id.ability_effect);
                            abilityName.setText(ability.getName());
                            if (ability.isHidden()) {
                                abilityName.append(" (" + getString(R.string.ability_hidden) + ")");
                            }
                            abilityEffect.setText(PokemonTextUtil.processDexText(getActivity(),
                                                                                 ability.getEffect()));
                            dialog.show(); // todo when this dialog gets moved to its rightful place, destroy the dialog on onDestroy
                        }
                    });
                    TextView abilityName = (TextView) dexEntryAbilityRowView.findViewById(R.id.ability_name);
                    TextView abilityFlavorText =
                            (TextView) dexEntryAbilityRowView.findViewById(R.id.ability_flavor_text);
                    abilityName.setText(ability.getName());
                    if (ability.isHidden()) {
                        abilityName.append(" (" + getString(R.string.ability_hidden_short) + ")");
                    }
                    abilityFlavorText.setText(ability.getFlavorText());
                    dexEntryAbilitiesContent.addView(dexEntryAbilityRowView);
                }

                // Height
                dexEntryHeightMeters.setText(String.format("%.1f", pokemon.getHeight()) + "m");
                dexEntryHeightFeetInches.setText(ConversionUtil.toFeetInches(pokemon.getHeight()));

                // Weight
                dexEntryWeightKilograms.setText(String.format("%.1f", pokemon.getWeight()) + "kg");
                dexEntryWeightPounds.setText(String.format("%.1f", ConversionUtil.toPounds(pokemon.getWeight())) + "lb");

                return rootView;
            } else {
                return rootView;
            }
        }
    }
}
