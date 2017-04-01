package com.ivan.translateapp.data.net;

import android.content.Context;

import com.ivan.translateapp.data.net.yandex.IYandexTranslateApiInterface;
import com.ivan.translateapp.data.net.yandex.YandexTranslateService;
import com.ivan.translateapp.data.net.yandex.mapper.LanguageResponseMapper;
import com.ivan.translateapp.data.net.yandex.mapper.SupportedLanguagesResponseMapper;
import com.ivan.translateapp.data.net.yandex.mapper.TranslationResponseMapper;

import java.io.File;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Ivan on 26.03.2017.
 */

@Module
public class NetworkModule {
    private static final int CACHE_SIZE = 10 * 1024 * 1024;
    private static final String CACHE_PATH = "httpCache";
    private static final String BASE_URL = "https://translate.yandex.net/api/v1.5/tr.json/";

    private static final String API_KEY = "trnsl.1.1.20170317T170351Z.34081ee0ccb0bc5a.0aa288afa818fd81d6fefc8ce938b0de8995cc6f";



    @Provides
    @Singleton
    public SupportedLanguagesResponseMapper provideSupportedLanguagesDTOMapper(){
        return new SupportedLanguagesResponseMapper();
    }

    @Provides
    @Singleton
    public TranslationResponseMapper provideTranslateResultDTOMapper(){
        return new TranslationResponseMapper();
    }

    @Provides
    @Singleton
    public LanguageResponseMapper provideLanguageDTOMapper(){
        return new LanguageResponseMapper();
    }

    @Provides
    @Singleton
    public ITranslateService provideITranslateService(IYandexTranslateApiInterface apiInterface,
                                                      LanguageResponseMapper languageResponseMapper,
                                                      SupportedLanguagesResponseMapper supportedLanguagesResponseMapper,
                                                      TranslationResponseMapper translationResponseMapper){

        return new YandexTranslateService(apiInterface, languageResponseMapper, supportedLanguagesResponseMapper, translationResponseMapper);
    }

    @Provides
    @Singleton
    public IYandexTranslateApiInterface provideIYandexTranslateApiInterface(Context context) {

        //setup cache
        File httpCacheDirectory = new File(context.getCacheDir(), CACHE_PATH);

        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request original = chain.request();
                    HttpUrl originalUrl = original.url();

                    HttpUrl url = originalUrl.newBuilder()
                            .addQueryParameter("key", API_KEY)
                            .build();

                    Request.Builder requestBuilder = original.newBuilder()
                            .url(url);

                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                })
                .cache(new Cache(httpCacheDirectory, CACHE_SIZE))
                .build();

        Retrofit.Builder builder = new Retrofit.Builder().
                baseUrl(BASE_URL)
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create());

        IYandexTranslateApiInterface apiInterface = builder.build()
                .create(IYandexTranslateApiInterface.class);

        return apiInterface;
    }
}
