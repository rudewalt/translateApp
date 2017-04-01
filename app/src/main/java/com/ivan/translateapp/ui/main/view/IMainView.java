package com.ivan.translateapp.ui.main.view;

import com.ivan.translateapp.domain.Language;

import java.util.List;

/**
 * Created by Ivan on 27.03.2017.
 */

public interface IMainView {

    void loadLanguages(List<Language> languages);

    void setTranslatedText(String text);

    void setFromLanguage();

    void setToLanguage();

    void setText(String text);

    void saveToFavourites();

    void showError(String text);

    void showHistoryScreen();

    void showClearButton();

    void hideClearButton();

    void showAddToFavouritesButton();

    void hideAddToFavouritesButton();

    void setStateActiveOnAddToFavouritesButton();
}
