package com.ivan.translateapp.data.net.yandex.mapper;

import com.ivan.translateapp.data.net.yandex.response.TranslationResponse;
import com.ivan.translateapp.domain.Translation;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * Created by Ivan on 11.04.2017.
 */

public class TranslationResponseMapper implements Function<TranslationResponse, Translation> {

    @Override
    public Translation apply(@NonNull TranslationResponse translationResponse) throws Exception {

        return null;
    }
}
