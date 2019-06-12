package com.app.despoliation;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

//it is ONLY 1 static object
public class Flat {
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
        //System.out.println("    Flat: add"+thing);
    }

    public void addAll(List<Thing> things) {
        this.things.addAll(things);
    }

    public List<Thing> getAll() {
        System.out.println("    Flat: getAll");
        for (Thing th : things) { System.out.println("    "+th); }
        return things;
    }

    public void removeAll(List<Thing> findedThief) {
        //System.out.println("    Flat: removeAll");
        //for (Thing th : findedThief) { System.out.println("    "+th); }
        things.removeAll(findedThief);
    }

    public int size() {
        return things.size();
    }
}
