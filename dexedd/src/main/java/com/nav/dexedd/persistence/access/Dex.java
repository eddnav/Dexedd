package com.nav.dexedd.persistence.access;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.nav.dexedd.R;
import com.nav.dexedd.model.Pokemon;
import com.nav.dexedd.model.Type;
import com.nav.dexedd.persistence.DexDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Dex data access object
 *
 * @author Eduardo Naveda
 * @since 0.0.1
 */
public class Dex extends Access {

    private static final String TAG = Dex.class.getSimpleName();

    public static enum DexType {

        NATIONAL_DEX(1); // Todo add all dex

        private Integer dexType;

        private DexType(Integer dexType) {
            this.dexType = dexType;
        }

        @Override
        public String toString() {
            return dexType.toString();
        }

    }

    private DexType dexType;


    private Dex(Context context, DexType dexType) {
        super(context);
        this.dexType = dexType;
    }

    public static Dex create(Context context) {
        database = DexDatabase.getInstance(context).getReadableDatabase();
        DexType dexType = DexType.NATIONAL_DEX; // Todo from preferences
        return new Dex(context, dexType);
    }

    public static Dex create(Context context, DexType dexType) {
        database = DexDatabase.getInstance(context).getReadableDatabase();
        return new Dex(context, dexType);
    }

    public List<Pokemon> getPokemon() {
        String[] argsPokemon = {dexType.toString()};
        String queryPokemon = getContext().getString(R.string.get_dex);
        Cursor cursorPokemon = database.rawQuery(queryPokemon, argsPokemon);
        List<Pokemon> pokemon = new ArrayList<>();
        Cursor cursorTypes;
        String queryTypes = getContext().getString(R.string.get_types);
        String[] argsTypes;
        while (cursorPokemon.moveToNext()) {
            Pokemon pokemonItem = new Pokemon();
            pokemonItem.setId(cursorPokemon.getInt(0));
            pokemonItem.setDexNumber(cursorPokemon.getInt(1));
            pokemonItem.setName(cursorPokemon.getString(2));
            argsTypes = new String[]{pokemonItem.getId().toString()};
            cursorTypes = database.rawQuery(queryTypes, argsTypes);
            int i = 0;
            while (cursorTypes.moveToNext()) {
                Type type = new Type();
                type.setId(cursorTypes.getInt(0));
                type.setName(cursorTypes.getString(1));
                if (i == 0) {
                    pokemonItem.setPrimaryType(type);
                } else {
                    if (i == 1) {
                        pokemonItem.setSecondaryType(type);
                    }
                }
                i++;
            }
            cursorTypes.close();
            pokemonItem.setCatched(cursorPokemon.getInt(3) == 1);
            pokemon.add(pokemonItem);
        }
        cursorPokemon.close();
        return pokemon;
    }

    public static void setCatched(Context context, Integer id, boolean catched) {
        SQLiteDatabase database = DexDatabase.getInstance(context).getReadableDatabase();
        String where = "id=?";
        String[] args = {id.toString()};
        ContentValues contentValues = new ContentValues();
        contentValues.put("catched", catched);
        database.update("pokemon_species", contentValues, where, args);
    }

}