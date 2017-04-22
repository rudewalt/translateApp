package com.ivan.translateapp.data.db.entity;

import com.ivan.translateapp.domain.Translation;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * маппер из сущности в доменный класс
 */

public class TranslationEntityMapper implements Function<TranslationEntity, Translation> {
    @Override
    public Translation apply(@NonNull TranslationEntity translationEntity) throws Exception {
        return new Translation(
                translationEntity.getText(),
                translationEntity.getTranslated(),
                translationEntity.getFromLanguage(),
                translationEntity.getToLanguage(),
                translationEntity.isHistory(),
                translationEntity.isFavorite());
    }
}
