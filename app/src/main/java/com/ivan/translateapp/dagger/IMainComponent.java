package com.ivan.translateapp.dagger;

import com.ivan.translateapp.ui.history.view.HistoryFragment;
import com.ivan.translateapp.ui.main.view.MainFragment;

import dagger.Subcomponent;

/**
 * Created by Ivan on 28.03.2017.
 */


@Subcomponent(modules = {MainModule.class})
public interface IMainComponent {

    void inject(MainFragment mainFragment);

    void inject(HistoryFragment historyFragment);

}
