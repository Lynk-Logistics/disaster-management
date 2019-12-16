package com.dwarsoftgames.dwarsoft_lynk_hackathon.Database;

import android.os.AsyncTask;

import androidx.annotation.NonNull;

public class UserInitializer {

    public static void populateAsync(@NonNull final AppDatabase db, @NonNull user_table userTable) {
        PopulateDbAsync task = new PopulateDbAsync(db, userTable);
        task.execute();
    }

    private static void addUser(final AppDatabase db, user_table userTable) {
        db.userDao().insertAll(userTable);
    }

    private static void populateWithTestData(AppDatabase db, user_table userTable) {
        addUser(db,userTable);
    }

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final AppDatabase mDb;
        private final user_table userr;

        PopulateDbAsync(AppDatabase db, user_table userTable) {
            mDb = db;
            userr = userTable;
        }

        @Override
        protected Void doInBackground(final Void... params) {
            populateWithTestData(mDb,userr);
            return null;
        }

    }
}