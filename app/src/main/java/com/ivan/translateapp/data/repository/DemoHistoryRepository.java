package com.ivan.translateapp.data.repository;

import com.ivan.translateapp.domain.Language;
import com.ivan.translateapp.domain.Translation;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Ivan on 01.04.2017.
 */

public class DemoHistoryRepository implements IHistoryRepository {

    public List<Translation> demoTransaltions;

    public DemoHistoryRepository(){
        demoTransaltions = new ArrayList<>();
        demoTransaltions.add(new Translation("привет","hi","ru","en",false));
        demoTransaltions.add(new Translation("пока","bye","ru","en",false));
        demoTransaltions.add(new Translation("мир","the world","ru","en",false));
        demoTransaltions.add(new Translation("привет","hi","ru","en",false));
        demoTransaltions.add(new Translation("пока","bye","ru","en",false));
        demoTransaltions.add(new Translation("мир","the world","ru","en",false));
        demoTransaltions.add(new Translation("привет","hi","ru","en",false));
        demoTransaltions.add(new Translation("пока","bye","ru","en",false));
        demoTransaltions.add(new Translation("мир","the world","ru","en",false));
        demoTransaltions.add(new Translation("привет","hi","ru","en",false));
        demoTransaltions.add(new Translation("пока","bye","ru","en",false));
        demoTransaltions.add(new Translation("мир","the world","ru","en",false));
        demoTransaltions.add(new Translation("привет","hi","ru","en",false));
        demoTransaltions.add(new Translation("пока","bye","ru","en",false));
        demoTransaltions.add(new Translation("мир","the world","ru","en",false));
        demoTransaltions.add(new Translation("привет","hi","ru","en",false));
        demoTransaltions.add(new Translation("пока","bye","ru","en",false));
        demoTransaltions.add(new Translation("мир","the world","ru","en",false));
        demoTransaltions.add(new Translation("привет","hi","ru","en",false));
        demoTransaltions.add(new Translation("пока","bye","ru","en",false));
        demoTransaltions.add(new Translation("мир","the world","ru","en",false));
        demoTransaltions.add(new Translation("привет","hi","ru","en",false));
        demoTransaltions.add(new Translation("пока","bye","ru","en",false));
        demoTransaltions.add(new Translation("мир","the world","ru","en",false));
        demoTransaltions.add(new Translation("привет","hi","ru","en",false));
        demoTransaltions.add(new Translation("пока","bye","ru","en",false));
        demoTransaltions.add(new Translation("мир","the world","ru","en",false));
        demoTransaltions.add(new Translation("привет","hi","ru","en",false));
        demoTransaltions.add(new Translation("пока","bye","ru","en",false));
        demoTransaltions.add(new Translation("мир","the world","ru","en",false));
        demoTransaltions.add(new Translation("привет","hi","ru","en",false));
        demoTransaltions.add(new Translation("пока","bye","ru","en",false));
        demoTransaltions.add(new Translation("мир","the world","ru","en",false));
        demoTransaltions.add(new Translation("привет","hi","ru","en",false));
        demoTransaltions.add(new Translation("пока","bye","ru","en",false));
        demoTransaltions.add(new Translation("мир","the world","ru","en",false));

    }

    @Override
    public Observable<List<Translation>> getHistory() {
        return Observable.fromArray(demoTransaltions);
    }

    @Override
    public Observable<List<Translation>> getFavourites() {
        return Observable.fromArray(demoTransaltions);
    }

    @Override
    public void add(Translation translation) {
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
