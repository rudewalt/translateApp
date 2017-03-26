package com.ivan.translateapp.data;

import android.support.test.runner.AndroidJUnit4;

import com.ivan.translateapp.data.net.yandex.YandexTranslateApiInterface;
import com.ivan.translateapp.data.net.yandex.YandexTranslateApiModule;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        YandexTranslateApiInterface apiInterface =  YandexTranslateApiModule.getApiInterface();
        apiInterface.getLanguages("ru");
    }
}