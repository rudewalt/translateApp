package com.ivan.translateapp.ui.presenter;

import com.ivan.translateapp.ui.view.favorites.IFavoritesView;

/**
 * Created by Ivan on 07.04.2017.
 */

public interface IFavoritesPresenter extends IPresenter<IFavoritesView>, ISupportIsFavoriteCheckbox {

    void loadFavorites();
}
