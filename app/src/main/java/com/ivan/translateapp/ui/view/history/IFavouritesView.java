package com.ivan.translateapp.ui.view.history;

import com.ivan.translateapp.domain.Translation;
import com.ivan.translateapp.ui.view.IBaseView;

import java.util.List;

/**
 * Created by Ivan on 07.04.2017.
 */

public interface IFavouritesView extends IBaseView {
    void showFavourites(List<Translation> translations);
}
