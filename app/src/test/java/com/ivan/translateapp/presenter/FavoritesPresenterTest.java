package com.ivan.translateapp.presenter;

import com.ivan.translateapp.domain.Translation;
import com.ivan.translateapp.domain.interactor.IHistoryInteractor;
import com.ivan.translateapp.ui.presenter.FavoritesPresenter;
import com.ivan.translateapp.ui.view.ITranslationListView;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class FavoritesPresenterTest {

    @Mock
    IHistoryInteractor mockHistoryInteractor;
    @Mock
    ITranslationListView mockTranslationListView;

    private FavoritesPresenter favoritesPresenter;


    @Before
    public void setUp() {
        favoritesPresenter = new FavoritesPresenter(mockHistoryInteractor);

        RxJavaPlugins.setIoSchedulerHandler(
                scheduler -> Schedulers.trampoline());
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(
                scheduler -> Schedulers.trampoline());
    }

    @After
    public void finish() {
        RxJavaPlugins.reset();
        RxAndroidPlugins.reset();
    }

    @Test
    public void loadTranslations_shouldShowTranslationsOnSuceess() {
        //given
        List<Translation> translations = new ArrayList<>();
        favoritesPresenter.bindView(mockTranslationListView);
        given(mockHistoryInteractor.getFavorites()).willReturn(Single.just(translations));
        //when
        favoritesPresenter.loadTranslations();

        //then
        verify(mockTranslationListView).showTranslations(translations);
    }

    @Test
    public void loadTranslations_shouldShowError() {
        //given
        Exception exception = new Exception();
        favoritesPresenter.bindView(mockTranslationListView);
        given(mockHistoryInteractor.getFavorites()).willReturn(Single.error(exception));
        //when
        favoritesPresenter.loadTranslations();

        //then
        verify(mockTranslationListView).showError(exception.getLocalizedMessage());
    }
}
