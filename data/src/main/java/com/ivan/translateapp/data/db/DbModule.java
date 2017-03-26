package com.ivan.translateapp.data.db;

import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import com.ivan.translateapp.data.entity.TranslationEntity;
import com.ivan.translateapp.data.entity.TranslationEntityStorIOSQLiteDeleteResolver;
import com.ivan.translateapp.data.entity.TranslationEntityStorIOSQLiteGetResolver;
import com.ivan.translateapp.data.entity.TranslationEntityStorIOSQLitePutResolver;
import com.pushtorefresh.storio.sqlite.SQLiteTypeMapping;
import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.impl.DefaultStorIOSQLite;


public class DbModule {
    public StorIOSQLite provideStorIOSQLite(@NonNull SQLiteOpenHelper sqLiteOpenHelper) {
        return DefaultStorIOSQLite.builder()
                .sqliteOpenHelper(sqLiteOpenHelper)
                .addTypeMapping(TranslationEntity.class, SQLiteTypeMapping.<TranslationEntity>builder()
                        .putResolver(new TranslationEntityStorIOSQLitePutResolver())
                        .getResolver(new TranslationEntityStorIOSQLiteGetResolver())
                        .deleteResolver(new TranslationEntityStorIOSQLiteDeleteResolver())
                        .build())
                .build();
    }
}
