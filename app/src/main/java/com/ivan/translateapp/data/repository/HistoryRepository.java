package com.ivan.translateapp.data.repository;

import com.ivan.translateapp.data.db.DbHelper;
import com.ivan.translateapp.data.db.tables.TranslationTable;
import com.ivan.translateapp.data.db.entity.TranslationEntity;
import com.ivan.translateapp.domain.Translation;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Ivan on 30.03.2017.
 */

public class HistoryRepository implements IHistoryRepository {

    private DbHelper dbOpenHelper;

    public HistoryRepository(DbHelper dbOpenHelper){
        this.dbOpenHelper = dbOpenHelper;
    }

    @Override
    public Observable<List<Translation>> getHistory() {
        return null;
    }

    @Override
    public Observable<List<Translation>> getFavourites() {
        return null;
    }

    @Override
    public void add(Translation translation) {
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
}
