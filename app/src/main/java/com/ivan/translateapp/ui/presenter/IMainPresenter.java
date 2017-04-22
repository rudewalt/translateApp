package com.ivan.translateapp.ui.presenter;

import com.ivan.translateapp.domain.Translation;
import com.ivan.translateapp.ui.view.main.IMainView;

/**
 * Презентер главного предсставления
 */
public interface IMainPresenter extends IPresenter<IMainView> {

    void loadLanguages();

    void listenText(String text, String fromLanguage, String toLanguage);

    void loadChanges(Translation translation);

    void clickIsFavouriteCheckbox(Translation translation);

    void textToTranslateLostFocus(Translation translation);

    void openFromAnotherFragment(Translation translation);

    void viewHidden(Translation translation);
}
