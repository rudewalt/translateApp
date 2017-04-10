package com.ivan.translateapp.ui.view.main;

import com.ivan.translateapp.domain.Language;
import com.ivan.translateapp.ui.view.IBaseView;

import java.util.List;

/**
 * Created by Ivan on 27.03.2017.
 */

public interface IMainView extends IBaseView {

    void setLanguages(List<Language> languages);

    void setTranslatedText(String text);

    void setFromLanguage(String fromLanguage);

    void setToLanguage(String toLanguage);

    void setText(String text);

    void showClearButton();

    void hideClearButton();

    void showIsFavouriteCheckbox();

    void hideIsFavouriteCheckbox();

    void setStateIsFavouriteCheckbox(boolean checked);
}
