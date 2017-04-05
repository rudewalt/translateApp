package com.ivan.translateapp.data.repository;

import com.ivan.translateapp.data.db.DbHelper;
import com.ivan.translateapp.data.db.entity.TranslationEntityMapper;
import com.ivan.translateapp.data.db.entity.TranslationEntity;
import com.ivan.translateapp.domain.Translation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by Ivan on 30.03.2017.
 */

public class HistoryRepository implements IHistoryRepository {

    private DbHelper dbOpenHelper;
    private TranslationEntityMapper translationEntityMapper;

    public HistoryRepository(DbHelper dbOpenHelper, TranslationEntityMapper translationEntityMapper) {
        this.dbOpenHelper = dbOpenHelper;
        this.translationEntityMapper = translationEntityMapper;
    }

    @Override
    public Observable<List<Translation>> getHistory() {
        return
                Observable.fromArray(dbOpenHelper.getAllHistory())
                        .map(this::sortByCreateDateDesc)
                        .map(this::mapToTranslation);
    }

    @Override
    public Observable<List<Translation>> getFavourites() {
        return
                Observable.fromArray(dbOpenHelper.getAllFavourites())
                        .map(this::mapToTranslation);
    }

    @Override
    public Observable<Boolean> isFavourite(String text, String fromLanguage, String toLanguage) {

        TranslationEntity entity = dbOpenHelper.get(text, fromLanguage, toLanguage);
        return Observable.just(entity != null && entity.isFavourite());
    }

    @Override
    public void add(Translation translation) {
        translation.setHistory(true);
        dbOpenHelper.put(translation);
    }

    @Override
    public void update(Translation translation) {
        dbOpenHelper.put(translation);
    }

    @Override
    public void addToFavourites(Translation translation) {

    }

    @Override
    public void clear() {

    }

    @Override
    public void delete(Translation translation) {

    }

    @Override
    public void deleteFromFavourites(Translation translation) {

    }

    private List<TranslationEntity> sortByCreateDateDesc(List<TranslationEntity> translationEntities) {
        Collections.sort(translationEntities,
                (entity1, entity2) -> entity2.getCreateDate().compareTo(entity1.getCreateDate()));

        return translationEntities;
    }

    private List<TranslationEntity> sortByAddToFavouriteDate(List<TranslationEntity> translationEntities){
        Collections.sort(translationEntities,
                (entity1, entity2) -> entity1.getAddToFavouriteDate().compareTo(entity2.getAddToFavouriteDate()));

        return translationEntities;
    }

    private List<Translation> mapToTranslation(List<TranslationEntity> translationEntities) throws Exception {
        List<Translation> translations = new ArrayList<Translation>();
        for (TranslationEntity entity : translationEntities) {
            translations.add(translationEntityMapper.apply(entity));
        }

        return translations;
    }
}
