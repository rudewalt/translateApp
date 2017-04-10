package com.ivan.translateapp.ui.presenter;

import com.ivan.translateapp.domain.Translation;
import com.ivan.translateapp.ui.view.main.IMainView;

/**
 * Created by Ivan on 27.03.2017.
 */

public interface IMainPresenter {

    void bindView(IMainView iMainView);

    void unbindVIew();

    void loadLanguages();

    void listenText(String text, String fromLanguage, String toLanguage);

    void clickIsFavouriteCheckbox(Translation translation);

    void textToTranslateLostFocus(Translation translation);
}
