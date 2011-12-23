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

import javax.cache.CacheManager;
import javax.cache.implementation.AbstractCacheManagerFactory;

public final class CoherenceCacheManagerFactory extends AbstractCacheManagerFactory {
    private static final CoherenceCacheManagerFactory INSTANCE = new CoherenceCacheManagerFactory();

    private CoherenceCacheManagerFactory() {
    }

    @Override
    protected CacheManager createCacheManager(ClassLoader classLoader, String name) {
        return new CoherenceCacheManager(classLoader, name);
    }

    @Override
    protected ClassLoader getDefaultClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    /**
     * Get the singleton instance
     * @return the singleton instance
     */
    public static CoherenceCacheManagerFactory getInstance() {
        return INSTANCE;
    }
}
