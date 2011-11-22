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

import com.tangosol.util.Binary;
import com.tangosol.util.BinaryEntry;
import com.tangosol.util.InvocableMap;

import java.util.Map;
import java.util.Set;

/**
 * @author ycosmado
 * @since 1.0
 */
public class Replace3Processor implements InvocableMap.EntryProcessor {
    private final Binary oldValue;
    private final Binary newValue;

    public Replace3Processor(Binary oldValue, Binary newValue) {
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    @Override
    public Object process(InvocableMap.Entry entry) {
        Boolean ret = Boolean.FALSE;
        if (entry.isPresent()) {
            BinaryEntry bEntry = (BinaryEntry) entry;
            if (bEntry.getBinaryValue().equals(oldValue)) {
                bEntry.updateBinaryValue(newValue);
                ret = Boolean.TRUE;
            }
        }
        return ret;
    }

    @Override
    public Map processAll(Set setEntries) {
        throw new UnsupportedOperationException();
    }
}
