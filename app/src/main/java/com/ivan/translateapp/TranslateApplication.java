package com.ivan.translateapp;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.ivan.translateapp.dagger.ApplicationModule;
import com.ivan.translateapp.dagger.DaggerIApplicationComponent;
import com.ivan.translateapp.dagger.IApplicationComponent;
import com.ivan.translateapp.data.net.NetworkModule;

import java.util.Locale;


public class TranslateApplication extends Application {

    private IApplicationComponent iApplicationComponent;

    public static TranslateApplication get(@NonNull Context context) {
        return (TranslateApplication) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        iApplicationComponent =
                DaggerIApplicationComponent
                        .builder()
                        .applicationModule(new ApplicationModule(
                                this,
                                Locale.getDefault()))
                        .networkModule(new NetworkModule(
                                "https://translate.yandex.net/api/v1.5/tr.json/",
                                "trnsl.1.1.20170317T170351Z.34081ee0ccb0bc5a.0aa288afa818fd81d6fefc8ce938b0de8995cc6f"
                        ))
                        .build();
    }

    @NonNull
    public IApplicationComponent getApplicationComponent() {
        return iApplicationComponent;
    }
}
