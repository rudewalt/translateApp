package com.ivan.translateapp.data.net.yandex;


import com.ivan.translateapp.data.net.yandex.response.LanguageResponse;
import com.ivan.translateapp.data.net.yandex.response.SupportedLanguagesResponse;
import com.ivan.translateapp.data.net.yandex.response.TranslationResponse;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Ivan on 25.03.2017.
 */

public interface IYandexTranslateApiInterface {

    @GET("getLangs")
    Observable<SupportedLanguagesResponse> getLanguages(@Query("ui") String ui);

    @POST("detect")
    Observable<LanguageResponse> detectLanguage(@Body String text);

    @GET("translate")
    Observable<TranslationResponse> translate(@Query("text") String text, @Query("lang") String translateDirection, @Query("options") Integer options);

}
