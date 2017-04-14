package com.ivan.translateapp.ui.presenter;

import com.ivan.translateapp.domain.Translation;

/**
 * Created by Ivan on 10.04.2017.
 */

public interface ISupportFavoritesPresenter {

    void isFavoriteCheckboxStateChanged(Translation translation);

    void clickOnTranslation(Translation translation);

}
