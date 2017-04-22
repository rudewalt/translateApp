package com.ivan.translateapp.presenter;

import com.ivan.translateapp.data.net.exception.TranslateServiceException;
import com.ivan.translateapp.domain.Language;
import com.ivan.translateapp.domain.Translation;
import com.ivan.translateapp.domain.exception.NoInternetConnectionException;
import com.ivan.translateapp.domain.interactor.IMainInteractor;
import com.ivan.translateapp.ui.presenter.MainPresenter;
import com.ivan.translateapp.ui.view.main.IMainView;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class MainPresenterTest {

    @Mock
    IMainInteractor mockMainInteractor;

    @Mock
    IMainView mockMainView;

    private MainPresenter mainPresenter;

    @Before
    public void setUp() {
        mainPresenter = new MainPresenter(mockMainInteractor);

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
    public void bindView_shouldBindView() {
        mainPresenter.bindView(mockMainView);
        assertThat(mainPresenter.view()).isSameAs(mockMainView);
    }

    @Test
    public void bindView_shouldThrowException() {
        //given
        mainPresenter.bindView(mockMainView);


        try {
            //when
            mainPresenter.bindView(mockMainView);
        } catch (IllegalStateException exception) {
            //then
            assertThat(exception).hasMessage("Already binded");
        }
    }

    @Test
    public void unbindView_shouldSetNullToView() {
        //given
        mainPresenter.bindView(mockMainView);

        //when
        mainPresenter.unbindView();

        //then
        assertThat(mainPresenter.view()).isNull();
    }

    @Test
    public void loadLanguages_shouldLoadLanguagesOnSuccess() {
        //given
        List<Language> testLanguages = getTestLanguages();
        List<String> resoredLanguages = getResoredLanguages();
        mainPresenter.bindView(mockMainView);
        given(mockMainInteractor.getLanguages()).willReturn(Single.just(testLanguages));
        given(mockMainInteractor.restoreTranslationDirection()).willReturn(Single.just(resoredLanguages));

        //when
        mainPresenter.loadLanguages();

        //then
        verify(mockMainView).setLanguages(testLanguages);
        verify(mockMainView).setFromLanguage(resoredLanguages.get(0));
        verify(mockMainView).setToLanguage(resoredLanguages.get(1));
    }

    public void loadLanguages_shouldShowErrorOnInternetConnectionError() {
        //given
        NoInternetConnectionException exception = new NoInternetConnectionException();
        List<String> resoredLanguages = getResoredLanguages();
        mainPresenter.bindView(mockMainView);
        given(mockMainInteractor.getLanguages()).willReturn(Single.error(exception));
        given(mockMainInteractor.restoreTranslationDirection()).willReturn(Single.just(resoredLanguages));

        //when
        mainPresenter.loadLanguages();

        //then
        verify(mockMainView).showInternetConnectionError();
    }

    private List<Language> getTestLanguages() {
        return new ArrayList<Language>() {{
            add(new Language("Тестовый 1", "t2"));
            add(new Language("Тестовый 2", "t1"));
        }};
    }

    private List<String> getResoredLanguages() {
        return new ArrayList<String>() {{
            add("test 1");
            add("test 2");
        }};
    }

    @Test
    public void loadLanguages_shouldShowErrorOnApiError() {
        //given
        int errCode = 1;
        TranslateServiceException exception = new TranslateServiceException(errCode);
        List<String> resoredLanguages = getResoredLanguages();
        mainPresenter.bindView(mockMainView);
        given(mockMainInteractor.getLanguages()).willReturn(Single.error(exception));
        given(mockMainInteractor.restoreTranslationDirection()).willReturn(Single.just(resoredLanguages));

        //when
        mainPresenter.loadLanguages();

        //then
        verify(mockMainView).showError(exception.getMessageResName(), exception.getDescriptionResName());
    }

    @Test
    public void listenText_shouldShowTranslationOnSuccess() {
        //given
        String text = "someText";
        String fromLanguage = "lang1";
        String toLanguage = "lang2";
        String translated = "someTranslation";
        boolean isHistory = false;
        boolean isFavorite = false;
        mainPresenter.bindView(mockMainView);
        given(mockMainInteractor.translateText(text, fromLanguage, toLanguage))
                .willReturn(Single.just(new Translation(text, translated, fromLanguage, toLanguage, isHistory, isFavorite)));

        //when
        mainPresenter.listenText(text, fromLanguage, toLanguage);

        //then
        verify(mockMainView).showProgress();
        verify(mockMainView).setTranslatedText(translated);
        verify(mockMainView).showIsFavoriteCheckbox();
        verify(mockMainView).setStateIsFavoriteCheckbox(isFavorite);
        verify(mockMainView).hideProgress();
    }

    @Test
    public void listenText_shouldShowErrorOnError() {
        //given
        String text = "someText";
        String fromLanguage = "lang1";
        String toLanguage = "lang2";
        mainPresenter.bindView(mockMainView);
        given(mockMainInteractor.translateText(text, fromLanguage, toLanguage))
                .willReturn(Single.error(new NoInternetConnectionException()));

        //when
        mainPresenter.listenText(text, fromLanguage, toLanguage);

        //then
        verify(mockMainView).showProgress();
        verify(mockMainView).showInternetConnectionError();
        verify(mockMainView).hideProgress();
    }

    @Test
    public void listenText_shouldShowErrorOnApiError() {
        //given
        String text = "someText";
        String fromLanguage = "lang1";
        String toLanguage = "lang2";
        int errorCode = 1;
        TranslateServiceException exception = new TranslateServiceException(errorCode);
        mainPresenter.bindView(mockMainView);
        given(mockMainInteractor.translateText(text, fromLanguage, toLanguage))
                .willReturn(Single.error(exception));

        //when
        mainPresenter.listenText(text, fromLanguage, toLanguage);

        //then
        verify(mockMainView).showProgress();
        verify(mockMainView).showError(exception.getMessageResName(), exception.getDescriptionResName());
        verify(mockMainView).hideProgress();
    }

    @Test
    public void loadChanges_shouldSetIsFavorite() {
        //given
        Translation translation = getTestTranslation();
        boolean isFavorite = true;
        mainPresenter.bindView(mockMainView);
        given(mockMainInteractor.isFavorite(translation)).willReturn(Single.just(isFavorite));
        //when
        mainPresenter.loadChanges(translation);

        //then
        verify(mockMainView).setStateIsFavoriteCheckbox(isFavorite);
    }

    @Test
    public void clickIsFavouriteCheckbox_shouldSaveTranslation() {
        //given
        Translation translation = getTestTranslation();
        given(mockMainInteractor.saveTranslation(translation)).willReturn(Completable.complete());
        //when
        mainPresenter.clickIsFavouriteCheckbox(translation);

        //then
        verify(mockMainInteractor).saveTranslation(translation);
    }

    private Translation getTestTranslation(){
        return new Translation("text", "translation",
                "language1", "language2", false, true);
    }

    @Test
    public void textToTranslateLostFocus_shouldSaveTranslation() {
        //given
        Translation translation = getTestTranslation();
        given(mockMainInteractor.saveTranslation(translation)).willReturn(Completable.complete());
        given(mockMainInteractor.saveTranslationDirection(translation.getFromLanguage(),translation.getToLanguage()))
                .willReturn(Completable.complete());
        mainPresenter.bindView(mockMainView);
        //when
        mainPresenter.textToTranslateLostFocus(translation);

        //then
        verify(mockMainInteractor).saveTranslation(translation);
        verify(mockMainInteractor).saveTranslationDirection(translation.getFromLanguage(), translation.getToLanguage());
    }

    @Test
    public void openFromAnotherFragment_shouldShowTranslation() {
        //given
        Translation translation = getTestTranslation();
        mainPresenter.bindView(mockMainView);
        //when
        mainPresenter.openFromAnotherFragment(translation);

        //then
        verify(mockMainView).setText(translation.getText());
        verify(mockMainView).setTranslatedText(translation.getTranslated());
        verify(mockMainView).setFromLanguage(translation.getFromLanguage());
        verify(mockMainView).setToLanguage(translation.getToLanguage());
        verify(mockMainView).setStateIsFavoriteCheckbox(translation.isFavorite());
        verify(mockMainView).showIsFavoriteCheckbox();
        verify(mockMainView).showClearButton();
    }

    @Test
    public void viewHidden_should() {
        //given
        Translation translation = getTestTranslation();
        mainPresenter.bindView(mockMainView);
        given(mockMainInteractor.saveTranslation(translation)).willReturn(Completable.complete());
        given(mockMainInteractor.saveTranslationDirection(translation.getFromLanguage(),translation.getToLanguage()))
                .willReturn(Completable.complete());
        //when
        mainPresenter.viewHidden(translation);

        //then
        verify(mockMainInteractor).saveTranslation(translation);
        verify(mockMainInteractor).saveTranslationDirection(translation.getFromLanguage(), translation.getToLanguage());
    }
}