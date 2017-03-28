package com.ivan.translateapp.ui.main.presenter;

import android.support.annotation.NonNull;

import com.ivan.translateapp.domain.Language;
import com.ivan.translateapp.domain.interactor.IMainInteractor;
import com.ivan.translateapp.ui.main.view.IMainView;
import com.jakewharton.rxbinding2.widget.TextViewTextChangeEvent;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Ivan on 27.03.2017.
 */

public class MainPresenter implements IMainPresenter {

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
                .subscribe(this::getLanguagesSuccess, this::getLanguagesError);
    }

    private void getLanguagesSuccess(List<Language> languages) {
        iMainView.loadLanguages(languages);
    }

    private void getLanguagesError(Throwable throwable) {
        iMainView.showError("");
    }

    @Override
    public void listenText(@NonNull Observable<TextViewTextChangeEvent> textToTranslateListener) {

    }
}
