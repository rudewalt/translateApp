package com.ivan.translateapp.ui.presenter;

import com.ivan.translateapp.domain.Translation;
import com.ivan.translateapp.domain.interactor.IHistoryInteractor;
import com.ivan.translateapp.ui.view.ITranslationListView;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Базовый презентер для управления фрагментами со списком переводов
 */
abstract class BaseTranslationListPresenter implements ITranslationListViewPresenter {

    private ITranslationListView listView;
    private IHistoryInteractor iHistoryInteractor;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    BaseTranslationListPresenter(IHistoryInteractor iHistoryInteractor) {
        this.iHistoryInteractor = iHistoryInteractor;
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void bindView(ITranslationListView view) {
        if(this.listView != null)
            throw new IllegalStateException("Already binded");

        this.listView = view;
    }

    @Override
    public void unbindView() {
        this.iHistoryInteractor = null;
        compositeDisposable.clear();
    }

    @Override
    public abstract void loadTranslations();

    @Override
    public void isFavoriteCheckboxStateChanged(Translation translation) {
        Disposable disposable = iHistoryInteractor.saveChanges(translation)
                .subscribeOn(Schedulers.io())
                .subscribe();

        compositeDisposable.add(disposable);
    }

    @Override
    public void clickOnTranslation(Translation translation) {
        listView.openMainView(translation);
    }

    protected void loadHistory() {
        Disposable disposable = iHistoryInteractor.getHistory()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleSuccessLoadTranslations, this::handleErrorLoadFavorites);

        compositeDisposable.add(disposable);
    }

    protected void loadFavorites() {
        Disposable disposable = iHistoryInteractor.getFavorites()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleSuccessLoadTranslations, this::handleErrorLoadFavorites);

        compositeDisposable.add(disposable);
    }

    private void handleSuccessLoadTranslations(List<Translation> translations) {
        listView.showTranslations(translations);
    }

    private void handleErrorLoadFavorites(Throwable throwable) {
        listView.showError(throwable.getLocalizedMessage());
    }
}
