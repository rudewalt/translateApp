package com.ivan.translateapp.repository;


import android.content.Context;
import android.content.SharedPreferences;

import com.ivan.translateapp.data.repository.SettingsRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
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
    @Mock
    SharedPreferences.Editor mockEditor;

    @Before
    public void setUp(){
        when(mockContext.getSharedPreferences(SHARED_PREF_FILENAME, Context.MODE_PRIVATE)).thenReturn(mockSharedPreferences);
        when(mockSharedPreferences.edit()).thenReturn(mockEditor);

        settingsRepository = new SettingsRepository(mockContext);
    }

    @Test
    public void getValue_shouldReturnValue(){
        //given
        String key = "test key";
        String value = "test value";
        given(mockSharedPreferences.getString(key,EMPTY_STRING)).willReturn(value);

        //when
        String result = settingsRepository.getValue(key).blockingGet();

        //then
        assertThat(result).isEqualTo(value);
    }

    @Test
    public void setValue_shouldSaveValueToPreferences(){
        //given
        String key = "test key";
        String value = "test value";

        //when
        settingsRepository.putValue(key,value).blockingGet();

        //then
        verify(mockEditor).putString(key, value);
        verify(mockEditor).apply();
    }

    @Test
    public void getStringSet_shouldReturnStringSet(){
        //given
        String key = "test key";
        Set<String> stringSet = new HashSet<>();
        given(mockSharedPreferences.getStringSet(key, new HashSet<>())).willReturn(stringSet);

        //when
        Set<String>  result = settingsRepository.getStringSet(key).blockingGet();

        //then
        assertThat(result).isSameAs(stringSet);
    }

    @Test
    public void setStringSet_shouldSaveSetToPreferences(){
        //given
        String key = "test key";
        Set<String> stringSet = new HashSet<>();

        //when
        settingsRepository.putStringSet(key,stringSet).blockingGet();

        //then
        verify(mockEditor).putStringSet(key,stringSet);
        verify(mockEditor).apply();
    }
}
