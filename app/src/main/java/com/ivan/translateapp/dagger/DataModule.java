package com.ivan.translateapp.dagger;

import com.ivan.translateapp.data.db.DbHelper;
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
    IHistoryRepository provideIHistoryRepository(DbHelper dbHelper){
        return new HistoryRepository(dbHelper);
    }

    @Provides
    @Singleton
    ITranslationRepository provideITranslationRepository(ITranslateService translateService){
        return new TranslationRepository(translateService);
    }
}
