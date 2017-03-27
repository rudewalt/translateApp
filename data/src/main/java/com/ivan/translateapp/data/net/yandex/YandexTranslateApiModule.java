package com.ivan.translateapp.data.net.yandex;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Ivan on 26.03.2017.
 */

public class YandexTranslateApiModule {
    private static final String BASE_URL = "https://translate.yandex.net/api/v1.5/tr.json/";

    private static final String API_KEY = "trnsl.1.1.20170317T170351Z.34081ee0ccb0bc5a.0aa288afa818fd81d6fefc8ce938b0de8995cc6f";

    public static IYandexTranslateApiInterface getApiInterface() {

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
                }).build();


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
