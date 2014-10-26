package com.nav.dexedd.persistence.access;

import android.content.Context;
import android.database.Cursor;

import com.nav.dexedd.R;
import com.nav.dexedd.model.Ability;
import com.nav.dexedd.model.Pokemon;
import com.nav.dexedd.model.Type;
import com.nav.dexedd.persistence.DexDatabase;
import com.nav.dexedd.util.PokemonStringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Dex entry data access object
 *
 * @author Eduardo Naveda
 * @since 0.0.1
 */
public class DexEntry extends Access {

    public static enum Version {

        XY(24); // Todo add all versions

        private Integer version;

        private Version(Integer version) {
            this.version = version;
        }

        @Override
        public String toString() {
            return version.toString();
        }

    }

    private Integer id;
    private Version version;

    private DexEntry(Context context, Integer id, Version version) {
        super(context);
        this.id = id;
        this.version = version;
    }

    public static DexEntry create(Context context, Integer id) {
        database = DexDatabase.getInstance(context).getReadableDatabase();
        Version version = Version.XY; // Todo from preferences
        return new DexEntry(context, id, version);
    }

    /**
     * Creates a dex entry.
     *
     * @param context The application context.
     * @param id      The Pokémon id.
     * @param version The game version.
     *
     * @return
     */
    public static DexEntry create(Context context, Integer id, Version version) {
        database = DexDatabase.getInstance(context).getReadableDatabase();
        return new DexEntry(context, id, version);
    }

    /**
     * Get a the Pokémon information for this dex entry.
     *
     * @return The {@link com.nav.dexedd.model.Pokemon} for the dex entry.
     */
    public Pokemon getPokemon() {
        String[] args = {Dex.DexType.NATIONAL_DEX.toString(), version.toString(), id.toString()};
        String query = getContext().getString(R.string.get_dex_entry);
        Cursor cursor = database.rawQuery(query, args);
        cursor.moveToFirst();
        Pokemon pokemon = new Pokemon();
        pokemon.setId(cursor.getInt(0));
        pokemon.setSpeciesId(cursor.getInt(1));
        pokemon.setDexNumber(cursor.getInt(2));
        pokemon.setName(cursor.getString(3));
        pokemon.setGenus(cursor.getString(4));
        pokemon.setFlavorText(PokemonStringUtil.cleanDexText(cursor.getString(5)));
        Type primaryType = new Type();
        primaryType.setId(cursor.getInt(6));
        pokemon.setPrimaryType(primaryType);
        Integer secondaryTypeId = cursor.getInt(7);
        if (secondaryTypeId != 0) {
            Type secondaryType = new Type();
            secondaryType.setId(secondaryTypeId);
            pokemon.setSecondaryType(secondaryType);
        }
        pokemon.setAbilities(getAbilities(pokemon.getId()));
        pokemon.setCatched(cursor.getInt(8) == 1);
        cursor.close();
        return pokemon;
    }

    public List<Ability> getAbilities(Integer id) {
        String[] args = {id.toString()};
        String query = getContext().getString(R.string.get_abilities);
        Cursor cursor = database.rawQuery(query, args);
        List<Ability> abilities = new ArrayList<>();
        while (cursor.moveToNext()) {
            Ability ability = new Ability();
            ability.setId(cursor.getInt(0));
            ability.setName(cursor.getString(1));
            ability.setFlavorText(PokemonStringUtil.cleanDexText(cursor.getString(2)));
            ability.setEffect(PokemonStringUtil.cleanDexText(cursor.getString(3)));
            ability.setIsHidden(cursor.getInt(4) == 1);
            ability.setSlot(cursor.getInt(5));
            abilities.add(ability);
        }
        cursor.close();
        return abilities;
    }
}
