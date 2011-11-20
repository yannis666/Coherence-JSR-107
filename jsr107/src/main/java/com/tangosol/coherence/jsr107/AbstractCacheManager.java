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
import java.util.HashSet;
import java.util.Set;

/**
 * @author ycosmado
 * @since 1.0
 */
public abstract class AbstractCacheManager implements CacheManager {
    private final HashSet<Class<?>> immutableClasses = new HashSet<Class<?>>();
    private final String name;
    private final ClassLoader classLoader;

    public AbstractCacheManager(String name, ClassLoader classLoader) {
        this.name = name;
        this.classLoader = classLoader;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void registerImmutableClass(Class<?> immutableClass) {
        if (immutableClass == null) {
            throw new NullPointerException();
        }
        immutableClasses.add(immutableClass);
    }

    @Override
    public void shutdown() {
        synchronized (immutableClasses) {
            immutableClasses.clear();
        }
    }

    protected Set<Class<?>> getImmutableClasses() {
        return immutableClasses;
    }

    protected ClassLoader getClassLoader() {
        return classLoader;
    }
}
