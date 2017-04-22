package com.ivan.translateapp.dagger;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.Locale;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {
    private final Context appContext;
    private final Locale appLocale;

    public ApplicationModule(@NonNull Context context, @NonNull Locale locale) {
        appContext = context;
        appLocale = locale;
    }

    @Provides
    @Singleton
    Context provideContext() {
        return appContext;
    }

    @Provides
    @Singleton
    Locale provideLocale() {
        return appLocale;
    }
}
