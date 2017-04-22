package com.ivan.translateapp.data.net.yandex.mapper;

import com.ivan.translateapp.data.net.yandex.response.LanguageResponse;
import com.ivan.translateapp.domain.Language;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;


public class LanguageResponseMapper implements Function<LanguageResponse, Language> {

    private static final String EMPTY_STRING = "";

    @Override
    public Language apply(@NonNull LanguageResponse languageDTO) throws Exception {
        return new Language(EMPTY_STRING, languageDTO.getLang());
    }
}
