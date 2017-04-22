package com.ivan.translateapp.ui.presenter;

import com.ivan.translateapp.ui.view.IView;

/**
 * Базовый презентер
 */
public interface IPresenter<T extends IView> {

    void bindView(T view);

    void unbindView();
}
