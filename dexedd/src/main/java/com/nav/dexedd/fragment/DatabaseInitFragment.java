package com.nav.dexedd.fragment;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import com.nav.dexedd.persistence.DexDatabase;

/**
 * Fragment which simply manages a task that handles the preloaded database initialization.
 *
 * @author Eduardo Naveda
 * @since 0.0.1
 */
public class DatabaseInitFragment extends Fragment {

    private static final String TAG = DatabaseInitFragment.class.getSimpleName();

    public static interface DatabaseInitCallbackable {
        void onPostExecute();
    }

    private DatabaseInitCallbackable listener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listener = ((DatabaseInitCallbackable) activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);

        DatabaseInitTask databaseInitTask = new DatabaseInitTask();
        databaseInitTask.execute();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public class DatabaseInitTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            DexDatabase dexDatabase = DexDatabase.getInstance(getActivity().getApplicationContext());
            dexDatabase.getReadableDatabase();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (listener != null) {
                listener.onPostExecute();
            }
        }

    }
}

