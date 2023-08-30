package com.timy.javautils;

import java.time.LocalDate;
import java.time.chrono.IsoChronology;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.ResolverStyle;
import java.util.Locale;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@UtilityClass
@Slf4j
public class DateTimeConverter {

  final DateTimeFormatter ACCEPT_FORMAT = new DateTimeFormatterBuilder()
      .parseCaseInsensitive()
      .appendPattern("[uuuuMMdd][uuuu-M-d][uuuu/M/d][M-d-uuuu][M/d/uuuu]") //可接受解析的格式
      .toFormatter(Locale.getDefault()) //預設時區
      .withChronology(IsoChronology.INSTANCE) //ISO 日期格式，要使用 uuuu(年) 取代 yyyy(年分)
      .withResolverStyle(ResolverStyle.STRICT); //嚴謹解析，例如 0229 不是每年都有

  /**
   * @param date : yyyy-MM-dd , yyyy/MM/dd , yyyyMMdd , MM-dd-yyyy , MM/dd/yyyy , yyyMM
   * @return localDate
   */
  public LocalDate toLocalDate(String date) {
    if (StringUtils.isBlank(date)) {
      return null;
    }
    String newDate = date;
    if (date.length() == 5) { //yyyMM
      newDate = rocToAd(date) + "01";
    } else if (date.length() == 6) { //yyyyMM
      newDate += "01";
    }
    try {
      return LocalDate.parse(newDate, ACCEPT_FORMAT);
    } catch (Exception e) {
      log.error("date({}) , toLocalDate error : {}", date, e.getMessage());
      return null;
    }
  }

  public String toYYYYMMDD(LocalDate localDate) {
    return localDate == null ? "" : localDate.format(DateTimeFormatter.BASIC_ISO_DATE);
  }

  public String toYYYYMMDD(String date) {
    return toYYYYMMDD(toLocalDate(date));
  }

  public String toYYYMM(LocalDate localDate) {
    return adToRoc(localDate.format(DateTimeFormatter.ofPattern("yyyyMM")));
  }

  public String toYYYMM(String date) {
    return toYYYMM(toLocalDate(date));
  }

  public String toEvenYearMonth(LocalDate localDate) {
    final int yyyMM = Integer.parseInt(toYYYMM(localDate));
    return "" + (yyyMM % 2 == 1 ? yyyMM + 1 : yyyMM);
  }

  public String toEvenYearMonth(String date) {
    return toEvenYearMonth(toLocalDate(date));
  }

  public String rocToAd(String roc) {
    if (StringUtils.isBlank(roc)) {
      return "";
    }
    if (!(StringUtils.trim(roc).length() == 5 || StringUtils.trim(roc).length() == 7)) {
      log.warn("date({}) , rocToAd skip.", roc);
      return roc;
    }
    final String yyyy = (Integer.parseInt(StringUtils.substring(roc, 0, 3)) + 1911) + "";
    return yyyy + StringUtils.substring(roc, 3);
  }

  public String adToRoc(String ad) {
    if (StringUtils.isBlank(ad)) {
      return "";
    }
    if (!(StringUtils.trim(ad).length() == 6 || StringUtils.trim(ad).length() == 8)) {
      log.warn("date({}) , adToRoc skip.", ad);
      return ad;
    }
    final String yyy = (Integer.parseInt(StringUtils.substring(ad, 0, 4)) - 1911) + "";
    return yyy + StringUtils.substring(ad, 3);
  }


}
