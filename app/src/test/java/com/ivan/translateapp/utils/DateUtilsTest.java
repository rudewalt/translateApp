package com.ivan.translateapp.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class DateUtilsTest {
    @Test
    public void parse_shouldReturnNullIfNullArgument(){
        //given
        String argument = "";

        //when
        Date result = DateUtils.parse(argument);

        //then
        assertThat(result).isNull();
    }

    @Test
    public void parse_shouldReturnDate(){
        //given
        String argument = "2017-04-23 11:00:23";

        //when
        Date result = DateUtils.parse(argument);

        //then
        assertThat(result).isNotNull();
        assertThat(result.compareTo(new Date(117,3,23,11,0,23))).isEqualTo(0);
    }

    @Test
    public void getCurrentDateTime_shouldReturnNotNullDate(){
        //when
        String currentDateTime = DateUtils.getCurrentDateTime();

        //then
        assertThat(currentDateTime).isNotEmpty();
    }

}