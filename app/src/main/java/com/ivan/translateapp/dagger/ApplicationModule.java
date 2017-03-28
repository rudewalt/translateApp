package com.ivan.translateapp.dagger;

import android.content.Context;
import android.support.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Ivan on 28.03.2017.
 */

@Module
public class ApplicationModule {
    private final Context appContext;

    public ApplicationModule(@NonNull Context context) {
        appContext = context;
    }

    @Provides
    @Singleton
    Context provideContext() {
        return appContext;
    }
}
