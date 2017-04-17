package com.ivan.translateapp.ui.presenter;

import com.ivan.translateapp.domain.interactor.IHistoryInteractor;

/**
 * Created by Ivan on 07.04.2017.
 */

public class FavoritesPresenter extends BaseTranslationListPresenter {

    public FavoritesPresenter(IHistoryInteractor iHistoryInteractor){
        super(iHistoryInteractor);
    }

    @Override
    public void loadTranslations() {
        super.loadFavorites();
    }
}
