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

import javax.cache.CacheLoader;
import javax.cache.CacheWriter;
import javax.cache.experimental.CacheBuilder;
import javax.cache.implementation.AbstractCacheConfiguration;
import javax.cache.transaction.IsolationLevel;
import javax.cache.transaction.Mode;

public class CoherenceCacheConfiguration<K, V> extends AbstractCacheConfiguration {

    private CoherenceCacheConfiguration(boolean readThrough, boolean writeThrough,
                                        boolean storeByValue, boolean statisticsEnabled,
                                        IsolationLevel isolationLevel, Mode transactionMode,
                                        Duration[] timeToLive) {
        super(readThrough, writeThrough, storeByValue, statisticsEnabled, isolationLevel, transactionMode, timeToLive);
    }

    @Override
    public CacheLoader<K, ? extends V> getCacheLoader() {
        throw new UnsupportedOperationException();
    }

    @Override
    public CacheWriter<? super K, ? super V> getCacheWriter() {
        throw new UnsupportedOperationException();
    }

    @Override
    public CacheBuilder createBuilderEXPERIMENTAL() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    /**
     * Builds the config
     * @author Yannis Cosmadopoulos
     */
    public static class Builder extends AbstractCacheConfiguration.Builder {

        /**
         * Create a new CoherenceCacheConfiguration instance.
         *
         * @return a new CoherenceCacheConfiguration instance
         */
        public CoherenceCacheConfiguration build() {
            return new CoherenceCacheConfiguration(readThrough, writeThrough,
                storeByValue, statisticsEnabled,
                isolationLevel, transactionMode,
                timeToLive);
        }
    }
}
