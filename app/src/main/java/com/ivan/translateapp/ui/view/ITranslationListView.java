package com.ivan.translateapp.ui.view;

import com.ivan.translateapp.domain.Translation;

import java.util.List;

/**
 * Created by Ivan on 17.04.2017.
 */

public interface ITranslationListView extends IView  {


    /**
     * Called when we want to show translations on current view
     * @param translations
     */
    void showTranslations(List<Translation> translations);

    /**
     * Hide current view and open main view with
     * @param translation
     */
    void openMainView(Translation translation);
}
