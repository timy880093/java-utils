package com.timy.javautils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class DateTimeConverterTest {

  @ParameterizedTest
  @CsvSource(value = {
      "20230105,true",
      "2023-01-05,true",
      "2023-1-05,true",
      "2023-01-5,true",
      "2023-1-5,true",
      "1-5-2023,true",
      "01-05-2023,true",
      "1-05-2023,true",
      "01-5-2023,true",
      "2023/01/05,true",
      "2023/1/05,true",
      "2023/01/5,true",
      "2023/1/5,true",
      "1/5/2023,true",
      "01/05/2023,true",
      "1/05/2023,true",
      "01/5/2023,true",
      "202301,true",
      "11201,true",
      "202315,false",
  })
  void test_toLocalDate(String date, boolean expectPass) {
//    if (expectPass) {
    final LocalDate localDate = DateTimeConverter.toLocalDate(date);
    System.out.println(localDate);
    assertThat(localDate != null).isEqualTo(expectPass);
//    } else {
//      assertThatThrownBy(() -> {
//        final LocalDate localDate = DateTimeConverter.toLocalDate(date);
//      }).isInstanceOf(DateTimeParseException.class);
//    }
  }

  @ParameterizedTest
  @CsvSource(value = {
      "20230105,20230105",
      "2023-01-05,20230105",
      "2023-1-05,20230105",
      "2023-01-5,20230105",
      "2023-1-5,20230105",
      "1-5-2023,20230105",
      "01-05-2023,20230105",
      "1-05-2023,20230105",
      "01-5-2023,20230105",
      "2023/01/05,20230105",
      "2023/1/05,20230105",
      "2023/01/5,20230105",
      "2023/1/5,20230105",
      "1/5/2023,20230105",
      "01/05/2023,20230105",
      "1/05/2023,20230105",
      "01/5/2023,20230105",
      "202301,20230101",
      "11201,20230101",
      "202315,''",
  })
  void test_toYYYYMMDD(String date, String expect) {
    final String yyyymmdd = DateTimeConverter.toYYYYMMDD(date);
    assertThat(yyyymmdd).isEqualTo(expect);
  }

  @ParameterizedTest
  @CsvSource(value = {
      "20230105,20230105",
      "2023-01-05,20230105",
      "2023-1-05,20230105",
      "2023-01-5,20230105",
      "2023-1-5,20230105",
      "1-5-2023,20230105",
      "01-05-2023,20230105",
      "1-05-2023,20230105",
      "01-5-2023,20230105",
      "2023/01/05,20230105",
      "2023/1/05,20230105",
      "2023/01/5,20230105",
      "2023/1/5,20230105",
      "1/5/2023,20230105",
      "01/05/2023,20230105",
      "1/05/2023,20230105",
      "01/5/2023,20230105",
      "202301,20230101",
      "11201,20230101",
      "202315,''",
  })
  void test_toYYYYMMDD_localDate(String date, String expect) {
    final LocalDate localDate = DateTimeConverter.toLocalDate(date);
    final String yyyymmdd = DateTimeConverter.toYYYYMMDD(localDate);
    assertThat(yyyymmdd).isEqualTo(expect);
  }

  @ParameterizedTest
  @CsvSource(value = {
      "11201,202301",
      "1120105,20230105",
      "112010,112010",
      "'',''",
  })
  void rocToAd(String roc, String expect) {
    final String ad = DateTimeConverter.rocToAd(roc);
    assertThat(ad).isEqualTo(expect);
  }

  @Test
  void adToRoc() {
  }
}