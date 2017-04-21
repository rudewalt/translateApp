package com.ivan.translateapp;

import com.ivan.translateapp.data.repository.IHistoryRepository;
import com.ivan.translateapp.data.repository.ISettingsRepository;
import com.ivan.translateapp.data.repository.ITranslationRepository;
import com.ivan.translateapp.domain.Language;
import com.ivan.translateapp.domain.interactor.MainInteractor;
import com.ivan.translateapp.utils.ConnectivityUtils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Locale;

import io.reactivex.Observable;
import io.reactivex.Single;

import static org.mockito.BDDMockito.given;


@RunWith(MockitoJUnitRunner.class)
public class MainInteractorTests {

    private MainInteractor mainInteractor;

    private static final String UI = "en";

    @Mock private Single<List<Language>> mockLanguages;
    @Mock private ITranslationRepository mockTranslationRepository;
    @Mock private IHistoryRepository mockHistoryRepository;
    @Mock private ISettingsRepository mockSettingsRepository;
    @Mock private Locale mockLocale;
    @Mock private ConnectivityUtils mockConnectivityUtils;


    @Before
    public void setUp()  {
        mainInteractor = new MainInteractor(mockTranslationRepository,mockHistoryRepository,
                mockSettingsRepository,mockLocale,mockConnectivityUtils);
    }

    @Test
    public void getLanguagesTest(){
        //given
        given(mockTranslationRepository.getLanguages(UI)).willReturn(mockLanguages);

        //when
        Single<List<Language>> languages = mainInteractor.getLanguages();

    }
}
