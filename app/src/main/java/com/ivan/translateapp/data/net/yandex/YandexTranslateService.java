package com.ivan.translateapp.data.net.yandex;

import android.text.TextUtils;
import android.util.Log;

import com.ivan.translateapp.data.net.ITranslateService;
import com.ivan.translateapp.data.net.exception.TranslateServiceException;
import com.ivan.translateapp.data.net.yandex.mapper.LanguageResponseMapper;
import com.ivan.translateapp.data.net.yandex.mapper.SupportedLanguagesResponseMapper;
import com.ivan.translateapp.data.net.yandex.response.BaseResponse;
import com.ivan.translateapp.domain.Language;
import com.ivan.translateapp.domain.Translation;

import java.util.List;

import io.reactivex.Observable;

/**
 * Implementation of ITranslateService
 */

public class YandexTranslateService implements ITranslateService {

    private static final String TAG = YandexTranslateService.class.toString();
    private static final int MAX_TEXT_LENGTH = 10000;


    private IYandexTranslateApiInterface apiInterface;
    private LanguageResponseMapper languageResponseMapper;
    private SupportedLanguagesResponseMapper supportedLanguagesResponseMapper;


    public YandexTranslateService(IYandexTranslateApiInterface apiInterface,
                                  LanguageResponseMapper languageResponseMapper,
                                  SupportedLanguagesResponseMapper supportedLanguagesResponseMapper) {
        this.apiInterface = apiInterface;
        this.languageResponseMapper = languageResponseMapper;
        this.supportedLanguagesResponseMapper = supportedLanguagesResponseMapper;
    }

    @Override
    public Observable<List<Language>> getLanguages(String ui) {
        Observable<List<Language>> languages = apiInterface
                .getLanguages(ui)
                .map(this::checkResponseCode)
                .map(supportedLanguagesResponseMapper);

        return languages;
    }

    @Override
    public Observable<Language> detectLanguage(String text) {

        Observable<Language> detectedLanguage = apiInterface
                .detectLanguage(text)
                .map(this::checkResponseCode)
                .map(languageResponseMapper);

        return detectedLanguage;
    }

    @Override
    public Observable<Translation> translate(String text, String fromLanguage, String toLanguage) {
        final int includeDetectedLanguage = 1;
        String direction = fromLanguage == null || fromLanguage.isEmpty()
                ? toLanguage
                : String.format("%1$s-%2$s", fromLanguage, toLanguage);


        Observable<Translation> translation = apiInterface
                .translate(text, direction, includeDetectedLanguage)
                .map(this::checkResponseCode)
                .map(translationResponse -> {
                    String translatedText = TextUtils.join(" ", translationResponse.getText());
                    return new Translation(text, translatedText, toLanguage, fromLanguage, false, false);
                });

        return translation;
    }

    private <T extends BaseResponse> T checkResponseCode(T response) throws TranslateServiceException {
        Integer code = response.getCode();
        if(code == null)
            return response;

        switch (code) {
            case ErrorCodes.CANT_TRANSLATE:
            case ErrorCodes.TEXT_LENGTH_LIMIT_EXCEEDED:
            case ErrorCodes.DAILY_LIMIT_EXCEEDED:
            case ErrorCodes.UNSUPPORTED_DIRECTION:
                throw new TranslateServiceException(code);

            case ErrorCodes.INVALID_API_KEY:
            case ErrorCodes.BLOCKED_API_KEY:
                Log.e(TAG," Yandex Api return error response code " + code);
                throw new TranslateServiceException(code);
        }

        return response;
    }
}
