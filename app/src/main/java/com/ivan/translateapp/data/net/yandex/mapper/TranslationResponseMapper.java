package com.ivan.translateapp.data.net.yandex.mapper;

import android.text.TextUtils;

import com.ivan.translateapp.data.net.yandex.response.TranslationResponse;
import com.ivan.translateapp.domain.Translation;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;


/**
 * Map response of YandexApi to domain class
 */
public class TranslationResponseMapper implements Function<TranslationResponse, Translation> {

    @Override
    public Translation apply(@NonNull TranslationResponse translationResponse) throws Exception {
        Translation translation = new Translation();
        translation.setTranslated(TextUtils.join(" ", translationResponse.getText()));
        return translation;
    }
}
