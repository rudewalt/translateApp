package com.ivan.translateapp.history.presenter;

import com.ivan.translateapp.history.view.IHistoryView;

/**
 * Created by Ivan on 27.03.2017.
 */

public interface IHistoryPresenter {

    void bindView(IHistoryView iHistoryView);

    void unbindView();

    void loadHistory();

    void loadFavourites();
}
