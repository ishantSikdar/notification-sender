package com.notification.sender.util;

import java.util.concurrent.atomic.AtomicLong;

public class IDUtil {

    private static AtomicLong globalApiRequestId = new AtomicLong();

    public static long getGlobalApiRequestId() {
        return globalApiRequestId.getAndIncrement();
    }
}
