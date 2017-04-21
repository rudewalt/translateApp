package com.ivan.translateapp.ui.view.main;

import com.ivan.translateapp.domain.Language;
import com.ivan.translateapp.ui.view.IView;

import java.util.List;

public interface IMainView extends IView {

    void setLanguages(List<Language> languages);

    void setTranslatedText(String text);

    void setFromLanguage(String fromLanguage);

    void setToLanguage(String toLanguage);

    void setText(String text);

    void showClearButton();

    void hideClearButton();

    void showIsFavoriteCheckbox();

    void hideIsFavoriteCheckbox();

    void setStateIsFavoriteCheckbox(boolean checked);

    void showProgress();

    void hideProgress();
}
