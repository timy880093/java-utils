package com.timy.javautils;

import com.timy.javautils.model.InvoiceExternalTrackEntity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import javafx.util.Pair;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ToString
public class TrackCache {
  //  private String version;

  // key: type period,value: list of trackId
  private Map<Pair<String, String>, List<String>> cache;

  private final ReentrantReadWriteLock lock;

  public TrackCache() {
    this.cache = new HashMap<>();
    this.lock = new ReentrantReadWriteLock();
  }

  public static TrackCache create(List<InvoiceExternalTrackEntity> tracks) {
    final TrackCache trackCache = new TrackCache();
    trackCache.putAll(tracks);
    return trackCache;
  }

  public TrackCache(final Map<Pair<String, String>, List<String>> cache) {
    this.cache = cache;
    this.lock = new ReentrantReadWriteLock();
  }

  private void put(InvoiceExternalTrackEntity entity) {
    final Pair<String, String> pair = new Pair<>(entity.getYearMonth(), entity.getTypeCode());
    //    log.debug("put pair : {}", pair);
    final List<String> trackIdList = this.cache.getOrDefault(pair, new ArrayList<>());
    //    log.debug("put trackIdList : {}", trackIdList);
    trackIdList.add(entity.getTrack());
    this.cache.put(pair, trackIdList);
  }

  public void updateAll(List<InvoiceExternalTrackEntity> trackEntities) {
    putAll(trackEntities, true);
  }

  public void putAll(List<InvoiceExternalTrackEntity> trackEntities) {
    putAll(trackEntities, false);
  }

  public void putAll(List<InvoiceExternalTrackEntity> trackEntities, boolean update) {
//    log.debug("trackEntities size : {}", trackEntities.size());
    if (trackEntities.isEmpty()) return;
    try {
      lock.writeLock().lock();
      if (update) this.cache = new HashMap<>();

      trackEntities.forEach(this::put);

    } catch (Exception e) {
      log.error("put()", e);
    } finally {
      lock.writeLock().unlock();
    }
  }

  public boolean isEmpty() {
    try {
      lock.readLock().lock();
      return this.cache == null || this.cache.isEmpty();
    } catch (Exception e) {
      log.error("isEmpty()", e);
      throw new RuntimeException("isEmpty()", e);
    } finally {
      lock.readLock().unlock();
    }
  }

  public Map<Pair<String, String>, List<String>> getCache() {
    try {
      lock.readLock().lock();
      return this.cache;
    } catch (Exception e) {
      log.error("get()", e);
      throw new RuntimeException("getCache()", e);
    } finally {
      lock.readLock().unlock();
    }
  }

  public List<String> getTrack(final String yearMonth, final String typeCode) {
    try {
      lock.readLock().lock();
      final List<String> tracks = this.cache.get(new Pair<>(yearMonth, typeCode));
      return tracks == null ? new ArrayList<>() : tracks;
    } catch (Exception e) {
      log.error("getTrackId()", e);
      throw new RuntimeException("getTrackId()", e);
    } finally {
      lock.readLock().unlock();
    }
  }
}
