package com.ivan.translateapp.data.net.yandex.mapper;

import com.ivan.translateapp.data.net.yandex.response.SupportedLanguagesResponse;
import com.ivan.translateapp.domain.Language;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;


/**
 * Маппинг ответа api переводчика в список объектов
 */
public class SupportedLanguagesResponseMapper implements Function<SupportedLanguagesResponse, List<Language>> {
    @Override
    public List<Language> apply(@NonNull SupportedLanguagesResponse supportedLanguagesDTO) throws Exception {
        List<Language> result = new ArrayList<>();

        for (Map.Entry<String, String> mapEntry : supportedLanguagesDTO.getLangs().entrySet()) {
            result.add(new Language(mapEntry.getValue(), mapEntry.getKey()));
        }

        return result;
    }
}
