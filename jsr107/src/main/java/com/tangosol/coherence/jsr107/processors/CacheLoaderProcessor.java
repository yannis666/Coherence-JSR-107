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
package com.tangosol.coherence.jsr107.processors;

import com.tangosol.util.InvocableMap;
import com.tangosol.util.processor.AbstractProcessor;

import javax.cache.Cache;
import javax.cache.CacheLoader;

/**
 * @author ycosmado
 * @since 1.0
 */
public class CacheLoaderProcessor<K, V> extends AbstractProcessor {
    private final InvocableMap.EntryProcessor next;
    private final CacheLoader<K, V> cacheLoader;

    public CacheLoaderProcessor(InvocableMap.EntryProcessor next, CacheLoader<K, V> cacheLoader) {
        this.next = next;
        this.cacheLoader = cacheLoader;
    }

    @Override
    public Object process(InvocableMap.Entry entry) {
        if (!entry.isPresent()) {
            Cache.Entry<K, V> loaded = cacheLoader.load((K) entry.getKey());
            if (loaded != null) {
                V value = loaded.getValue();
                if (value == null) {
                    throw new NullPointerException();
                }
                entry.setValue(value);
            }
        }
        return next.process(entry);
    }
}
