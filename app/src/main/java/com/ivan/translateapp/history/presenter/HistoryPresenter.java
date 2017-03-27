package com.ivan.translateapp.history.presenter;

import com.ivan.translateapp.domain.interactor.IMainInteractor;
import com.ivan.translateapp.history.view.IHistoryView;

/**
 * Created by Ivan on 27.03.2017.
 */

public class HistoryPresenter implements IHistoryPresenter {

    IMainInteractor iTranslationInteractor;

    IHistoryView iHistoryView;

    @Override
    public void bindView(IHistoryView iHistoryView) {
        this.iHistoryView = iHistoryView;
    }

    @Override
    public void unbindView() {
        iHistoryView = null;
    }

    @Override
    public void loadHistory() {

    }

    @Override
    public void loadFavourites() {

    }
}
