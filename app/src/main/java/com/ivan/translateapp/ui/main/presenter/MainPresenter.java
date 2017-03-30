package com.ivan.translateapp.ui.main.presenter;

import android.support.annotation.NonNull;
import android.util.Log;

import com.ivan.translateapp.domain.Language;
import com.ivan.translateapp.domain.Translation;
import com.ivan.translateapp.domain.interactor.IMainInteractor;
import com.ivan.translateapp.ui.main.view.IMainView;
import com.jakewharton.rxbinding2.widget.TextViewAfterTextChangeEvent;
import com.jakewharton.rxbinding2.widget.TextViewTextChangeEvent;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Ivan on 27.03.2017.
 */

public class MainPresenter implements IMainPresenter {

    private static final String TAG = MainPresenter.class.toString();

    private IMainInteractor iMainInteractor;

    private IMainView iMainView;

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
    }

    @Override
    public void loadLanguages() {
        iMainInteractor.getLanguages()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleSuccessetLanguages, this::handleErrorGetLanguagesError);
    }

    private void handleSuccessetLanguages(List<Language> languages) {
        iMainView.loadLanguages(languages);
    }

    private void handleErrorGetLanguagesError(Throwable throwable) {
        iMainView.showError("");
    }

    @Override
    public void listenText(String text, String fromLanguage, String toLanguage) {
        iMainInteractor.translateText(text, fromLanguage, toLanguage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleSuccessTranslate, this::handleErrorTranslate);
    }

    private void handleSuccessTranslate(Translation translation){
        iMainView.setTranslatedText(translation.getTranslated());
    }

    private void handleErrorTranslate(Throwable throwable){
        Log.e(TAG,throwable.getMessage());
    }
}
