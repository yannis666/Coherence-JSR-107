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
import javax.cache.Status;
import java.util.Date;

/**
 * @author ycosmado
 * @since 1.0
 */
public class CoherenceCacheStatistics implements CacheStatistics {
    private final String name;
    private final SimpleCacheStatistics statistics;

    CoherenceCacheStatistics(NamedCache namedCache) {
        this.name = namedCache.getCacheName();
        DefaultConfigurableCacheFactory.Manager backingMapManager = (DefaultConfigurableCacheFactory.Manager) namedCache.getCacheService().getBackingMapManager();
        LocalCache backingMap = (LocalCache) backingMapManager.getBackingMap(namedCache.getCacheName());
        statistics = (SimpleCacheStatistics) backingMap.getCacheStatistics();
    }

    @Override
    public void clearStatistics() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Date getStartAccumulationDate() {
        throw new UnsupportedOperationException();
    }

    @Override
    public long getCacheHits() {
        throw new UnsupportedOperationException();
    }

    @Override
    public float getCacheHitPercentage() {
        throw new UnsupportedOperationException();
    }

    @Override
    public long getCacheMisses() {
        throw new UnsupportedOperationException();
    }

    @Override
    public float getCacheMissPercentage() {
        throw new UnsupportedOperationException();
    }

    @Override
    public long getCacheGets() {
        throw new UnsupportedOperationException();
    }

    @Override
    public long getCachePuts() {
        throw new UnsupportedOperationException();
    }

    @Override
    public long getCacheRemovals() {
        throw new UnsupportedOperationException();
    }

    @Override
    public long getCacheEvictions() {
        throw new UnsupportedOperationException();
    }

    @Override
    public float getAverageGetMillis() {
        throw new UnsupportedOperationException();
    }

    @Override
    public float getAveragePutMillis() {
        throw new UnsupportedOperationException();
    }

    @Override
    public float getAverageRemoveMillis() {
        throw new UnsupportedOperationException();
    }
}
