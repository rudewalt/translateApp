package com.ivan.translateapp.ui.presenter;

import android.util.Log;

import com.ivan.translateapp.domain.Language;
import com.ivan.translateapp.domain.Translation;
import com.ivan.translateapp.domain.interactor.IMainInteractor;
import com.ivan.translateapp.ui.view.main.IMainView;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Ivan on 27.03.2017.
 */

public class MainPresenter implements IMainPresenter {

    private static final String TAG = MainPresenter.class.toString();

    private IMainInteractor iMainInteractor;
    private IMainView iMainView;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public MainPresenter(IMainInteractor iMainInteractor) {

        this.iMainInteractor = iMainInteractor;
    }

    @Override
    public void bindView(IMainView iMainView) {
        this.iMainView = iMainView;
    }

    @Override
    public void unbindVIew() {
        iMainView = null;
        compositeDisposable.clear();
    }

    @Override
    public void loadLanguages() {
        Disposable disposable =
                Observable.zip(
                        iMainInteractor.getLanguages(),
                        iMainInteractor.restoreTranslationDirection(),
                        (languages, restored) -> new LanguagesWithSettings(languages, restored))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::handleSuccessLanguages, this::handleErrorGetLanguagesError);

        compositeDisposable.add(disposable);
    }

    @Override
    public void listenText(String text, String fromLanguage, String toLanguage) {
        Disposable disposable = iMainInteractor.translateText(text, fromLanguage, toLanguage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleSuccessTranslate, this::handleErrorTranslate);

        compositeDisposable.add(disposable);
    }

    @Override
    public void clickIsFavouriteCheckbox(Translation translation) {
        saveTranslation(translation);
    }

    @Override
    public void textToTranslateLostFocus(Translation translation) {

        saveTranslation(translation);
        iMainInteractor.saveTranslationDirection(translation.getFromLanguage(), translation.getToLanguage());
    }


    private void handleSuccessLanguages(LanguagesWithSettings data) {
        iMainView.setLanguages(data.getLanguages());

        iMainView.setFromLanguage(data.getFromLanguage());
        iMainView.setToLanguage(data.getToLanguage());
    }

    private void handleErrorGetLanguagesError(Throwable throwable) {
        String message = throwable.getMessage();
        iMainView.showError(message);
    }

    private void handleSuccessTranslate(Translation translation) {
        iMainView.setTranslatedText(translation.getTranslated());
        iMainView.setStateIsFavouriteCheckbox(translation.isFavourite());
    }

    private void handleErrorTranslate(Throwable throwable) {

        Log.e(TAG, throwable.getMessage());
    }

    private void saveTranslation(Translation translation) {
        iMainInteractor.saveTranslation(translation);
    }

    private class LanguagesWithSettings {
        private List<Language> languages;
        private String fromLanguage;
        private String toLanguage;

        public LanguagesWithSettings(List<Language> languages, List<String> savedSelection) {
            this.languages = languages;
            if (savedSelection != null && savedSelection.size() == 2) {
                this.fromLanguage = savedSelection.get(0);
                this.toLanguage = savedSelection.get(1);
            }
        }

        public List<Language> getLanguages() {
            return languages;
        }

        public String getFromLanguage() {
            return fromLanguage;
        }

        public String getToLanguage() {
            return toLanguage;
        }
    }
}
