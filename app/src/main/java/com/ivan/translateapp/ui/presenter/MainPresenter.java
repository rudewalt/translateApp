package com.ivan.translateapp.ui.presenter;

import android.util.Log;

import com.ivan.translateapp.domain.Language;
import com.ivan.translateapp.domain.Translation;
import com.ivan.translateapp.domain.interactor.IMainInteractor;
import com.ivan.translateapp.ui.view.main.IMainView;

import java.util.List;

import io.reactivex.Completable;
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
    public void unbindView() {
        iMainView = null;
        compositeDisposable.clear();
    }

    @Override
    public void loadLanguages() {
        Disposable disposable =
                Observable.zip(
                        iMainInteractor.getLanguages(),
                        iMainInteractor.restoreTranslationDirection(), LanguagesWithSettings::new)
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
    public void loadChanges(Translation translation) {
        Disposable disposable = iMainInteractor.isFavorite(translation)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleSuccessCheckIsFavorite, this::handleErrorCheckIsFavorite);

        compositeDisposable.add(disposable);
    }

    @Override
    public void clickIsFavouriteCheckbox(Translation translation) {
        saveTranslation(translation);
    }

    @Override
    public void textToTranslateLostFocus(Translation translation) {
        saveTranslation(translation);
        saveTranslationDirection(translation.getFromLanguage(), translation.getToLanguage());
    }

    @Override
    public void openFromAnotherFragment(Translation translation) {
        iMainView.setText(translation.getText());
        iMainView.setTranslatedText(translation.getTranslated());
        iMainView.setFromLanguage(translation.getFromLanguage());
        iMainView.setToLanguage(translation.getToLanguage());
        iMainView.setStateIsFavoriteCheckbox(translation.isFavorite());
        iMainView.showIsFavoriteCheckbox();
        iMainView.showClearButton();
    }

    @Override
    public void viewHidden(Translation translation) {
        saveTranslation(translation);
        saveTranslationDirection(translation.getFromLanguage(), translation.getToLanguage());
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
        iMainView.setStateIsFavoriteCheckbox(translation.isFavorite());
    }

    private void handleSuccessCheckIsFavorite(Boolean isFavorite){
        iMainView.setStateIsFavoriteCheckbox(isFavorite);
    }

    private void handleErrorCheckIsFavorite(Throwable throwable){
        String message = throwable.getMessage();
        iMainView.showError(message);
    }

    private void handleErrorTranslate(Throwable throwable) {

        Log.e(TAG, throwable.getMessage());
    }

    private void saveTranslation(Translation translation) {

        Disposable disposable = iMainInteractor.saveTranslation(translation)
                .subscribeOn(Schedulers.io())
                .subscribe();

        compositeDisposable.add(disposable);
    }

    private void saveTranslationDirection(String fromLanguage, String toLanguage){
        Disposable disposable = iMainInteractor.saveTranslationDirection(fromLanguage, toLanguage)
                .subscribeOn(Schedulers.io())
                .subscribe();

        compositeDisposable.add(disposable);
    }

    private class LanguagesWithSettings {
        private List<Language> languages;
        private String fromLanguage;
        private String toLanguage;

        public LanguagesWithSettings(List<Language> languages, List<String> savedSelection) {
            final int size = 2;
            this.languages = languages;
            if (savedSelection != null && savedSelection.size() == size) {
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
