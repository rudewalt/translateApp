package com.ivan.translateapp.ui.presenter;

import com.ivan.translateapp.ui.view.ITranslationListView;

public interface ITranslationListViewPresenter
        extends IPresenter<ITranslationListView>, ISupportFavoritesPresenter {

    void loadTranslations();
}
