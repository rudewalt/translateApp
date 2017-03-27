package com.ivan.translateapp.main.presenter;

import android.support.annotation.NonNull;

import com.ivan.translateapp.main.view.IMainView;
import com.jakewharton.rxbinding2.widget.TextViewTextChangeEvent;

import io.reactivex.Observable;

/**
 * Created by Ivan on 27.03.2017.
 */

public interface IMainPresenter {

    void bindView(IMainView iMainView);

    void unbindVIew();

    void loadLanguages();

    void listenText(@NonNull Observable<TextViewTextChangeEvent> textToTranslateListener);
}
