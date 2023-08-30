//package com.timy.javautils.sample;
//
//import com.timy.javautils.HmacUtils;
//import javax.crypto.Mac;
//import lombok.experimental.UtilityClass;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.StringUtils;
//
//@Slf4j
//@UtilityClass
//public class HmacValidator {
//
//    private static final String KEY = "cBgIhuVgrIBhzeZVLRrlT3of4PSkQf25";
//    private static final String ALGORITHM = "HmacSHA256";
//    private static final Mac MAC = HmacUtils.initMac(KEY, ALGORITHM);
//
//    public String toHmac(String data) {
//        return HmacUtils.toHmacBase64(MAC, data);
//    }
//
//    public boolean isValid(String data, String hmac) {
//        return StringUtils.equals(data, hmac);
//    }
//
//
//}
