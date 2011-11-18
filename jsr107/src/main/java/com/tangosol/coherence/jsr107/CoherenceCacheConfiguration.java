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

import javax.cache.CacheConfiguration;
import javax.cache.CacheLoader;
import javax.cache.CacheWriter;
import javax.cache.Caching;
import javax.cache.InvalidConfigurationException;
import javax.cache.OptionalFeature;
import javax.cache.transaction.IsolationLevel;
import javax.cache.transaction.Mode;
import java.util.concurrent.atomic.AtomicBoolean;

public class CoherenceCacheConfiguration implements CacheConfiguration {

    private final AtomicBoolean readThrough;
    private final AtomicBoolean writeThrough;
    private final AtomicBoolean storeByValue;
    private final AtomicBoolean statisticsEnabled;
    private final Duration[] timeToLive;

    private CoherenceCacheConfiguration(boolean readThrough,
                                 boolean writeThrough,
                                 boolean storeByValue,
                                 boolean statisticsEnabled,
                                 Duration[] timeToLive) {
        this.readThrough = new AtomicBoolean(readThrough);
        this.writeThrough = new AtomicBoolean(writeThrough);
        this.storeByValue = new AtomicBoolean(storeByValue);
        this.statisticsEnabled = new AtomicBoolean(statisticsEnabled);
        this.timeToLive = timeToLive;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isReadThrough() {
        return readThrough.get();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isWriteThrough() {
        return writeThrough.get();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isStoreByValue() {
        return storeByValue.get();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isStatisticsEnabled() {
        return statisticsEnabled.get();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setStatisticsEnabled(boolean enableStatistics) {
        this.statisticsEnabled.set(enableStatistics);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isTransactionEnabled() {
        return false;
    }

    @Override
    public IsolationLevel getTransactionIsolationLevel() {
        return IsolationLevel.NONE;
    }

    @Override
    public Mode getTransactionMode() {
        return Mode.NONE;
    }

    @Override
    public CacheLoader getCacheLoader() {
        throw new UnsupportedOperationException();
    }

    @Override
    public CacheWriter getCacheWriter() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Duration getExpiry(ExpiryType type) {
        return timeToLive[type.ordinal()];
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CacheConfiguration)) return false;

        CacheConfiguration that = (CacheConfiguration) o;

        if (isReadThrough() != that.isReadThrough()) return false;
        if (isStatisticsEnabled() != that.isStatisticsEnabled()) return false;
        if (isStoreByValue() != that.isStoreByValue()) return false;
        if (isTransactionEnabled() != that.isTransactionEnabled()) return false;
        if (isWriteThrough() != that.isWriteThrough()) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = readThrough.hashCode();
        boolean b;

        b = isWriteThrough();
        result = 31 * result + (b ? 1 : 0);
        b = isStoreByValue();
        result = 31 * result + (b ? 1 : 0);
        b = isStatisticsEnabled();
        result = 31 * result + (b ? 1 : 0);
        b = isTransactionEnabled();
        result = 31 * result + (b ? 1 : 0);
        return result;
    }

    /**
     * Builds the config
     * @author Yannis Cosmadopoulos
     */
    public static class Builder {
        private static final boolean DEFAULT_READ_THROUGH = false;
        private static final boolean DEFAULT_WRITE_THROUGH = false;
        private static final boolean DEFAULT_STORE_BY_VALUE = true;
        private static final boolean DEFAULT_STATISTICS_ENABLED = false;
        private static final Duration DEFAULT_TIME_TO_LIVE = Duration.ETERNAL;

        private boolean readThrough = DEFAULT_READ_THROUGH;
        private boolean writeThrough = DEFAULT_WRITE_THROUGH;
        private boolean storeByValue = DEFAULT_STORE_BY_VALUE;
        private boolean statisticsEnabled = DEFAULT_STATISTICS_ENABLED;
        private final Duration[] timeToLive;

        public Builder() {
            timeToLive = new Duration[ExpiryType.values().length];
            for (int i = 0; i < timeToLive.length; i++) {
                timeToLive[i] = DEFAULT_TIME_TO_LIVE;
            }
        }

        /**
         * Set whether read through is active
         * @param readThrough whether read through is active
         * @return this Builder instance
         */
        public Builder setReadThrough(boolean readThrough) {
            this.readThrough = readThrough;
            return this;
        }

        /**
         * Set whether write through is active
         *
         * @param writeThrough whether write through is active
         * @return this Builder instance
         */
        public Builder setWriteThrough(boolean writeThrough) {
            this.writeThrough = writeThrough;
            return this;
        }

        /**
         * Set whether store by value is active
         *
         * @param storeByValue whether store by value is active
         * @return this Builder instance
         */
        public Builder setStoreByValue(boolean storeByValue) {
            if (!storeByValue && !Caching.isSupported(OptionalFeature.STORE_BY_REFERENCE)) {
                throw new InvalidConfigurationException("storeByValue");
            }
            this.storeByValue = storeByValue;
            return this;
        }

        /**
         * Set whether statistics are enabled
         *
         * @param statisticsEnabled statistics are enabled
         * @return this Builder instance
         */
        public Builder setStatisticsEnabled(boolean statisticsEnabled) {
            this.statisticsEnabled = statisticsEnabled;
            return this;
        }

        /**
         * Create a new RICacheConfiguration instance.
         *
         * @return a new RICacheConfiguration instance
         */
        public CoherenceCacheConfiguration build() {
            return new CoherenceCacheConfiguration(readThrough, writeThrough, storeByValue, statisticsEnabled, timeToLive);
        }

        public Builder setExpiry(ExpiryType type, Duration duration) {
            if (type == null) {
                throw new NullPointerException();
            }
            if (duration == null) {
                throw new NullPointerException();
            }
            this.timeToLive[type.ordinal()] =
                    duration.getDurationAmount() == 0 ? Duration.ETERNAL : duration;
            return this;
        }
    }
}
