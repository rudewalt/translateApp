package com.ivan.translateapp.ui.main.presenter;

import android.util.Log;

import com.ivan.translateapp.domain.Language;
import com.ivan.translateapp.domain.Translation;
import com.ivan.translateapp.domain.interactor.IMainInteractor;
import com.ivan.translateapp.ui.main.view.IMainView;

import java.util.List;

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
        Disposable disposable = iMainInteractor.getLanguages()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleSuccessetLanguages, this::handleErrorGetLanguagesError);

        compositeDisposable.add(disposable);
    }

    private void handleSuccessetLanguages(List<Language> languages) {
        iMainView.loadLanguages(languages);
    }

    private void handleErrorGetLanguagesError(Throwable throwable) {
        String message = throwable.getMessage();
        iMainView.showError("");
    }

    @Override
    public void listenText(String text, String fromLanguage, String toLanguage) {
        Disposable disposable = iMainInteractor.translateText(text, fromLanguage, toLanguage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleSuccessTranslate, this::handleErrorTranslate);

        compositeDisposable.add(disposable);
    }

    private void handleSuccessTranslate(Translation translation) {

        iMainView.setTranslatedText(translation.getTranslated());
    }

    private void handleErrorTranslate(Throwable throwable) {

        Log.e(TAG, throwable.getMessage());
    }
}
