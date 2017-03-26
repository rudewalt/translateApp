package com.ivan.translateapp.data;

import com.ivan.translateapp.data.dto.SupportedLanguagesDTO;
import com.ivan.translateapp.data.net.yandex.YandexTranslateApiInterface;
import com.ivan.translateapp.data.net.yandex.YandexTranslateApiModule;

import org.junit.Test;

import io.reactivex.Maybe;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        YandexTranslateApiInterface apiInterface =  YandexTranslateApiModule.getApiInterface();
        Maybe<SupportedLanguagesDTO> response =  apiInterface.getLanguages("ui").firstElement();
    }
}