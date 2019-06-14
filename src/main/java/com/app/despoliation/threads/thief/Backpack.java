package com.app.despoliation.threads.thief;

import com.app.despoliation.Thing;
import org.apache.log4j.Logger;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

public class Backpack  {
    private static final Logger logger = Logger.getLogger(MethodHandles.lookup().lookupClass()); //String Full-Name

    private List<Thing> things = new ArrayList<>();
    private int weightLimit;
    private int thisWeight = 0;

    public Backpack(int weightLimit) {
        this.weightLimit = (weightLimit<=0) ? 0 : weightLimit;
    }

    public List<Thing> getThings() {
        return things;
    }

    public int getWeightLimit() {
        return weightLimit;
    }

    public boolean tryAdd(Thing thing) {
        if( thing.getWeight() <= weightLimit) {
            this.things.add(thing);
            thisWeight += thing.getWeight() ;
            return true;
        }
        else return false;
    }

    public boolean tryAddAll(List<Thing> things) {
        int weightNewThings = 0;
        for (Thing th : things) {
             weightNewThings += th.getWeight();
        }
        if( weightNewThings <= weightLimit) {
            this.things.addAll(things);
            thisWeight += weightNewThings;
            return true;
        }
        else
            return false;
    }

    public void removeAll() {
        things.clear();
        thisWeight = 0;
    }

    public int size() {
        return things.size();
    }
}
