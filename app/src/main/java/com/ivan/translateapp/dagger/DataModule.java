package com.ivan.translateapp.dagger;

import android.content.Context;
import android.support.annotation.NonNull;

import com.ivan.translateapp.data.db.DbHelper;
import com.ivan.translateapp.data.db.entity.TranslationEntityMapper;
import com.ivan.translateapp.data.net.ITranslateService;
import com.ivan.translateapp.data.repository.HistoryRepository;
import com.ivan.translateapp.data.repository.IHistoryRepository;
import com.ivan.translateapp.data.repository.ITranslationRepository;
import com.ivan.translateapp.data.repository.TranslationRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


@Module
public class DataModule {

    @Provides
    @Singleton
    TranslationEntityMapper provideTranslationEntityMapper(){
        return new TranslationEntityMapper();
    }

    @Provides
    @NonNull
    @Singleton
    public DbHelper provideDbHelper(@NonNull Context context) {
        return new DbHelper(context);
    }

    @Provides
    @Singleton
    IHistoryRepository provideIHistoryRepository(DbHelper dbHelper,TranslationEntityMapper translationEntityMapper){
        return new HistoryRepository(dbHelper, translationEntityMapper);
    }

    @Provides
    @Singleton
    ITranslationRepository provideITranslationRepository(ITranslateService translateService){
        return new TranslationRepository(translateService);
    }


}
