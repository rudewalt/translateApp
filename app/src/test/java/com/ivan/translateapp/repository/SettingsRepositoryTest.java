package com.ivan.translateapp.repository;


import android.content.Context;
import android.content.SharedPreferences;

import com.ivan.translateapp.data.repository.SettingsRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SettingsRepositoryTest {

    private static final String SHARED_PREF_FILENAME = "translateAppSharedPref";
    private static final String EMPTY_STRING = "";

    private SettingsRepository settingsRepository;

    @Mock
    Context mockContext;
    @Mock
    SharedPreferences mockSharedPreferences;

    @Before
    public void setUp(){
        when(mockContext.getSharedPreferences(SHARED_PREF_FILENAME, Context.MODE_PRIVATE)).thenReturn(mockSharedPreferences);
        settingsRepository = new SettingsRepository(mockContext);
    }

    @Test
    public void getValue_sholdReturnValue(){
        //given
        String key = "test key";
        String value = "test value";
        given(mockSharedPreferences.getString(key,EMPTY_STRING)).willReturn(value);

        //when
        String result = settingsRepository.getValue(key).blockingGet();

        //then
        assertThat(result).isEqualTo(value);
    }
}
