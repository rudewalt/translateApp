package com.ivan.translateapp.data.db;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DbModule {

    @Provides
    @NonNull
    @Singleton
    public DbHelper provideDbHelper(@NonNull Context context) {
        return new DbHelper(context);
    }
}
