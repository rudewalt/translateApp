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

import io.reactivex.Single;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class HistoryInteractorTest {

    private IHistoryInteractor historyInteractor;

    @Mock
    HistoryRepository mockHistoryRepository;

    @Before
    public void setUp() {
        historyInteractor = new HistoryInteractor(mockHistoryRepository);
    }

    @Test
    public void getHistory_shouldReturnTranslationHistory(){
        //given
        ArrayList<Translation> testTranslations = getTestTranslations();
        given(mockHistoryRepository.getHistory()).willReturn(Single.just(testTranslations));

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

    @Test
    public void saveChanges_ShouldSaveTranslation(){
        //given
        Translation translation = new Translation();

        //when
        historyInteractor.saveChanges(translation);

        //then
        verify(mockHistoryRepository).update(translation);
    }

    @Test
    public void delete_shouldDeleteTranslation(){
        //given
        Translation translation  = new Translation();

        //when
        historyInteractor.delete(translation);

        //then
        verify(mockHistoryRepository).delete(translation);
    }
}
