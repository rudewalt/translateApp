package com.ivan.translateapp.ui.view.history;

import com.ivan.translateapp.domain.Translation;
import com.ivan.translateapp.ui.view.IView;

import java.util.List;

/**
 * Created by Ivan on 27.03.2017.
 */

public interface IHistoryView extends IView {
    void showTranslations(List<Translation> translations);

    void openMainView(Translation translation);
}
