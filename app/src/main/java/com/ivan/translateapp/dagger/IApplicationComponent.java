package com.ivan.translateapp.dagger;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Ivan on 28.03.2017.
 */

@Singleton
@Component(modules = {ApplicationModule.class})
public interface IApplicationComponent {

    IMainComponent plus(MainModule mainModule);

}
