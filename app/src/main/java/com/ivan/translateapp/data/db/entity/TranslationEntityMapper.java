package com.ivan.translateapp.data.db.entity;

import com.ivan.translateapp.domain.Translation;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * mapper from entity to domain class
 */

public class TranslationEntityMapper implements Function<TranslationEntity, Translation> {
    @Override
    public Translation apply(@NonNull TranslationEntity translationEntity) throws Exception {

        Translation translation = new Translation(
                translationEntity.getText(),
                translationEntity.getTranslated(),
                translationEntity.getFromLanguage(),
                translationEntity.getToLanguage(),
                translationEntity.isFavorite(),
                translationEntity.isHistory());

        return translation;
    }
}
