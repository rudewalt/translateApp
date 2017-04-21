package com.ivan.translateapp.interactor;

import com.ivan.translateapp.data.repository.HistoryRepository;
import com.ivan.translateapp.domain.Translation;
import com.ivan.translateapp.domain.interactor.HistoryInteractor;
import com.ivan.translateapp.domain.interactor.IHistoryInteractor;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

/**
 * Created by Ivan on 16.04.2017.
 */

@RunWith(MockitoJUnitRunner.class)
public class HistoryInteractorTest {

    private IHistoryInteractor historyInteractor;

    @Mock
    HistoryRepository HistoryRepository;

    @Before
    public void setUp() {
        historyInteractor = new HistoryInteractor(HistoryRepository);
    }

    @Test
    public void getHistory_shouldReturnTranslationHistory(){
        //given
        ArrayList<Translation> testTranslations = getTestTranslations();
        given(HistoryRepository.getHistory()).willReturn(Single.just(testTranslations));

        //when
        List<Translation> result =  historyInteractor.getHistory().blockingGet();

        //then
        assertThat(result).isNotEmpty();
        assertThat(result.size()).isEqualTo(testTranslations.size());
    }

    private ArrayList<Translation> getTestTranslations(){
        return new ArrayList<Translation>(){{
            add(new Translation());
            add(new Translation());
            add(new Translation());
            add(new Translation());
        }};
    }
}
