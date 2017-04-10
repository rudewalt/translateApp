package com.ivan.translateapp.ui.presenter;

import com.ivan.translateapp.domain.Translation;
import com.ivan.translateapp.ui.view.history.IFavouritesView;

/**
 * Created by Ivan on 07.04.2017.
 */

public interface IFavouritesPresenter extends ISupportIsFavouriteCheckbox {

    void bindView(IFavouritesView iFavouritesView);

    void unbindView();

    void loadFavourites();
}
