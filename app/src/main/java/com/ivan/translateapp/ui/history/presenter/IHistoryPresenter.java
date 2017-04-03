package com.ivan.translateapp.ui.history.presenter;

import android.support.annotation.NonNull;

import com.ivan.translateapp.domain.Translation;
import com.ivan.translateapp.ui.history.view.IHistoryView;

import io.reactivex.Observable;

/**
 * Created by Ivan on 27.03.2017.
 */

public interface IHistoryPresenter {

    void bindView(IHistoryView iHistoryView);

    void unbindView();

    void loadHistory();

    void loadFavourites();

    void saveChanges(Translation translation);
}
