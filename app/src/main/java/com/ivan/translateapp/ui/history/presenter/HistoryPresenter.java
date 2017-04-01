package com.ivan.translateapp.ui.history.presenter;

import com.ivan.translateapp.domain.Translation;
import com.ivan.translateapp.domain.interactor.IHistoryInteractor;
import com.ivan.translateapp.ui.history.view.IHistoryView;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Ivan on 27.03.2017.
 */

public class HistoryPresenter implements IHistoryPresenter {

    private IHistoryInteractor iHistoryInteractor;
    private IHistoryView iHistoryView;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public HistoryPresenter(IHistoryInteractor iHistoryInteractor){
        this.iHistoryInteractor = iHistoryInteractor;
    }

    @Override
    public void bindView(IHistoryView iHistoryView) {
        this.iHistoryView = iHistoryView;
    }

    @Override
    public void unbindView() {
        iHistoryView = null;
        compositeDisposable.clear();
    }

    @Override
    public void loadHistory() {
        Disposable disposable = iHistoryInteractor.getHistory()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleSuccessLoadHistory, this::handleErrorLoadHistory);

        compositeDisposable.add(disposable);
    }

    private void handleSuccessLoadHistory(List<Translation> translations){
        iHistoryView.showHistory(translations);
    }

    private void handleErrorLoadHistory(Throwable throwable){
        iHistoryView.showError(throwable.getMessage());
    }

    @Override
    public void loadFavourites() {

    }
}
