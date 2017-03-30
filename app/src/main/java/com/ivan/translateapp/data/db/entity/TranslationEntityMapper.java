package com.ivan.translateapp.data.db.entity;

import com.ivan.translateapp.domain.Translation;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * mapper from entity to domain
 */

public class TranslationEntityMapper implements Function<TranslationEntity, Translation> {
    @Override
    public Translation apply(@NonNull TranslationEntity translationEntity) throws Exception {

        Translation translation = new Translation();
        translation.setText(translationEntity.getText());
        translation.setTranslated(translationEntity.getTranslated());
        translation.setFromLanguage(translationEntity.getFromLanguage());
        translation.setToLanguage(translationEntity.getToLanguage());

        return translation;
    }
}
