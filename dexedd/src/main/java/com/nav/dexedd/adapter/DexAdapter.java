package com.nav.dexedd.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.nav.dexedd.R;
import com.nav.dexedd.app.DexEntryActivity;
import com.nav.dexedd.model.Pokemon;
import com.nav.dexedd.model.Type;
import com.nav.dexedd.persistence.access.Dex;
import com.nav.dexedd.ui.TypeTagView;
import com.nav.dexedd.util.PokemonTextUtil;
import com.nav.dexedd.util.TypeUtil;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.util.List;

/**
 * Adapter for dexes.
 *
 * @author Eduardo Naveda
 * @since 0.0.1
 */
public class DexAdapter extends ArrayAdapter<Pokemon> {

    private static final String TAG = DexAdapter.class.getSimpleName();

    private DisplayImageOptions displayImageOptions = new DisplayImageOptions.Builder()
            .showImageOnLoading(R.drawable.pokeball_background_gray).showImageForEmptyUri(R.drawable.pokeball_background_gray)
            .showImageOnFail(R.drawable.pokeball_background_gray).cacheInMemory(true)
            .displayer(new FadeInBitmapDisplayer(400, true, false, false)).build();

    private int resource;
    private ImageLoader imageLoader;

    private DexAdapter(Context context, int resource, List<Pokemon> pokemon) {
        super(context, resource, pokemon);
        this.resource = resource;
    }

    public static DexAdapter createDexAdapter(Context context, int resource, List<Pokemon> pokemon) {
        DexAdapter dexAdapter = new DexAdapter(context, resource, pokemon);

        // I really despise method calls in the constructor
        dexAdapter.imageLoader = ImageLoader.getInstance();
        return dexAdapter;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Pokemon pokemon = getItem(position);

        View row = convertView;

        if (row == null) {
            Holder holder = new Holder();
            row = LayoutInflater.from(getContext()).inflate(resource, parent, false);
            holder.dexCell = (RelativeLayout) row.findViewById(R.id.dex_cell);
            holder.dexPrimaryType = (TypeTagView) row.findViewById(R.id.dex_primary_type);
            holder.dexSecondaryType = (TypeTagView) row.findViewById(R.id.dex_secondary_type);
            holder.dexImage = (ImageView) row.findViewById(R.id.dex_image);
            holder.dexPanel = (RelativeLayout) row.findViewById(R.id.dex_panel);
            holder.dexNumber = (TextView) row.findViewById(R.id.dex_number);
            holder.dexName = (TextView) row.findViewById(R.id.dex_name);
            holder.dexCatchButton = (ImageButton) row.findViewById(R.id.dex_catch_button);
            row.setTag(holder);
        }

        Holder holder = (Holder) row.getTag();
        final Integer id = pokemon.getId();
        final Integer speciesId = pokemon.getSpeciesId();
        final Type primaryType = pokemon.getPrimaryType();
        final Type secondaryType = pokemon.getSecondaryType();
        final String dexNumber = PokemonTextUtil.getFormattedDexNumber(pokemon.getDexNumber());
        final String name = pokemon.getName();
        final Boolean catched = pokemon.getCatched();

        holder.dexPrimaryType.setType(TypeUtil.Type.getTypeByValue(primaryType.getId()));

        if (secondaryType != null) {
            holder.dexSecondaryType.setType(TypeUtil.Type.getTypeByValue(secondaryType.getId()));
        } else {
            holder.dexSecondaryType.setType(TypeUtil.Type.NONE);
        }

        imageLoader.displayImage("assets://images/pokemon/art/" + dexNumber.substring(1, dexNumber.length()) + ".png",
                                 holder.dexImage, displayImageOptions);

        // To dex entry!
        View.OnClickListener toDexEntry = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), DexEntryActivity.class);
                intent.putExtra(DexEntryActivity.POKEMON_ID, id);
                intent.putExtra(DexEntryActivity.DEX_ENTRY_TYPE_ID, primaryType.getId());
                getContext().startActivity(intent);
            }
        };
        holder.dexCell.setOnClickListener(toDexEntry);

        holder.dexName.setText(name);
        holder.dexNumber.setText(dexNumber);

        if (catched) {
            holder.dexCatchButton.setImageDrawable(
                    getContext().getResources().getDrawable(R.drawable.ic_catched));
        } else {
            holder.dexCatchButton.setImageDrawable(
                    getContext().getResources().getDrawable(R.drawable.ic_uncatched));
        }

        holder.dexCatchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pokemon.getCatched()) {
                    Dex.setCatched(getContext().getApplicationContext(), speciesId, false);
                    pokemon.setCatched(false);
                    ((ImageButton) view).setImageDrawable(
                            getContext().getResources().getDrawable(R.drawable.ic_uncatched));
                } else {
                    Dex.setCatched(getContext().getApplicationContext(), speciesId, true);
                    pokemon.setCatched(true);
                    ((ImageButton) view).setImageDrawable(
                            getContext().getResources().getDrawable(R.drawable.ic_catched));
                }
            }
        });

        return row;
    }

    private class Holder {
        RelativeLayout dexCell;
        TypeTagView dexPrimaryType;
        TypeTagView dexSecondaryType;
        ImageView dexImage;
        RelativeLayout dexPanel;
        TextView dexName;
        TextView dexNumber;
        ImageButton dexCatchButton;
    }
}