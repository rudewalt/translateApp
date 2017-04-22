package com.ivan.translateapp.dagger;

import com.ivan.translateapp.data.net.NetworkModule;

import javax.inject.Singleton;

import dagger.Component;


@Component(modules = {ApplicationModule.class, NetworkModule.class, DataModule.class})
@Singleton
public interface IApplicationComponent {

    IMainComponent plus(MainModule mainModule);
}
