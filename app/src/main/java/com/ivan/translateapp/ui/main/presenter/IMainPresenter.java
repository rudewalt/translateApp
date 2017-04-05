package com.ivan.translateapp.ui.main.presenter;

import android.support.annotation.NonNull;

import com.ivan.translateapp.ui.main.view.IMainView;
import com.jakewharton.rxbinding2.widget.TextViewAfterTextChangeEvent;
import com.jakewharton.rxbinding2.widget.TextViewTextChangeEvent;

import io.reactivex.Observable;

/**
 * Created by Ivan on 27.03.2017.
 */

public interface IMainPresenter {

    void bindView(IMainView iMainView);

    void unbindVIew();

    void loadLanguages();

    void listenText(String text, String fromLanguage, String toLanguage);

    void saveTranslation();

    void listenIsFavourite(boolean checked);
}
