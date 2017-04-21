package com.ivan.translateapp.data.net;

import android.content.Context;
import android.support.annotation.NonNull;

import com.ivan.translateapp.data.net.yandex.IYandexTranslateApiInterface;
import com.ivan.translateapp.data.net.yandex.YandexTranslateService;
import com.ivan.translateapp.data.net.yandex.mapper.LanguageResponseMapper;
import com.ivan.translateapp.data.net.yandex.mapper.SupportedLanguagesResponseMapper;
import com.ivan.translateapp.data.net.yandex.mapper.TranslationResponseMapper;
import com.ivan.translateapp.utils.ConnectivityUtils;

import java.io.File;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


@Module
public class NetworkModule {
    private static final int CACHE_SIZE = 10 * 1024 * 1024;
    private static final String CACHE_CONTROL = "Cache-Control";
    private static final String CACHE_PATH = "httpCache";
    private static final int CONNECT_TIMEOUT = 10; //seconds

    private final String translationApiBaseUrl;
    private final String apiKey;

    public NetworkModule(@NonNull String translationApiBaseUrl, @NonNull String apiKey){
        this.translationApiBaseUrl = translationApiBaseUrl;
        this.apiKey = apiKey;
    }

    @Provides
    @Singleton
    public ConnectivityUtils provideConnectivityUtil(Context context){
        return new ConnectivityUtils(context);
    }

    @Provides
    @Singleton
    public SupportedLanguagesResponseMapper provideSupportedLanguagesDTOMapper() {
        return new SupportedLanguagesResponseMapper();
    }

    @Provides
    @Singleton
    public TranslationResponseMapper provideTranslateResultDTOMapper() {
        return new TranslationResponseMapper();
    }

    @Provides
    @Singleton
    public LanguageResponseMapper provideLanguageDTOMapper() {
        return new LanguageResponseMapper();
    }

    @Provides
    @Singleton
    public ITranslateService provideITranslateService(IYandexTranslateApiInterface apiInterface,
                                                      LanguageResponseMapper languageResponseMapper,
                                                      SupportedLanguagesResponseMapper supportedLanguagesResponseMapper) {

        return new YandexTranslateService(apiInterface, languageResponseMapper, supportedLanguagesResponseMapper);
    }

    @Provides
    @Singleton
    public IYandexTranslateApiInterface provideIYandexTranslateApiInterface(Context context) {

        OkHttpClient httpClient = new OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(provideAddApiKeyInterceptor())
                .addInterceptor(provideOfflineCacheInterceptor())
                .addNetworkInterceptor(provideCacheInterceptor())
                .cache(getCache(context))
                .build();

        Retrofit.Builder builder = new Retrofit.Builder().
                baseUrl(translationApiBaseUrl)
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create());

        IYandexTranslateApiInterface apiInterface = builder.build()
                .create(IYandexTranslateApiInterface.class);

        return apiInterface;
    }

    private Interceptor provideAddApiKeyInterceptor() {
        return chain -> {
            Request original = chain.request();
            HttpUrl originalUrl = original.url();

            HttpUrl url = originalUrl.newBuilder()
                    .addQueryParameter("key", apiKey)
                    .build();

            Request.Builder requestBuilder = original.newBuilder()
                    .url(url);

            Request request = requestBuilder.build();
            return chain.proceed(request);
        };
    }

    //setup cache size and cache directory
    private Cache getCache(Context context) {

        File httpCacheDirectory = new File(context.getCacheDir(), CACHE_PATH);
        return new Cache(httpCacheDirectory, CACHE_SIZE);
    }

    private Interceptor provideOfflineCacheInterceptor() {
        return chain -> {
            Request request = chain.request();

            CacheControl cacheControl = new CacheControl.Builder()
                    .maxStale(7, TimeUnit.DAYS)
                    .build();

            request = request.newBuilder()
                    .cacheControl(cacheControl)
                    .build();

            return chain.proceed(request);
        };
    }

    private static Interceptor provideCacheInterceptor() {
        return chain -> {
            Response response = chain.proceed(chain.request());

            CacheControl cacheControl = new CacheControl.Builder()
                    .maxAge(2, TimeUnit.MINUTES)
                    .build();

            return response.newBuilder()
                    .header(CACHE_CONTROL, cacheControl.toString())
                    .build();
        };
    }
}
