package com.ivan.translateapp.data.net.yandex.mapper;

import android.text.TextUtils;

import com.ivan.translateapp.data.net.yandex.dto.TranslateResultDTO;
import com.ivan.translateapp.domain.Translation;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;


/**
 * Map response of YandexApi to domain class
 */
public class TranslateResultDTOMapper implements Function<TranslateResultDTO, Translation> {

    @Override
    public Translation apply(@NonNull TranslateResultDTO translateResultDTO) throws Exception {
        Translation translation = new Translation();
        translation.setTranslated(TextUtils.join(" ",translateResultDTO.getText()));
        return translation;
    }
}
