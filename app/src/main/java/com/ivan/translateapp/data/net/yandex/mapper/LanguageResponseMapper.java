package com.ivan.translateapp.data.net.yandex.mapper;

import com.ivan.translateapp.data.net.yandex.response.LanguageResponse;
import com.ivan.translateapp.domain.Language;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * Created by Ivan on 26.03.2017.
 */

public class LanguageResponseMapper implements Function<LanguageResponse, Language> {

    @Override
    public Language apply(@NonNull LanguageResponse languageDTO) throws Exception {
        Language lang = new Language("",languageDTO.getLang());

        return lang;
    }
}
