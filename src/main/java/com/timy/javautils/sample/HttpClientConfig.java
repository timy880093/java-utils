//package com.timy.javautils;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class HttpClientConfig {
//
//    @Value("${gateweb.api.url}")
//    String gatewebApiUrl;
//
//    @Bean
//    public GatewebApiService gatewebApiService() {
//        return RetrofitProvider.createClient(gatewebApiUrl, GatewebApiService.class);
//    }
//
//}
