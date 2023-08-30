//package com.timy.javautils.sample;
//
//@Slf4j
//@Configuration
//public class CacheConfig {
//
//  @Bean
//  public TrackCache trackCache(InvoiceExternalTrackRepository repository) {
//    final List<InvoiceExternalTrackEntity> tracks = repository.findAll();
//    final TrackCache trackCache = TrackCache.create(tracks);
//    log.info("Init TrackCache : {} records", tracks.size());
//    return trackCache;
//  }
//}