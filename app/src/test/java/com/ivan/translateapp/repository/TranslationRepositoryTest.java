package com.ivan.translateapp.repository;

import com.ivan.translateapp.data.net.ITranslateService;
import com.ivan.translateapp.data.repository.TranslationRepository;
import com.ivan.translateapp.domain.Language;
import com.ivan.translateapp.domain.Translation;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

/**
 * Created by Ivan on 16.04.2017.
 */

@RunWith(MockitoJUnitRunner.class)
public class TranslationRepositoryTest {

    private TranslationRepository translationRepository;

    @Mock
    ITranslateService mockTranslateService;

    @Before
    public void setUp(){
        translationRepository = new TranslationRepository(mockTranslateService);
    }

    @Test
    public void getTranslation_shouldReturnTranslation(){
        //given
        String text = "text";
        String fromLanguage="en";
        String toLanguage = "ru";
        String translated = "текст";

        given(mockTranslateService.translate(text,fromLanguage,toLanguage))
                .willReturn(Observable.just(new Translation(text,translated,fromLanguage,toLanguage,false,false)));

        //when
        Translation result = translationRepository.getTranslation(text,fromLanguage,toLanguage).blockingFirst();

        //then
        assertThat(result).isNotNull();
        assertThat(result.getText()).isEqualTo(text);
    }

    @Test
    public void getLanguages_shouldReturnLanguages(){
        //given
        String ui = "test ui";
        given(mockTranslateService.getLanguages(ui)).willReturn(Observable.just(new ArrayList<>()));

        //when
        List<Language> result = translationRepository.getLanguages(ui).blockingFirst();

        //then
        assertThat(result).isNotNull();
    }
}
