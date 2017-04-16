package com.ivan.translateapp.interactor;

import com.ivan.translateapp.data.repository.IHistoryRepository;
import com.ivan.translateapp.domain.Translation;
import com.ivan.translateapp.domain.interactor.FavoritesInteractor;

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

@RunWith(MockitoJUnitRunner.class)
public class FavoritesInteractorTest {

    private FavoritesInteractor favoritesInteractor;

    @Mock
    IHistoryRepository iHistoryRepository;

    @Before
    public void setUp(){
        favoritesInteractor = new FavoritesInteractor(iHistoryRepository);
    }

    @Test
    public void getFavorites_shouldReturnFavorites(){
        //given
        List<Translation> testTranslations = getTranslations();
        given(iHistoryRepository.getFavorites()).willReturn(Observable.fromArray(testTranslations));

        //when
        List<Translation> result = favoritesInteractor.getFavorites().blockingFirst();

        //then
        assertThat(result).isNotEmpty();
        assertThat(result.size()).isEqualTo(testTranslations.size());
    }

    private List<Translation> getTranslations(){
        return new ArrayList<Translation>(){{
            add(new Translation());
            add(new Translation());
            add(new Translation());
            add(new Translation());
        }};
    }

}
