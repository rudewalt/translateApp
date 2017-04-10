package com.ivan.translateapp.domain.interactor;

import com.ivan.translateapp.data.repository.IHistoryRepository;
import com.ivan.translateapp.domain.Translation;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Ivan on 07.04.2017.
 */

public class FavouritesInteractor implements IFavouritesInteractor {

    private IHistoryRepository iHistoryRepository;

    public FavouritesInteractor(IHistoryRepository iHistoryRepository){
        this.iHistoryRepository = iHistoryRepository;
    }

    @Override
    public Observable<List<Translation>> getFavourites() {
        return iHistoryRepository.getFavourites();
    }

    @Override
    public void delete(Translation translation) {
        //no need
        // try to save without isFavourite
    }

    @Override
    public void clear() {

    }

    @Override
    public void save(Translation translation) {
        iHistoryRepository.update(translation);
    }

}
