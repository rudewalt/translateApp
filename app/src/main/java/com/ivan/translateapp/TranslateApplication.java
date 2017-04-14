package com.ivan.translateapp;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.ivan.translateapp.dagger.ApplicationModule;
import com.ivan.translateapp.dagger.DaggerIApplicationComponent;
import com.ivan.translateapp.dagger.IApplicationComponent;

import java.util.Locale;

/**
 * Created by Ivan on 27.03.2017.
 */

public class TranslateApplication extends Application {

    private IApplicationComponent iApplicationComponent;

    public static TranslateApplication get(@NonNull Context context){
        return (TranslateApplication)context.getApplicationContext();
    }

    @Override
    public void onCreate(){
        super.onCreate();

        iApplicationComponent = DaggerIApplicationComponent
                .builder()
                .applicationModule(new ApplicationModule(this, Locale.getDefault()))
                .build();
    }

    @NonNull
    public IApplicationComponent getApplicationComponent(){
        return iApplicationComponent;
    }
}
