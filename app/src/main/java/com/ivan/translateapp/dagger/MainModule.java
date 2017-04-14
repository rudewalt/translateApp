package com.ivan.translateapp.dagger;

import com.ivan.translateapp.data.repository.IHistoryRepository;
import com.ivan.translateapp.data.repository.ITranslationRepository;
import com.ivan.translateapp.domain.interactor.FavoritesInteractor;
import com.ivan.translateapp.domain.interactor.HistoryInteractor;
import com.ivan.translateapp.domain.interactor.IFavoritesInteractor;
import com.ivan.translateapp.domain.interactor.IHistoryInteractor;
import com.ivan.translateapp.domain.interactor.IMainInteractor;
import com.ivan.translateapp.domain.interactor.MainInteractor;
import com.ivan.translateapp.ui.presenter.FavoritesPresenter;
import com.ivan.translateapp.ui.presenter.IFavoritesPresenterPresenter;
import com.ivan.translateapp.ui.presenter.HistoryPresenter;
import com.ivan.translateapp.ui.presenter.IHistoryPresenterPresenter;
import com.ivan.translateapp.ui.presenter.IMainPresenter;
import com.ivan.translateapp.ui.presenter.MainPresenter;

import java.util.Locale;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Ivan on 28.03.2017.
 */

@Module
public class MainModule {

    @Provides
    public IMainInteractor provideIMainInteractor(ITranslationRepository iTranslationRepository, IHistoryRepository iHistoryRepository, Locale locale) {
        return new MainInteractor(iTranslationRepository, iHistoryRepository, locale);
    }

    @Provides
    public IMainPresenter provideIMainPresenter(IMainInteractor iMainInteractor) {
        return new MainPresenter(iMainInteractor);
    }

    @Provides
    public IHistoryInteractor provideIHistoryInteractor(IHistoryRepository  iHistoryRepository){
        return new HistoryInteractor(iHistoryRepository);
    }

    @Provides
    public IHistoryPresenterPresenter provideIHistoryPresenter(IHistoryInteractor iHistoryInteractor){
        return new HistoryPresenter(iHistoryInteractor);
    }

    @Provides
    public IFavoritesInteractor provideIFavoritesInteractor(IHistoryRepository  iHistoryRepository){
        return new FavoritesInteractor(iHistoryRepository);
    }

    @Provides
    public IFavoritesPresenterPresenter provideIFavoritesPresenter(IFavoritesInteractor iFavoritesInteractor){
        return new FavoritesPresenter(iFavoritesInteractor);
    }
}
