package com.ivan.translateapp.dagger;

import com.ivan.translateapp.data.net.NetworkModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Ivan on 28.03.2017.
 */


@Component(modules = {ApplicationModule.class, NetworkModule.class, DataModule.class})
@Singleton
public interface IApplicationComponent {

    IMainComponent plus(MainModule mainModule);

}
