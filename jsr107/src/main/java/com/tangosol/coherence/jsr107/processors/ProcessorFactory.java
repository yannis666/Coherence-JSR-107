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

import com.tangosol.net.BackingMapManagerContext;
import com.tangosol.net.NamedCache;
import com.tangosol.util.Binary;
import com.tangosol.util.InvocableMap;

import javax.cache.CacheLoader;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ycosmado
 * @since 1.0
 */
public class ProcessorFactory<K, V> {
    private final BackingMapManagerContext context;

    public ProcessorFactory(NamedCache namedCache) {
        this.context = namedCache.getCacheService().getBackingMapManager().getContext();
    }

    public InvocableMap.EntryProcessor getGetProcessor() {
        return new GetProcessor();
    }

    public InvocableMap.EntryProcessor getContainsKeyProcessor() {
        return new ContainsKeyProcessor();
    }

    public InvocableMap.EntryProcessor getPutProcessor(V value) {
        return new PutProcessor(valueToBinary(value));
    }

    public InvocableMap.EntryProcessor getGetAndPutProcessor(V value) {
        return new GetAndPutProcessor(valueToBinary(value));
    }

    public InvocableMap.EntryProcessor getPutIfAbsentProcessor(V value) {
        return new PutIfAbsentProcessor(valueToBinary(value));
    }

    public InvocableMap.EntryProcessor getRemoveProcessor() {
        return new RemoveProcessor();
    }

    public InvocableMap.EntryProcessor getRemove2Processor(V value) {
        return new Remove2Processor(valueToBinary(value));
    }

    public InvocableMap.EntryProcessor getGetAndRemoveProcessor() {
        return new GetAndRemoveProcessor();
    }

    public InvocableMap.EntryProcessor getReplace3Processor(V oldValue, V newValue) {
        return new Replace3Processor(valueToBinary(oldValue), valueToBinary(newValue));
    }

    public InvocableMap.EntryProcessor getReplace2Processor(V value) {
        return new Replace2Processor(valueToBinary(value));
    }

    public InvocableMap.EntryProcessor getGetAndReplaceProcessor(V value) {
        return new GetAndReplaceProcessor(valueToBinary(value));
    }

    public InvocableMap.EntryProcessor getPutAllProcessor(Map<Binary, Binary> map) {
        return new PutAllProcessor(map);
    }

    public InvocableMap.EntryProcessor getPutAllProcessor1(Map<K, V> map) {
        Map<Binary, Binary> binaryMap = new HashMap<Binary, Binary>(map.size());
        for (Map.Entry<K,V> entry : map.entrySet()) {
            binaryMap.put(keyToBinary(entry.getKey()), valueToBinary(entry.getValue()));
        }
        return new PutAllProcessor(binaryMap);
    }

    public InvocableMap.EntryProcessor getCacheLoaderProcessor(InvocableMap.EntryProcessor processor, CacheLoader<K, ? extends V> cacheLoader) {
        return new CacheLoaderProcessor<K, V>(processor, cacheLoader);
    }

    private Binary valueToBinary(V o) {
        return (Binary)context.getValueToInternalConverter().convert(o);
    }

    private Binary keyToBinary(K o) {
        return (Binary)context.getKeyToInternalConverter().convert(o);
    }
}
