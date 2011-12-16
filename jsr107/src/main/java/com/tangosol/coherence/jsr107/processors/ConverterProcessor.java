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

import javax.cache.Cache;
import java.util.Map;
import java.util.Set;

/**
 * @author ycosmado
 * @since 1.0
 */
public class ConverterProcessor<K, V> implements InvocableMap.EntryProcessor {
    private final Cache.EntryProcessor<K, V> processor;

    public ConverterProcessor(Cache.EntryProcessor<K, V> processor) {
        this.processor = processor;
    }

    @Override
    public Object process(InvocableMap.Entry entry) {
        return processor.process(new ConverterEntry<K, V>(entry));
    }

    @Override
    public Map processAll(Set set) {
        throw new UnsupportedOperationException();
    }

    private static class ConverterEntry<K, V> implements Cache.MutableEntry<K, V> {
        private final InvocableMap.Entry entry;

        public ConverterEntry(InvocableMap.Entry entry) {
            this.entry = entry;
        }

        @Override
        public boolean exists() {
            return entry.isPresent();
        }

        @Override
        public void remove() {
            entry.remove(false);
        }

        @Override
        public void setValue(V value) {
            entry.setValue(value);
        }

        @Override
        public K getKey() {
            return (K) entry.getKey();
        }

        @Override
        public V getValue() {
            return (V) entry.getValue();
        }
    }
}
