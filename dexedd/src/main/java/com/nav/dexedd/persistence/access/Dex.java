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

    /**
     * Creates a dex instance using the default dex type.
     *
     * @param context The application context.
     *
     * @return dex instance.
     */
    public static Dex create(Context context) {
        database = DexDatabase.getInstance(context).getReadableDatabase();
        DexType dexType = DexType.NATIONAL_DEX; // Todo from preferences
        return new Dex(context, dexType);
    }

    /**
     * Creates a dex instance using a specific dex type.
     *
     * @param context The application context.
     * @param dexType The dex type.
     *
     * @return dex instance.
     */
    public static Dex create(Context context, DexType dexType) {
        database = DexDatabase.getInstance(context).getReadableDatabase();
        return new Dex(context, dexType);
    }

    /**
     * Fetches all Pokémon within the dex instance.
     *
     * @return a list of all the {@link com.nav.dexedd.model.Pokemon} within the dex.
     */
    public List<Pokemon> listPokemon() {
        String[] args = {dexType.toString()};
        String query = getContext().getString(R.string.get_dex);
        Cursor cursor = database.rawQuery(query, args);
        List<Pokemon> pokemon = new ArrayList<>();
        while (cursor.moveToNext()) {
            Pokemon pokemonItem = new Pokemon();
            pokemonItem.setId(cursor.getInt(0));
            pokemonItem.setSpeciesId(cursor.getInt(1));
            pokemonItem.setDexNumber(cursor.getInt(2));
            pokemonItem.setName(cursor.getString(3));
            Type primaryType = new Type();
            primaryType.setId(cursor.getInt(4));
            pokemonItem.setPrimaryType(primaryType);
            Integer secondaryTypeId = cursor.getInt(5);
            if (secondaryTypeId != 0) {
                Type secondaryType = new Type();
                secondaryType.setId(secondaryTypeId);
                pokemonItem.setSecondaryType(secondaryType);
            }
            pokemonItem.setCatched(cursor.getInt(6) == 1);
            pokemon.add(pokemonItem);
        }
        cursor.close();
        return pokemon;
    }

    /**
     * Marks a Pokémon species as catched.
     *
     * @param context The application context.
     * @param id      The pokemon species id.
     * @param catched Mark as catched or not.
     */
    public static void setCatched(Context context, Integer id, boolean catched) {
        SQLiteDatabase database = DexDatabase.getInstance(context).getReadableDatabase();
        String where = "id=?";
        String[] args = {id.toString()};
        ContentValues contentValues = new ContentValues();
        contentValues.put("catched", catched);
        database.update("pokemon_species", contentValues, where, args);
    }

}