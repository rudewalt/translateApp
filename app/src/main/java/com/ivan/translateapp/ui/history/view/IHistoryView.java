package com.ivan.translateapp.ui.history.view;

import com.ivan.translateapp.domain.Translation;
import com.ivan.translateapp.ui.IBaseView;

import java.util.List;

/**
 * Created by Ivan on 27.03.2017.
 */

public interface IHistoryView extends IBaseView {

    void showHistory(List<Translation> translations);

    void showFavourites(List<Translation> translations);

    void clearButtonClicked();

    void deleteButtonClicked();
}
