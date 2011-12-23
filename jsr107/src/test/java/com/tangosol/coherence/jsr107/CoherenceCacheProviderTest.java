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

import org.junit.Test;

import javax.cache.OptionalFeature;
import javax.cache.spi.CachingProvider;

import static junit.framework.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

public class CoherenceCacheProviderTest {

    @Test
    public void testReflectionConstructor() throws Exception {
        Class<CoherenceCacheProvider> clazz = CoherenceCacheProvider.class;
        CachingProvider provider = clazz.newInstance();
        assertNotNull(provider);
    }

    @Test
    public void testIsSupported() {
        CachingProvider cacheProvider = getCachingProvider();
        for (OptionalFeature feature: OptionalFeature.values()) {
            assertFalse(cacheProvider.isSupported(feature));
        }
    }

    // Utilities --------------------------------------------------

    static CoherenceCacheProvider getCachingProvider() {
        return new CoherenceCacheProvider();
    }
}
