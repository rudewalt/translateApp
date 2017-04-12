package com.ivan.translateapp.domain.interactor;

import com.ivan.translateapp.data.repository.IHistoryRepository;
import com.ivan.translateapp.domain.Translation;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Ivan on 07.04.2017.
 */

public class FavoritesInteractor implements IFavoritesInteractor {

    private IHistoryRepository iHistoryRepository;

    public FavoritesInteractor(IHistoryRepository iHistoryRepository){
        this.iHistoryRepository = iHistoryRepository;
    }

    @Override
    public Observable<List<Translation>> getFavorites() {
        return iHistoryRepository.getFavorites();
    }

    @Override
    public void delete(Translation translation) {
        //no need
        // try to save without isFavorite
    }

    @Override
    public void clear() {

    }

    @Override
    public void save(Translation translation) {
        iHistoryRepository.update(translation);
    }

}
