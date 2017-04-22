package com.ivan.translateapp.data.repository;

import com.ivan.translateapp.data.db.DbHelper;
import com.ivan.translateapp.data.db.entity.TranslationEntity;
import com.ivan.translateapp.data.db.entity.TranslationEntityMapper;
import com.ivan.translateapp.domain.Translation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;


/**
 * Реализация репозитория для сохранения переводов в локальной БД
 */
public class HistoryRepository implements IHistoryRepository {

    private final DbHelper dbOpenHelper;
    private final TranslationEntityMapper translationEntityMapper;

    public HistoryRepository(DbHelper dbOpenHelper, TranslationEntityMapper translationEntityMapper) {
        this.dbOpenHelper = dbOpenHelper;
        this.translationEntityMapper = translationEntityMapper;
    }

    @Override
    public Single<List<Translation>> getHistory() {
        return
                Single.fromCallable(() -> dbOpenHelper.getAllHistory())
                        .map(this::sortByCreateDateDesc)
                        .map(this::mapToTranslation);
    }

    @Override
    public Single<List<Translation>> getFavorites() {
        return
                Single.fromCallable(() -> dbOpenHelper.getAllFavorites())
                        .map(this::sortByAddToFavouriteDate)
                        .map(this::mapToTranslation);
    }

    @Override
    public Single<Boolean> isFavourite(String text, String fromLanguage, String toLanguage) {
        return Single.fromCallable(() -> {
            TranslationEntity entity = dbOpenHelper.getTranslation(text, fromLanguage, toLanguage);
            return entity != null && entity.isFavorite();
        });
    }

    @Override
    public Completable add(Translation translation) {
        return
                Completable.fromAction(() -> {
                    translation.setHistory(true);
                    dbOpenHelper.saveTranslation(translation);
                });
    }

    @Override
    public Completable update(Translation translation) {
        return
                Completable.fromAction(() -> dbOpenHelper.saveTranslation(translation));
    }

    @Override
    public Completable deleteHistory() {
        return
                Completable.fromAction(() -> dbOpenHelper.deleteHistory());
    }

    @Override
    public Completable deleteFavorites() {
        return
                Completable.fromAction(() -> dbOpenHelper.deleteFavorites());
    }

    @Override
    public Completable delete(Translation translation) {
        return
                Completable.fromAction(() -> dbOpenHelper.delete(translation));
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
        List<Translation> translations = new ArrayList<>();
        for (TranslationEntity entity : translationEntities) {
            translations.add(translationEntityMapper.apply(entity));
        }

        return translations;
    }
}
