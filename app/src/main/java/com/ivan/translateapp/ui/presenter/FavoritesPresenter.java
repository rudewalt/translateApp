package com.ivan.translateapp.ui.presenter;

import com.ivan.translateapp.domain.Translation;
import com.ivan.translateapp.domain.interactor.IFavoritesInteractor;
import com.ivan.translateapp.ui.view.favorites.IFavoritesView;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Ivan on 07.04.2017.
 */

//TODO избавиться от копипасты
public class FavoritesPresenter implements IFavoritesPresenter {

    private IFavoritesView iFavoritesView;
    private IFavoritesInteractor iFavoritesInteractor;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public FavoritesPresenter(IFavoritesInteractor iFavoritesInteractor){
        this.iFavoritesInteractor = iFavoritesInteractor;
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void bindView(IFavoritesView iFavoritesView) {
        this.iFavoritesView = iFavoritesView;
    }

    @Override
    public void unbindView() {
        this.iFavoritesInteractor = null;
        compositeDisposable.clear();
    }

    @Override
    public void loadFavorites() {
        Disposable disposable =  iFavoritesInteractor.getFavorites()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleSuccessLoadFavorites, this::handleErrorLoadFavorites);

        compositeDisposable.add(disposable);
    }

    @Override
    public void isFavoriteCheckboxStateChanged(Translation translation) {
        iFavoritesInteractor.save(translation);
    }

    private void handleSuccessLoadFavorites(List<Translation> translations){
        iFavoritesView.showFavorites(translations);
    }

    private void handleErrorLoadFavorites(Throwable throwable){
        iFavoritesView.showError(throwable.getLocalizedMessage());
    }
}
