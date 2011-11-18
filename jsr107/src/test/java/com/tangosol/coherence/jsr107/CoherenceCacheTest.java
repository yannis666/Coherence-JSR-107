/*
 *
 * Copyright (c) 2011. All Rights Reserved. Oracle Corporation.
 *
 * Oracle is a registered trademark of Oracle Corporation and/or its affiliates.
 *
 * This software is the confidential and proprietary information of Oracle
 * Corporation. You shall not disclose such confidential and proprietary
 * information and shall use it only in accordance with the terms of the license
 * agreement you entered into with Oracle Corporation.
 *
 * Oracle Corporation makes no representations or warranties about the
 * suitability of the software, either express or implied, including but not
 * limited to the implied warranties of merchantability, fitness for a
 * particular purpose, or non-infringement. Oracle Corporation shall not be
 * liable for any damages suffered by licensee as a result of using, modifying
 * or distributing this software or its derivatives.
 *
 * This notice may not be removed or altered.
 */
package com.tangosol.coherence.jsr107;

import com.tangosol.net.NamedCache;
import com.tangosol.util.MapEvent;
import com.tangosol.util.MapListener;
import org.junit.Test;

import javax.cache.Cache;
import javax.cache.Caching;

import static org.junit.Assert.assertEquals;

public class CoherenceCacheTest {
    static final String CACHE_NAME = "JSR107TestCache";

    @Test
    public void testGetName() {
        String name = CACHE_NAME;
        Cache cache = getCache(name);
        assertEquals(name, cache.getName());
    }

    @Test
    public void testPutAndGet() {
        Cache<Integer, String> cache = getCache();
        int key = 1;
        String value = "value" + key;
        cache.put(key, value);
        assertEquals(value, cache.get(key));
    }

    @Test
    public void testUnwrap() {
        CoherenceCache cache = getCache().unwrap(CoherenceCache.class);
        assertEquals(CACHE_NAME, cache.getName());
    }

    @Test
    public void testListener() throws Exception {
        Cache<Integer, String> cache = getCache();
        NamedCache namedCache =
                cache.unwrap(CoherenceCache.class).getNamedCache();
        MapListener myListener = new MapListener() {
            @Override
            public void entryInserted(MapEvent evt) {
                System.out.println("entryInserted: " + evt);
            }

            @Override
            public void entryUpdated(MapEvent evt) {
                System.out.println("entryUpdated: " + evt);
            }

            @Override
            public void entryDeleted(MapEvent evt) {
                System.out.println("entryDeleted: " + evt);
            }
        };
        namedCache.addMapListener(myListener);
        namedCache.put(1, "a");
        assertEquals("a", namedCache.get(1));
    }

    public static void main(String[] args) {
        Cache cache = getCache();
        System.out.println("------------------------------" + cache.getName());
    }

    // Utilities --------------------------------------------------
    static <K, V> Cache<K, V> getCache() {
        return getCache(CACHE_NAME);
    }

    static <K, V> Cache<K, V> getCache(String cacheName) {
        Cache<K, V> cache = CoherenceCacheManagerTest.getCacheManager().getCache(cacheName);
        if (cache == null) {
            cache = CoherenceCacheManagerTest.getCacheManager().<K, V>createCacheBuilder(cacheName).build();
        }
        return cache;
    }
}
