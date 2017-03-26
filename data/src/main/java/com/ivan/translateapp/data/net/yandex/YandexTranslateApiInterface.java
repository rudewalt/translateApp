package com.ivan.translateapp.data.net.yandex;


import com.ivan.translateapp.data.dto.LanguageDTO;
import com.ivan.translateapp.data.dto.SupportedLanguagesDTO;
import com.ivan.translateapp.data.dto.TranslateResultDTO;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Ivan on 25.03.2017.
 */

public interface YandexTranslateApiInterface {

    @GET("getLangs")
    Observable<SupportedLanguagesDTO> getLanguages(@Query("ui") String ui);

    @POST("detect")
    Observable<LanguageDTO> detectLanguage(@Body String text);

    @GET("translate")
    Observable<TranslateResultDTO> translate(@Query("text") String text, @Query("lang") String translateDirection, @Query("options") Integer options);

}
