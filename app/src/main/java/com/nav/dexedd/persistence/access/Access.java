package com.nav.dexedd.persistence.access;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Base data access class.
 *
 * @author Eduardo Naveda
 * @since 0.0.2
 */
public abstract class Access {

    protected static SQLiteDatabase database;

    private Context context;

    public Access(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
