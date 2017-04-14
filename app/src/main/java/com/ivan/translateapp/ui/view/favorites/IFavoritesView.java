package com.ivan.translateapp.ui.view.favorites;

import com.ivan.translateapp.domain.Translation;
import com.ivan.translateapp.ui.view.IView;

import java.util.List;

/**
 * Created by Ivan on 07.04.2017.
 */

public interface IFavoritesView extends IView {
    void showFavorites(List<Translation> translations);

    void openMainView(Translation translation);
}
