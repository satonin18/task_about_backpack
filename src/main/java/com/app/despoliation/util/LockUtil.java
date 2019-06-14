package com.app.despoliation.util;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class LockUtil {
    private static final ReadWriteLock lock = new ReentrantReadWriteLock();
    private static final Lock Lock4owner = lock.readLock();
    private static final Lock Lock4thief = lock.writeLock();

    public static Lock getLock4owner() {
        return Lock4owner;
    }

    public static Lock getLock4thief() {
        return Lock4thief;
    }
}
