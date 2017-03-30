package com.ivan.translateapp.domain.interactor;


import com.ivan.translateapp.data.repository.IHistoryRepository;
import com.ivan.translateapp.data.repository.ITranslationRepository;
import com.ivan.translateapp.domain.Language;
import com.ivan.translateapp.domain.Translation;

import java.util.List;


import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by Ivan on 28.03.2017.
 */

public class MainInteractor implements IMainInteractor {

    private ITranslationRepository iTranslationRepository;

    private IHistoryRepository iHistoryRepository;

    public MainInteractor(ITranslationRepository iTranslationRepository, IHistoryRepository iHistoryRepository){
        this.iTranslationRepository = iTranslationRepository;
        this.iHistoryRepository = iHistoryRepository;
    }

    @Override
    public Observable<List<Language>> getLanguages() {

        return
            iTranslationRepository.getLanguages("ru");
    }

    @Override
    public Observable<Translation> translateText(String text, String toLanguage, String fromLanguage) {
        return
            iTranslationRepository.getTranslation(text,toLanguage,fromLanguage);
    }

    @Override
    public void addToFavourites(Translation translation) {
        iHistoryRepository.addToFavourites(translation);
    }
}
