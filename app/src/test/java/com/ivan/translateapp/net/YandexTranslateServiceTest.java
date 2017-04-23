package com.ivan.translateapp.net;

import com.ivan.translateapp.data.net.exception.TranslateServiceException;
import com.ivan.translateapp.data.net.yandex.ErrorCodes;
import com.ivan.translateapp.data.net.yandex.IYandexTranslateApiInterface;
import com.ivan.translateapp.data.net.yandex.YandexTranslateService;
import com.ivan.translateapp.data.net.yandex.mapper.LanguageResponseMapper;
import com.ivan.translateapp.data.net.yandex.mapper.SupportedLanguagesResponseMapper;
import com.ivan.translateapp.data.net.yandex.response.SupportedLanguagesResponse;
import com.ivan.translateapp.domain.Language;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Single;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class YandexTranslateServiceTest {

    @Mock
    IYandexTranslateApiInterface mockApiInterface;
    @Mock
    LanguageResponseMapper mockLanguageResponseMapper;
    @Mock
    SupportedLanguagesResponseMapper mockSupportedLanguagesResponseMapper;
    @Mock


    private YandexTranslateService yandexTranslateService;

    @Before
    public void setUp() {
        yandexTranslateService = new YandexTranslateService(mockApiInterface,
                mockLanguageResponseMapper, mockSupportedLanguagesResponseMapper);
    }

    @Test
    public void getLanguages_shouldReturnDataOnSuccess() throws Exception {
        //given
        String someUi = "ui";
        SupportedLanguagesResponse response = getTestResponse(null);
        given(mockApiInterface.getLanguages(someUi)).willReturn(Single.just(response));
        given(mockSupportedLanguagesResponseMapper.apply(response)).willReturn(getLanguages());

        //when
        List<Language> result = yandexTranslateService.getLanguages(someUi).blockingGet();

        //then
        assertThat(result).isNotNull();
        assertThat(result).isNotEmpty();
    }

    @Test
    public void getLanguages_shouldThrowExceptionOnErrorResponceCode() throws Exception {

        //given
        String someUi = "ui";
        SupportedLanguagesResponse response = getTestResponse(ErrorCodes.CANT_TRANSLATE);
        given(mockApiInterface.getLanguages(someUi)).willReturn(Single.just(response));

        try {
            //when
            List<Language> result = yandexTranslateService.getLanguages(someUi).blockingGet();
        } catch (Throwable throwable) {
            //then
            assertThat(throwable.getCause()).isInstanceOf(TranslateServiceException.class);
        }

    }

    private SupportedLanguagesResponse getTestResponse(Integer code) {
        SupportedLanguagesResponse response = new SupportedLanguagesResponse();
        response.setDirs(new ArrayList<String>() {{
            add("ru-en");
            add("ru-pl");
            add("ru-hu");
        }});

        response.setLangs(new HashMap<String, String>() {{
            put("ru", "русский");
            put("en", "английский");
            put("pl", "польский");
        }});

        response.setCode(code);

        return response;
    }

    private List<Language> getLanguages() {
        return new ArrayList<Language>() {{
            add(new Language("русский", "ru"));
            add(new Language("английский", "en"));
            add(new Language("польский", "pl"));
        }};
    }
}