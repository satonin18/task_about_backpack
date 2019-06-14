package com.app.despoliation;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.invoke.MethodHandles;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

// TODO EDIT STATIC CLASS
//it is ONLY 1 static object
public class Flat {
    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    private static Flat thisFlat = new Flat();
    private static final ReadWriteLock lock = new ReentrantReadWriteLock();
    private static final Lock Lock4owner = lock.readLock();
    private static final Lock Lock4thief = lock.writeLock();

    public static Lock getLock4owner() {
        return Lock4owner;
    }

    public static Lock getLock4thief() {
        return Lock4thief;
    }

    public static Flat getApartment(){
        return thisFlat;
    }
//------------------------------------------------------------------------------
    private List<Thing> things;

    private Flat() {
        this.things = new CopyOnWriteArrayList<>();
    }

    public void add(Thing thing) {
        this.things.add(thing);
        logger.log(Level.DEBUG,"    Flat: add" + thing);
    }

    public void addAll(List<Thing> things) {
        this.things.addAll(things);
    }

    public List<Thing> getAll() {
        logger.log(Level.DEBUG,"    Flat: getAll");
        logger.log(Level.DEBUG,"    " + things);
        return things;
    }

    public void removeAll(List<Thing> findedThief) {
        logger.log(Level.DEBUG,"    Flat: removeAll");
        logger.log(Level.DEBUG,"    " + findedThief);
        things.removeAll(findedThief);
    }

    public int size() {
        return things.size();
    }
}
