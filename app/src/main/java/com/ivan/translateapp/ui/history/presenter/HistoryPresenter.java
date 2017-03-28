package com.ivan.translateapp.ui.history.presenter;

import com.ivan.translateapp.domain.interactor.IHistoryInteractor;
import com.ivan.translateapp.domain.interactor.IMainInteractor;
import com.ivan.translateapp.ui.history.view.IHistoryView;

/**
 * Created by Ivan on 27.03.2017.
 */

public class HistoryPresenter implements IHistoryPresenter {

    IHistoryInteractor iHistoryInteractor;

    IHistoryView iHistoryView;

    public HistoryPresenter(IHistoryInteractor iHistoryInteractor){
        this.iHistoryInteractor = iHistoryInteractor;
    }

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
