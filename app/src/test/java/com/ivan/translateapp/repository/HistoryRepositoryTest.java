package com.ivan.translateapp.repository;

import com.ivan.translateapp.domain.Translation;
import com.ivan.translateapp.data.db.DbHelper;
import com.ivan.translateapp.data.db.entity.TranslationEntity;
import com.ivan.translateapp.data.db.entity.TranslationEntityMapper;
import com.ivan.translateapp.data.repository.HistoryRepository;
import com.ivan.translateapp.utils.DateUtils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class HistoryRepositoryTest {

    private HistoryRepository historyRepository;

    @Mock
    private DbHelper mockDbHelper;
    @Mock
    private TranslationEntityMapper mockMapper;


    @Before
    public void setUp() {
        historyRepository = new HistoryRepository(mockDbHelper, mockMapper);
    }

    @Test
    public void getHistory_shouldReturnTranslations() throws Exception {
        //given
        List<TranslationEntity> translationEntities = getTranslations();
        given(mockDbHelper.getAllHistory()).willReturn(translationEntities);
        for (TranslationEntity t : translationEntities) {
            given(mockMapper.apply(t)).willReturn(new Translation());
        }

        //when
        List<Translation> result = historyRepository.getHistory().blockingGet();

        //then
        assertThat(result).isNotEmpty();
        assertThat(result.size()).isEqualTo(translationEntities.size());
    }

    private List<TranslationEntity> getTranslations() {
        String str = "testValue";

        Date createDateFirst = DateUtils.parse("2016-07-01 12:00:32");
        Date createDateLast = DateUtils.parse("2017-07-01 12:00:32");
        Date addToFavoritesDateFirst = DateUtils.parse("2016-07-01 12:00:32");
        Date addToFavoritesDateLast = DateUtils.parse("2017-07-01 12:00:32");
        return new ArrayList<TranslationEntity>() {{
            add(new TranslationEntity(str, str, str, str, createDateLast, addToFavoritesDateLast, true, true));
            add(new TranslationEntity(str, str, str, str, createDateFirst, addToFavoritesDateFirst, true, true));
        }};
    }

    @Test
    public void getFavorites_shouldReturnTranslations() {

    }

    @Test
    public void isFavorite_shouldReturnTrue() {
        //given
        String text = "text";
        String fromLanguage = "fromLanguage";
        String toLangage = "toLanguage";
        String translated = "текст";
        Date date = DateUtils.parse("2017-07-01 12:00:32");

        given(mockDbHelper.getTranslation(text, fromLanguage, toLangage))
                .willReturn(new TranslationEntity(text, translated, fromLanguage, toLangage, date, date, true, true));

        //when
        Boolean result = historyRepository.isFavourite(text, fromLanguage, toLangage).blockingGet();

        //then
        assertThat(result).isTrue();
    }
}
