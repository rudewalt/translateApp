package com.ivan.translateapp.domain.interactor;

import com.ivan.translateapp.data.repository.IHistoryRepository;
import com.ivan.translateapp.domain.Translation;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Ivan on 28.03.2017.
 */

public class HistoryInteractor implements IHistoryInteractor {

    private IHistoryRepository iHistoryRepository;

    public HistoryInteractor(IHistoryRepository iHistoryRepository){
        this.iHistoryRepository = iHistoryRepository;
    }

    @Override
    public Observable<List<Translation>> getHistory() {
        return iHistoryRepository.getHistory();
    }

    @Override
    public Observable<List<Translation>> getFavorites() {
        return iHistoryRepository.getFavorites();
    }


    @Override
    public void delete(Translation translation) {
        iHistoryRepository.delete(translation);
    }


    @Override
    public void saveChanges(Translation translation) {
        iHistoryRepository.update(translation);
    }


}
