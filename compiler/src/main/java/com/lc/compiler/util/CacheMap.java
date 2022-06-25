package com.lc.compiler.util;

import cn.hutool.core.collection.ConcurrentHashSet;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Aaron Yeung
 * @date 6/25/2022 6:15 PM
 */
public class CacheMap<K, V> extends AbstractMap<K, V> {


    private class CacheEntry implements Entry<K, V> {
        long time;
        V value;
        K key;

        CacheEntry(K key, V value) {
            super();
            this.value = value;
            this.key = key;
            this.time = System.currentTimeMillis();
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public V setValue(V value) {
            return this.value = value;
        }

        @Override
        public int hashCode() {
            return super.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            return super.equals(obj);
        }
    }

    private class ClearThread extends Thread {
        ClearThread() {
            setName("clear-cache-map-thread");
        }

        @SuppressWarnings("all")
        @Override
        public void run() {
            while (true) {
                try {
                    long now = System.currentTimeMillis();
                    Iterator<Entry<K, CacheEntry>> it = map.entrySet().iterator();

                    while (it.hasNext()) {
                        Entry<K, CacheEntry> entry = it.next();
                        CacheEntry value = entry.getValue();
                        if (now - value.time >= cacheTimeout) {
                            it.remove();
                        }
                    }

                    Thread.sleep(cacheTimeout);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private final long cacheTimeout;
    private final Map<K, CacheEntry> map = new ConcurrentHashMap<>();

    public CacheMap(long timeout) {
        this.cacheTimeout = timeout;
        new ClearThread().start();
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        Set<Entry<K, V>> entrySet = new ConcurrentHashSet<>();
        Set<Entry<K, CacheEntry>> wrapEntrySet = map.entrySet();
        for (Entry<K, CacheEntry> entry : wrapEntrySet) {
            entrySet.add(entry.getValue());
        }
        return entrySet;
    }

    @Override
    public V get(Object key) {
        CacheEntry entry = map.get(key);
        return entry == null ? null : entry.value;
    }

    @Override
    public V put(K key, V value) {
        CacheEntry entry = new CacheEntry(key, value);
        map.put(key, entry);
        return value;
    }

}
