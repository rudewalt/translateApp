package com.ivan.translateapp.data.net.yandex.mapper;

import com.ivan.translateapp.data.net.yandex.dto.LanguageDTO;
import com.ivan.translateapp.domain.Language;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * Created by Ivan on 26.03.2017.
 */

public class LanguageDTOMapper implements Function<LanguageDTO, Language> {

    @Override
    public Language apply(@NonNull LanguageDTO languageDTO) throws Exception {
        Language lang = new Language("",languageDTO.getLang());

        return lang;
    }
}
