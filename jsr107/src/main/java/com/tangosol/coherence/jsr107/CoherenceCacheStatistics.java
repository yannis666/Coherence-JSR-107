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

import com.tangosol.net.DefaultConfigurableCacheFactory;
import com.tangosol.net.NamedCache;
import com.tangosol.net.cache.LocalCache;
import com.tangosol.net.cache.SimpleCacheStatistics;

import javax.cache.CacheStatistics;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author ycosmado
 * @since 1.0
 */
public class CoherenceCacheStatistics implements CacheStatistics {
    private final SimpleCacheStatistics statistics;
    private final AtomicLong startTime = new AtomicLong();
    private final AtomicLong  removeCount = new AtomicLong();
    private final AtomicLong  removeMillis = new AtomicLong();

    CoherenceCacheStatistics(NamedCache namedCache) {
        DefaultConfigurableCacheFactory.Manager backingMapManager = (DefaultConfigurableCacheFactory.Manager) namedCache.getCacheService().getBackingMapManager();
        LocalCache backingMap = (LocalCache) backingMapManager.getBackingMap(namedCache.getCacheName());
        statistics = (SimpleCacheStatistics) backingMap.getCacheStatistics();
        statistics.resetHitStatistics();
    }

    @Override
    public void clearStatistics() {
        statistics.resetHitStatistics();
        startTime.set(SimpleCacheStatistics.getSafeTimeMillis());
        removeCount.set(0);
        removeMillis.set(0);
    }

    @Override
    public Date getStartAccumulationDate() {
        return new Date(startTime.get());
    }

    @Override
    public long getCacheHits() {
        return statistics.getCacheHits();
    }

    @Override
    public float getCacheHitPercentage() {
        float misses = statistics.getCacheHits();
        float gets = statistics.getTotalGets();
        return gets == 0 ? 0 : (misses/gets);
    }

    @Override
    public long getCacheMisses() {
        return statistics.getCacheMisses();
    }

    @Override
    public float getCacheMissPercentage() {
        float misses = statistics.getCacheMisses();
        float gets = statistics.getTotalGets();
        return gets == 0 ? 0 : (misses/gets);
    }

    @Override
    public long getCacheGets() {
        return statistics.getTotalGets();
    }

    @Override
    public long getCachePuts() {
        return statistics.getTotalPuts();
    }

    @Override
    public long getCacheRemovals() {
        return removeCount.get();
    }

    @Override
    public long getCacheEvictions() {
        return statistics.getCachePrunes();
    }

    @Override
    public float getAverageGetMillis() {
        return (float) statistics.getAverageGetMillis();
    }

    @Override
    public float getAveragePutMillis() {
        return (float) statistics.getAveragePutMillis();
    }

    @Override
    public float getAverageRemoveMillis() {
        long count = removeCount.get();
        if (count == 0) {
            return 0;
        } else {
            float total = removeMillis.get();
            return total/count;
        }
    }
    
    public void registerHits(int count, long startMillis) {
        statistics.registerHits(count, startMillis);
    }

    public void registerMisses(int count, long startMillis) {
        statistics.registerMisses(count, startMillis);
    }

    public long currentTimeMillis() {
        return SimpleCacheStatistics.getSafeTimeMillis();
    }
}
