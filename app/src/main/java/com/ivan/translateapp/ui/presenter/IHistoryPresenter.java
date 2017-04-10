package com.ivan.translateapp.ui.presenter;

import android.support.annotation.NonNull;

import com.ivan.translateapp.domain.Translation;
import com.ivan.translateapp.ui.view.history.IHistoryView;

/**
 * Created by Ivan on 27.03.2017.
 */

public interface IHistoryPresenter extends ISupportIsFavouriteCheckbox{

    void bindView(IHistoryView iHistoryView);

    void unbindView();

    void loadHistory();

    void clickToClearHistoryButton();

    void clickToDeleteTranslationButton(@NonNull Translation translation);
}
