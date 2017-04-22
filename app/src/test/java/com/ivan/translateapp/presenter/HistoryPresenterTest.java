package com.ivan.translateapp.presenter;

import com.ivan.translateapp.domain.Translation;
import com.ivan.translateapp.domain.interactor.IHistoryInteractor;
import com.ivan.translateapp.ui.presenter.HistoryPresenter;
import com.ivan.translateapp.ui.view.ITranslationListView;

import org.junit.After;
import org.junit.Before;
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
public class HistoryPresenterTest {

    @Mock
    IHistoryInteractor mockHistoryInteractor;
    @Mock
    ITranslationListView mockTranslationListView;

    private HistoryPresenter historyPresenter;

    @Before
    public void setUp() {
        historyPresenter = new HistoryPresenter(mockHistoryInteractor);

        RxJavaPlugins.setIoSchedulerHandler(
                scheduler -> Schedulers.trampoline());
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(
                scheduler -> Schedulers.trampoline());
    }

    @After
    public void finish(){
        RxJavaPlugins.reset();
        RxAndroidPlugins.reset();
    }

    @org.junit.Test
    public void loadTranslations_shouldShowTranslationsOnSuceess() {
        //given
        List<Translation> translations = new ArrayList<>();
        historyPresenter.bindView(mockTranslationListView);
        given(mockHistoryInteractor.getHistory()).willReturn(Single.just(translations));
        //when
        historyPresenter.loadTranslations();

        //then
        verify(mockTranslationListView).showTranslations(translations);
    }

    @org.junit.Test
    public void loadTranslations_shouldShowError() {
        //given
        Exception exception = new Exception();
        historyPresenter.bindView(mockTranslationListView);
        given(mockHistoryInteractor.getHistory()).willReturn(Single.error(exception));
        //when
        historyPresenter.loadTranslations();

        //then
        verify(mockTranslationListView).showError(exception.getLocalizedMessage());
    }

}