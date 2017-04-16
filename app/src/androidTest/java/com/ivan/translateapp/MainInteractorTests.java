package com.ivan.translateapp;

import com.ivan.translateapp.data.repository.IHistoryRepository;
import com.ivan.translateapp.data.repository.ISettingsRepository;
import com.ivan.translateapp.data.repository.ITranslationRepository;
import com.ivan.translateapp.domain.Language;
import com.ivan.translateapp.domain.interactor.MainInteractor;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Locale;

import io.reactivex.Observable;

import static org.mockito.BDDMockito.given;


@RunWith(MockitoJUnitRunner.class)
public class MainInteractorTests {

    private MainInteractor mainInteractor;

    private static final String UI = "en";

    @Mock private Observable<List<Language>> mockLanguages;
    @Mock private ITranslationRepository mockTranslationRepository;
    @Mock private IHistoryRepository mockHistoryRepository;
    @Mock private ISettingsRepository mockSettingsRepository;
    @Mock private Locale mockLocale;


    @Before
    public void setUp()  {
        mainInteractor = new MainInteractor(mockTranslationRepository,mockHistoryRepository,mockSettingsRepository,mockLocale);
    }

    @Test
    public void getLanguagesTest(){
        //given
        given(mockTranslationRepository.getLanguages(UI)).willReturn(mockLanguages);

        //when
        Observable<List<Language>> languages = mainInteractor.getLanguages();

    }
}
