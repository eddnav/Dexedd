package com.nav.dexedd.app;

import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.*;
import android.widget.*;

import com.nav.dexedd.R;
import com.nav.dexedd.component.ui.NotifyingScrollView;
import com.nav.dexedd.component.ui.TypeTagView;
import com.nav.dexedd.model.Pokemon;
import com.nav.dexedd.model.Type;
import com.nav.dexedd.persistence.access.DexEntry;
import com.nav.dexedd.util.DexStringUtil;
import com.nav.dexedd.util.TypeUtil;

import java.lang.reflect.Field;

/**
 * Pokemon entry activity, manages fragments that show entry information.
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
     * Fragment containing a Dex entry.
     */
    public static class DexEntryFragment extends Fragment {

        private FrameLayout dexEntryHead;
        private ImageView dexEntryPicture;
        private NotifyingScrollView dexEntryScroller;
        private TextView dexEntryName;
        private TextView dexEntryGenus;
        private TextView dexEntryFlavorText;
        private TypeTagView dexEntryPrimaryType;
        private TypeTagView dexEntrySecondaryType;

        private int toolBarDrawableAlpha = 0;

        public DexEntryFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            final ActionBar toolBar = ((ActionBarActivity) getActivity()).getSupportActionBar();

            View rootView = inflater.inflate(R.layout.fragment_dex_entry, container, false);

            dexEntryHead = (FrameLayout) rootView.findViewById(R.id.dex_entry_head);
            dexEntryPicture = (ImageView) rootView.findViewById(R.id.dex_entry_picture);
            dexEntryScroller = (NotifyingScrollView) rootView.findViewById(R.id.dex_entry_scroller);
            dexEntryName = (TextView) rootView.findViewById(R.id.dex_entry_name);
            dexEntryGenus = (TextView) rootView.findViewById(R.id.dex_entry_genus);
            dexEntryFlavorText = (TextView) rootView.findViewById(R.id.dex_entry_flavor_text);
            dexEntryPrimaryType = (TypeTagView) rootView.findViewById(R.id.primary_type);
            dexEntrySecondaryType = (TypeTagView) rootView.findViewById(R.id.secondary_type);

            dexEntryScroller.post(new Runnable() {
                public void run() {
                    dexEntryScroller.scrollTo(0, 0);
                }
            });

            if (getArguments() != null) {

                final DexEntry dexEntry = DexEntry.create(getActivity().getApplicationContext(),
                        getArguments().getInt(DexEntryActivity.POKEMON_ID));
                Pokemon pokemon = dexEntry.getPokemon();

                final String dexNumber = DexStringUtil.getFormattedDexNumber(pokemon.getDexNumber());
                final String name = pokemon.getName();
                final String genus = pokemon.getGenus();
                final String flavorText = pokemon.getFlavorText();
                final Type primaryType = pokemon.getPrimaryType();
                final Type secondaryType = pokemon.getSecondaryType();

                //  Setting up the toolbar
                final ColorDrawable toolBarTypeColorDrawable = new ColorDrawable(getResources()
                        .getColor(TypeUtil.getTypeColorRes(TypeUtil.Type.getTypeByValue(primaryType.getId()))));

                toolBarTypeColorDrawable.setAlpha(toolBarDrawableAlpha);

                toolBar.setBackgroundDrawable(toolBarTypeColorDrawable);

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

                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    toolBarTypeColorDrawable.setCallback(drawableCallback);
                }

                FrameLayout.LayoutParams dexEntryPictureLayoutParams = (FrameLayout.LayoutParams) dexEntryPicture
                        .getLayoutParams();

                final int dexEntryPictureMinSize = getResources()
                        .getDimensionPixelSize(R.dimen.dex_entry_picture_min_size);
                final int dexEntryPictureSize = dexEntryPictureLayoutParams.height;

                final int dexEntryPictureMarginBottom = dexEntryPictureLayoutParams.bottomMargin;
                final int dexEntryPictureMaxMarginBottom = getResources()
                        .getDimensionPixelSize(R.dimen.dex_entry_picture_max_bottom_margin);

                final int dexEntryNamePaddingTop = dexEntryName.getPaddingTop();
                final int dexEntryNameMaxPaddingTop = getResources()
                        .getDimensionPixelSize(R.dimen.dex_entry_name_max_top_padding);

                NotifyingScrollView.OnScrollChangedListener onScrollChangedListener = new NotifyingScrollView
                        .OnScrollChangedListener() {

                    private boolean isLimitHeightSet = false;
                    private int limitHeight = 0;
                    private Rect boundsRect = new Rect();

                    @Override
                    public void onScrollChanged(ScrollView scrollView, int l, int t, int oldl, int oldt) {
                        if (!isLimitHeightSet) {
                            limitHeight = dexEntryHead.getHeight() - toolBar.getHeight();
                            isLimitHeightSet = true;
                        }

                        scrollView.getDrawingRect(boundsRect);
                        boundsRect.top += toolBar.getHeight();

                        float ratio = (float) Math.min(Math.max(t, 0), limitHeight) / limitHeight;
                        toolBarDrawableAlpha = (int) (ratio * 255);
                        toolBarTypeColorDrawable.setAlpha(toolBarDrawableAlpha);

                        int dexEntryPictureNewSize = Math
                                .max(dexEntryPictureMinSize, (int) ((1 - ratio / 2) * dexEntryPictureSize));
                        dexEntryPicture.getLayoutParams().height = dexEntryPictureNewSize;
                        dexEntryPicture.getLayoutParams().width = dexEntryPictureNewSize;
                        dexEntryPicture.requestLayout();

                        int dexEntryPictureNewBottomMargin = Math.max(dexEntryPictureMarginBottom,
                                Math.min(dexEntryPictureMaxMarginBottom,
                                        (int) ((ratio * 1.5) * dexEntryPictureMaxMarginBottom)));
                        ((FrameLayout.LayoutParams) dexEntryPicture
                                .getLayoutParams()).bottomMargin = dexEntryPictureNewBottomMargin;
                        dexEntryPicture.requestLayout();

                        int dexEntryNameNewTopPadding = Math.max(dexEntryNamePaddingTop,
                                Math.min(dexEntryNameMaxPaddingTop, (int) ((ratio) * dexEntryNameMaxPaddingTop)));
                        dexEntryName.setPadding(dexEntryName.getPaddingLeft(), dexEntryNameNewTopPadding,
                                dexEntryName.getPaddingRight(), dexEntryName.getPaddingBottom());

                        Rect dexEntryNameBounds = new Rect();
                        dexEntryName.getLocalVisibleRect(dexEntryNameBounds);

                        if (Rect.intersects(boundsRect, dexEntryNameBounds)) {
                            toolBar.setTitle(name);
                        } else {
                            toolBar.setTitle(dexNumber);
                        }
                    }
                };
                dexEntryScroller.setOnScrollChangedListener(onScrollChangedListener);

                toolBar.setTitle(dexNumber);

                //head.setBackgroundColor(getResources().getColor(
                //        TypeUtil.getTypeColorRes(TypeUtil.Type.getTypeByValue(primaryType.getId())))); // Flat color

                dexEntryHead.setBackgroundResource(
                        TypeUtil.getTypeBackgroundRes(TypeUtil.Type.getTypeByValue(primaryType.getId())));

                try {
                    Class res = R.drawable.class;
                    Field field = res.getField("b" + dexNumber.substring(1, dexNumber.length()));
                    int drawableId = field.getInt(null);
                    dexEntryPicture.setImageDrawable(getResources().getDrawable(drawableId));
                } catch (IllegalAccessException | NoSuchFieldException e) {
                    dexEntryPicture.setImageDrawable(getResources().getDrawable(R.drawable.pokeball));
                }

                dexEntryPrimaryType.setType(TypeUtil.Type.getTypeByValue(primaryType.getId()));

                if (secondaryType != null) {
                    dexEntrySecondaryType.setType(TypeUtil.Type.getTypeByValue(secondaryType.getId()));
                } else {
                    dexEntrySecondaryType.setType(TypeUtil.Type.NONE);
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
