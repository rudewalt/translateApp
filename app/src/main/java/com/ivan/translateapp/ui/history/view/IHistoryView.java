package com.ivan.translateapp.ui.history.view;

import com.ivan.translateapp.domain.Translation;

import java.util.List;

/**
 * Created by Ivan on 27.03.2017.
 */

public interface IHistoryView {

    void showHistory(List<Translation> translations);

    void clearButtonClicked();

    void deleteButtonClicked();

    void showError(String message);
}
