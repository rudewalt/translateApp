package com.ivan.translateapp.dagger;

import com.ivan.translateapp.data.repository.IHistoryRepository;
import com.ivan.translateapp.data.repository.ITranslationRepository;
import com.ivan.translateapp.domain.interactor.HistoryInteractor;
import com.ivan.translateapp.domain.interactor.IHistoryInteractor;
import com.ivan.translateapp.domain.interactor.IMainInteractor;
import com.ivan.translateapp.domain.interactor.MainInteractor;
import com.ivan.translateapp.ui.history.presenter.HistoryPresenter;
import com.ivan.translateapp.ui.history.presenter.IHistoryPresenter;
import com.ivan.translateapp.ui.main.presenter.IMainPresenter;
import com.ivan.translateapp.ui.main.presenter.MainPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Ivan on 28.03.2017.
 */

@Module
public class MainModule {

    @Provides
    public IHistoryRepository provideIHistoryRepository() {
        return null;
    }

    @Provides
    public ITranslationRepository provideITranslationRepository() {
        return null;
    }

    @Provides
    public IMainInteractor provideIMainInteractor(ITranslationRepository iTranslationRepository, IHistoryRepository iHistoryRepository) {
        return new MainInteractor(iTranslationRepository, iHistoryRepository);
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
    public IHistoryPresenter provideIHistoryPresenter(IHistoryInteractor iHistoryInteractor){
        return new HistoryPresenter(iHistoryInteractor);
    }
}
