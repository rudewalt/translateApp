package com.ivan.translateapp.ui.presenter;

import com.ivan.translateapp.ui.view.ITranslationListView;
import com.ivan.translateapp.ui.view.IView;

/**
 * Created by Ivan on 17.04.2017.
 */

public interface ITranslationListViewPresenter extends IPresenter<ITranslationListView>,ISupportFavoritesPresenter {

    void loadTranslations();
}
