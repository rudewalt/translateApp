package com.ivan.translateapp.ui.view;

import com.ivan.translateapp.domain.Translation;

import java.util.List;


public interface ITranslationListView extends IView {

    void showTranslations(List<Translation> translations);

    void openMainView(Translation translation);
}
