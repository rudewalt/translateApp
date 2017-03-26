package com.ivan.translateapp.data.net.yandex.mapper;

import com.ivan.translateapp.data.net.yandex.dto.SupportedLanguagesDTO;
import com.ivan.translateapp.domain.Language;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * Created by Ivan on 26.03.2017.
 */

public class SupportedLanguagesDTOMapper implements Function<SupportedLanguagesDTO,List<Language>> {
    @Override
    public List<Language> apply(@NonNull SupportedLanguagesDTO supportedLanguagesDTO) throws Exception {

        List<Language> languages = Observable.fromIterable(supportedLanguagesDTO.getLangs().entrySet())
                .map(mapEntry-> new Language(mapEntry.getValue(), mapEntry.getKey()))
                .toList()
                .blockingGet();

        return languages;
    }
}
