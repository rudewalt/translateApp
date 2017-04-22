package com.ivan.translateapp.ui.presenter;

import com.ivan.translateapp.domain.interactor.IHistoryInteractor;


public class HistoryPresenter extends BaseTranslationListPresenter {

    public HistoryPresenter(IHistoryInteractor iHistoryInteractor) {
        super(iHistoryInteractor);
    }

    @Override
    public void loadTranslations() {
        super.loadHistory();
    }
}
