package com.ivan.translateapp.ui.history.model;

import com.ivan.translateapp.domain.Translation;

/**
 * Created by Ivan on 01.04.2017.
 */

public class TranslationModel {
    private Translation translation;
    private boolean isFavouirite;

    public TranslationModel(Translation translation, boolean isFavouirite) {
        this.translation = translation;
        this.isFavouirite = isFavouirite;
    }

    public boolean isFavourite() {
        return isFavouirite;
    }

    public void setFavouirite(boolean favouirite) {
        isFavouirite = favouirite;
    }

    public Translation getTranslation() {
        return translation;
    }

    public void setTranslation(Translation translation) {
        this.translation = translation;
    }
}
