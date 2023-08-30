package com.timy.javautils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@Slf4j
public class StringConverter {

//  public final String handleIllegalCharacter(String s) {
//    if (StringUtils.isEmpty(s)) {
//      return s;
//    }
//    // 前後空格
//    s = s.trim();
//
//    // 去除：空格\s,回車\n,水平製表符即tab \t,換行\r
//    Pattern p = Pattern.compile("\\s|\n" + "     |\t|");
//    Matcher m = p.matcher(s);
//    s = m.replaceAll("");
//
//    // Excel文檔中非法字符
//    if (s.contains("\u202C")) {
//      s = s.replace("\u202C", "").trim();
//    }
//    if (s.contains("\u202D")) {
//      s = s.replace("\u202D", "").trim();
//    }
//    if (s.contains("\u202E")) {
//      s = s.replace("\u202E", "").trim();
//    }
//
//    s = HtmlUtils.htmlEscape(s, "UTF-8");
//    s = JavaScriptUtils.javaScriptEscape(s);
//    return s;
//  }

}
