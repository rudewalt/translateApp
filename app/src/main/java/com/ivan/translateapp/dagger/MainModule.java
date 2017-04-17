package com.ivan.translateapp.dagger;

import com.ivan.translateapp.data.repository.IHistoryRepository;
import com.ivan.translateapp.data.repository.ISettingsRepository;
import com.ivan.translateapp.data.repository.ITranslationRepository;
import com.ivan.translateapp.domain.interactor.HistoryInteractor;
import com.ivan.translateapp.domain.interactor.IHistoryInteractor;
import com.ivan.translateapp.domain.interactor.IMainInteractor;
import com.ivan.translateapp.domain.interactor.MainInteractor;
import com.ivan.translateapp.ui.presenter.FavoritesPresenter;
import com.ivan.translateapp.ui.presenter.HistoryPresenter;
import com.ivan.translateapp.ui.presenter.IMainPresenter;
import com.ivan.translateapp.ui.presenter.ITranslationListViewPresenter;
import com.ivan.translateapp.ui.presenter.MainPresenter;

import java.util.Locale;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Ivan on 28.03.2017.
 */

@Module
public class MainModule {

    @Provides
    public IMainInteractor provideIMainInteractor(ITranslationRepository iTranslationRepository,
                                                  IHistoryRepository iHistoryRepository,
                                                  ISettingsRepository iSettingsRepository,
                                                  Locale locale) {
        return new MainInteractor(iTranslationRepository, iHistoryRepository, iSettingsRepository, locale);
    }

    @Provides
    public IMainPresenter provideIMainPresenter(IMainInteractor iMainInteractor) {
        return new MainPresenter(iMainInteractor);
    }

    @Provides
    public IHistoryInteractor provideIHistoryInteractor(IHistoryRepository iHistoryRepository) {
        return new HistoryInteractor(iHistoryRepository);
    }

    @Provides
    @Named("history")
    public ITranslationListViewPresenter provideIHistoryPresenter(IHistoryInteractor iHistoryInteractor) {
        return new HistoryPresenter(iHistoryInteractor);
    }

    @Provides
    @Named("favorites")
    public ITranslationListViewPresenter provideIFavoritesPresenter(IHistoryInteractor iHistoryInteractor) {
        return new FavoritesPresenter(iHistoryInteractor);
    }
}
