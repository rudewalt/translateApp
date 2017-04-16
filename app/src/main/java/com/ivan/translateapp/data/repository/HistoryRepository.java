package com.ivan.translateapp.data.repository;

import com.ivan.translateapp.data.db.DbHelper;
import com.ivan.translateapp.data.db.entity.TranslationEntityMapper;
import com.ivan.translateapp.data.db.entity.TranslationEntity;
import com.ivan.translateapp.domain.Translation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.Observable;

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
    public Observable<List<Translation>> getFavorites() {
        return
                Observable.fromArray(dbOpenHelper.getAllFavorites())
                        .map(this::sortByAddToFavouriteDate)
                        .map(this::mapToTranslation);
    }

    @Override
    public Observable<Boolean> isFavourite(String text, String fromLanguage, String toLanguage) {

        TranslationEntity entity = dbOpenHelper.getTranslation(text, fromLanguage, toLanguage);
        return Observable.just(entity != null && entity.isFavorite());
    }

    @Override
    public void saveSetting(String key, String value) {
        dbOpenHelper.saveKeyValue(key, value);
    }

    @Override
    public Observable<String> getSetting(String key) {
        return Observable.just(dbOpenHelper.getKeyValue(key))
                .map(keyValueEntity ->  keyValueEntity.getValue());
    }


    @Override
    public void add(Translation translation) {
        translation.setHistory(true);
        dbOpenHelper.saveTranslation(translation);
    }

    @Override
    public void update(Translation translation) {
        dbOpenHelper.saveTranslation(translation);
    }

    @Override
    public void deleteHistory() {
        dbOpenHelper.deleteHistory();
    }

    @Override
    public void deleteFavorites() {
        dbOpenHelper.deleteFavorites();
    }

    @Override
    public void delete(Translation translation) {
        dbOpenHelper.delete(translation);
    }

    private List<TranslationEntity> sortByCreateDateDesc(List<TranslationEntity> translationEntities) {
        Collections.sort(translationEntities,
                (entity1, entity2) -> entity2.getCreateDate().compareTo(entity1.getCreateDate()));

        return translationEntities;
    }

    private List<TranslationEntity> sortByAddToFavouriteDate(List<TranslationEntity> translationEntities) {
        Collections.sort(translationEntities,
                (entity1, entity2) -> entity2.getAddToFavoriteDate().compareTo(entity1.getAddToFavoriteDate()));

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
