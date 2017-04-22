package com.ivan.translateapp.ui.presenter;

import com.ivan.translateapp.domain.Translation;

public interface ISupportFavoritesPresenter {

    void isFavoriteCheckboxStateChanged(Translation translation);

    void clickOnTranslation(Translation translation);

}
