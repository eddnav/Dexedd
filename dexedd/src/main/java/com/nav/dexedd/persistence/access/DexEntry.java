package com.nav.dexedd.persistence.access;

import android.content.Context;
import android.database.Cursor;

import com.nav.dexedd.R;
import com.nav.dexedd.model.Pokemon;
import com.nav.dexedd.model.Type;
import com.nav.dexedd.persistence.DexDatabase;

/**
 * Dex entry data access object
 *
 * @author Eduardo Naveda
 * @since 0.0.1
 */
public class DexEntry extends Access {

    private static final String TAG = DexEntry.class.getSimpleName();

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

    public static DexEntry create(Context context, Integer id, Version version) {
        database = DexDatabase.getInstance(context).getReadableDatabase();
        return new DexEntry(context, id, version);
    }

    public Pokemon getPokemon() {
        String[] argsPokemon = {Dex.DexType.NATIONAL_DEX.toString(), version.toString(), id.toString()};
        String queryPokemon = getContext().getString(R.string.get_dex_entry);
        Pokemon pokemon = new Pokemon();
        Cursor cursorPokemon = database.rawQuery(queryPokemon, argsPokemon);
        cursorPokemon.moveToFirst();
        pokemon.setId(cursorPokemon.getInt(0));
        pokemon.setDexNumber(cursorPokemon.getInt(1));
        pokemon.setName(cursorPokemon.getString(2));
        pokemon.setGenus(cursorPokemon.getString(3));
        pokemon.setFlavorText(cursorPokemon.getString(4).replace("\n", " "));
        Type primaryType = new Type();
        primaryType.setId(cursorPokemon.getInt(5));
        pokemon.setPrimaryType(primaryType);
        Integer secondaryTypeId = cursorPokemon.getInt(6);
        if (secondaryTypeId != null) {
            Type secondaryType = new Type();
            secondaryType.setId(secondaryTypeId);
            pokemon.setSecondaryType(secondaryType);
        }
        pokemon.setCatched(cursorPokemon.getInt(7) == 1);
        cursorPokemon.close();
        return pokemon;
    }
}
