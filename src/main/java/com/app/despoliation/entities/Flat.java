package com.app.despoliation.entities;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.invoke.MethodHandles;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Flat{
    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    private static Flat instance = new Flat();
    // TODO ???
    public static Flat getInstance(){
        return Flat.instance;
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
