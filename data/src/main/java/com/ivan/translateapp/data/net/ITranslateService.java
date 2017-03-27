package com.ivan.translateapp.data.net;

import com.ivan.translateapp.domain.Language;
import com.ivan.translateapp.domain.Translation;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Ivan on 26.03.2017.
 */

public interface ITranslateService {

    Observable<List<Language>> getLanguages(String ui);

    Observable<Language> detectLanguage(String text);

    Observable<Translation> translate(String text, String toLanguage, String fromLanguage);
}
