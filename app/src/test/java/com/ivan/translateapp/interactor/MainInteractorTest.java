package com.ivan.translateapp.interactor;

import com.ivan.translateapp.data.repository.IHistoryRepository;
import com.ivan.translateapp.data.repository.ISettingsRepository;
import com.ivan.translateapp.data.repository.ITranslationRepository;
import com.ivan.translateapp.domain.Language;
import com.ivan.translateapp.domain.Translation;
import com.ivan.translateapp.domain.interactor.MainInteractor;
import com.ivan.translateapp.utils.ConnectivityUtils;

import org.assertj.core.api.Condition;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.reactivex.Single;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class MainInteractorTest {
    private static final String UI = "en";
    private MainInteractor mainInteractor;

    @Mock
    private ITranslationRepository mockTranslationRepository;
    @Mock
    private IHistoryRepository mockHistoryRepository;
    @Mock
    private ISettingsRepository mockSettingsRepository;
    @Mock
    private ConnectivityUtils connectivityUtils;


    @Before
    public void setUp() {
        Locale locale = new Locale("en");
        mainInteractor = new MainInteractor(mockTranslationRepository, mockHistoryRepository, mockSettingsRepository, locale, connectivityUtils);
        given(connectivityUtils.isOnline()).willReturn(true);
    }

    @Test
    public void getLanguages_shouldReturnLanguages() {
        //given
        given(mockTranslationRepository.getLanguages(UI)).willReturn(getUnsortedLanguages());

        //when
        List<Language> languages = mainInteractor.getLanguages().blockingGet();

        //then
        assertThat(languages).isNotEmpty();
    }

    @Test
    public void getLanguages_shouldReturnSortedListOfLanguages() {
        //given
        given(mockTranslationRepository.getLanguages(UI)).willReturn(getUnsortedLanguages());

        //when
        List<Language> languages = mainInteractor.getLanguages().blockingGet();

        //then
        assertThat(languages).is(inAscSorting());
    }

    private  Condition<List<? extends Language>> inAscSorting(){
        return new Condition<List<? extends Language>>() {
            @Override
            public boolean matches(List<? extends Language> languages) {
                for (int i = 0; i < languages.size() - 1; i++) {
                    if (languages.get(i).getTitle().compareTo(languages.get(i + 1).getTitle()) > 0)
                        return false;
                }

                return true;
            }
        };
    }

    private Single<List<Language>> getUnsortedLanguages() {
        List<Language> languages = new ArrayList<Language>() {{
            add(new Language("Русский", "ru"));
            add(new Language("Арабский", "ar"));
            add(new Language("Французский", "fr"));
        }};

        return Single.just(languages);
    }

    @Test
    public void translateText_shouldReturnNotFavoriteTranslation() {
        //given
        String text = "text";
        String fromLanguage = "en";
        String toLanguage = "ru";
        String translated = "текст";
        Boolean isHistory = false;
        Boolean isFavorite = false;

        given(mockTranslationRepository.getTranslation(text, fromLanguage, toLanguage))
                .willReturn(Single.just(new Translation(text, translated, fromLanguage, toLanguage, isHistory, isFavorite)));

        given(mockHistoryRepository.isFavourite(text, fromLanguage, toLanguage))
                .willReturn(Single.just(false));

        //when
        Single<Translation> result = mainInteractor.translateText(text, fromLanguage, toLanguage);
        Translation resultTranslation = result.blockingGet();

        //then
        assertThat(result).isNotNull();
        assertThat(resultTranslation.isFavorite()).isEqualTo(isFavorite);
        assertThat(resultTranslation.isHistory()).isEqualTo(isHistory);
        assertThat(resultTranslation.getText()).isEqualTo(text);
        assertThat(resultTranslation.getTranslated()).isEqualTo(translated);
    }

    @Test
    public void translateText_shouldReturnFavoriteTranslation() {
        //given
        String text = "text";
        String fromLanguage = "en";
        String toLanguage = "ru";
        String translated = "текст";
        Boolean isHistory = true;
        Boolean isFavorite = false;

        Boolean isFavoriteExpected = true;

        given(mockTranslationRepository.getTranslation(text, fromLanguage, toLanguage))
                .willReturn(Single.just(new Translation(text, translated, fromLanguage, toLanguage, isHistory, isFavorite)));

        given(mockHistoryRepository.isFavourite(text, fromLanguage, toLanguage))
                .willReturn(Single.just(isFavoriteExpected));

        //when
        Single<Translation> result = mainInteractor.translateText(text, fromLanguage, toLanguage);
        Translation resultTranslation = result.blockingGet();

        //then
        assertThat(result).isNotNull();
        assertThat(resultTranslation.isFavorite()).isEqualTo(isFavoriteExpected);
        assertThat(resultTranslation.isHistory()).isEqualTo(isHistory);
        assertThat(resultTranslation.getText()).isEqualTo(text);
        assertThat(resultTranslation.getTranslated()).isEqualTo(translated);
    }

    @Test
    public void restoreTranslationDirection_shouldReturnValues() {
        //given
        String key1 = "fromLanguage";
        String key2 = "toLanguage";
        String fromLanguage = "en";
        String toLanguage = "ru";
        given(mockSettingsRepository.getValue(key1)).willReturn(Single.just(fromLanguage));
        given(mockSettingsRepository.getValue(key2)).willReturn(Single.just(toLanguage));

        //when
        List<String> result = mainInteractor.restoreTranslationDirection().blockingGet();

        //then
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0)).isEqualTo(fromLanguage);
        assertThat(result.get(1)).isEqualTo(toLanguage);
    }

    @Test
    public void restoreTranslationDirection_shouldReturnEmptyList() {
        //given
        String key1 = "fromLanguage";
        String key2 = "toLanguage";
        String empty = "";
        given(mockSettingsRepository.getValue(key1)).willReturn(Single.just(empty));
        given(mockSettingsRepository.getValue(key2)).willReturn(Single.just(empty));

        //when
        List<String> result = mainInteractor.restoreTranslationDirection().blockingGet();

        //then
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(0);
    }

}
