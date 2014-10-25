package com.nav.dexedd.persistence;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Database helper class.
 *
 * @author Eduardo Naveda
 * @since 0.0.1
 */
public class DexDatabase extends SQLiteAssetHelper {

    private static final String DATABASE_NAME = "dex.sqlite";
    private static final int DATABASE_VERSION = 1;

    private static DexDatabase dexDatabase;

    public static DexDatabase getInstance(Context context) {
        if (dexDatabase == null) {
            dexDatabase = new DexDatabase(context);
        }
        return dexDatabase;
    }

    private DexDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

}