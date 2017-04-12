package com.ivan.translateapp.data.net.yandex;

import android.text.TextUtils;
import android.util.Log;

import com.ivan.translateapp.data.net.ITranslateService;
import com.ivan.translateapp.data.net.exception.CanNotBeTranslatedException;
import com.ivan.translateapp.data.net.exception.LimitException;
import com.ivan.translateapp.data.net.exception.TranslateServiceException;
import com.ivan.translateapp.data.net.exception.UnsupportedDirectionException;
import com.ivan.translateapp.data.net.yandex.mapper.LanguageResponseMapper;
import com.ivan.translateapp.data.net.yandex.mapper.SupportedLanguagesResponseMapper;
import com.ivan.translateapp.data.net.yandex.mapper.TranslationResponseMapper;
import com.ivan.translateapp.data.net.yandex.response.BaseResponse;
import com.ivan.translateapp.domain.Language;
import com.ivan.translateapp.domain.Translation;

import java.util.List;

import io.reactivex.Observable;

import static com.ivan.translateapp.data.net.yandex.ResponseCodes.SUCCESS_CODE;

/**
 * Implementation of ITranslateService
 */

public class YandexTranslateService implements ITranslateService {

    private static final String TAG = YandexTranslateService.class.toString();
    private static final int MAX_TEXT_LENGTH = 10000;


    private IYandexTranslateApiInterface apiInterface;
    private LanguageResponseMapper languageResponseMapper;
    private SupportedLanguagesResponseMapper supportedLanguagesResponseMapper;
    private TranslationResponseMapper translationResponseMapper;


    public YandexTranslateService(IYandexTranslateApiInterface apiInterface,
                                  LanguageResponseMapper languageResponseMapper,
                                  SupportedLanguagesResponseMapper supportedLanguagesResponseMapper,
                                  TranslationResponseMapper translationResponseMapper) {
        this.apiInterface = apiInterface;
        this.languageResponseMapper = languageResponseMapper;
        this.supportedLanguagesResponseMapper = supportedLanguagesResponseMapper;
        this.translationResponseMapper = translationResponseMapper;
    }

    @Override
    public Observable<List<Language>> getLanguages(String ui) {
        Observable<List<Language>> languages = apiInterface
                .getLanguages(ui)
                //.map(this::checkResponseCode)
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

    private <T extends BaseResponse> T checkResponseCode(T response)
            throws TranslateServiceException, CanNotBeTranslatedException, LimitException, UnsupportedDirectionException {
        Integer code = response.getCode();
        String errorMessage = ResponseCodes.getErrorMessage(code);

        switch (code) {
            case SUCCESS_CODE:
                return response;
            case ResponseCodes.CANT_TRANSLATE:
                throw new CanNotBeTranslatedException();
            case ResponseCodes.TEXT_LENGTH_LIMIT_EXCEEDED:
            case ResponseCodes.DAILY_LIMIT_EXCEEDED:
                throw new LimitException(errorMessage);
            case ResponseCodes.UNSUPPORTED_DIRECTION:
                throw new UnsupportedDirectionException(errorMessage);
            case ResponseCodes.INVALID_API_KEY:
            case ResponseCodes.BLOCKED_API_KEY:
                Log.e(TAG, errorMessage);
                throw new TranslateServiceException(errorMessage);
            default:
                return response;
        }
    }
}
