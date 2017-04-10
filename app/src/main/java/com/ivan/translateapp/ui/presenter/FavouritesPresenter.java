package com.ivan.translateapp.ui.presenter;

import com.ivan.translateapp.domain.Translation;
import com.ivan.translateapp.domain.interactor.IFavouritesInteractor;
import com.ivan.translateapp.ui.view.history.IFavouritesView;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Ivan on 07.04.2017.
 */

//TODO избавиться от копипасты
public class FavouritesPresenter implements IFavouritesPresenter {

    private IFavouritesView iFavouritesView;
    private IFavouritesInteractor iFavouritesInteractor;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public FavouritesPresenter(IFavouritesInteractor iFavouritesInteractor){
        this.iFavouritesInteractor = iFavouritesInteractor;
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void bindView(IFavouritesView iFavouritesView) {
        this.iFavouritesView = iFavouritesView;
    }

    @Override
    public void unbindView() {
        this.iFavouritesInteractor = null;
        compositeDisposable.clear();
    }

    @Override
    public void loadFavourites() {
        Disposable disposable =  iFavouritesInteractor.getFavourites()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleSuccessLoadFavourites, this::handleErrorLoadFavourites);

        compositeDisposable.add(disposable);
    }

    @Override
    public void clickIsFavouriteStateCheckbox(Translation translation) {
        iFavouritesInteractor.save(translation);
    }

    private void handleSuccessLoadFavourites(List<Translation> translations){
        iFavouritesView.showFavourites(translations);
    }

    private void handleErrorLoadFavourites(Throwable throwable){
        iFavouritesView.showError(throwable.getLocalizedMessage());
    }
}
