package com.app.despoliation.threads.thief;

import com.app.despoliation.Thing;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

public class Backpack  {
    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());

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

    public void add(Thing thing) throws Exception {
        if( thing.getWeight() <= weightLimit) {
            this.things.add(thing);
            thisWeight += thing.getWeight() ;
        }
        else {
            String errorMsg = "WeightLimit exceeded!"+
                    " thisWeight ="+thisWeight+
                    ", thing's weight ="+thing.getWeight();
            logger.log(Level.ERROR, errorMsg);
            throw new Exception(errorMsg);
        }
    }

    public void addAll(List<Thing> things) throws Exception {
        int weightNewThings = 0;
        for (Thing th : things) {
             weightNewThings += th.getWeight();
        }
        if( weightNewThings <= weightLimit) {
            this.things.addAll(things);
            thisWeight += weightNewThings;
        }
        else {
            String errorMsg = "WeightLimit exceeded!"+
                    " thisWeight ="+thisWeight+
                    ", thing's weight ="+weightNewThings;
            logger.log(Level.ERROR, errorMsg);
            throw new Exception(errorMsg);
        }
    }

    public void removeAll() {
        things.clear();
        thisWeight = 0;
    }

    public int size() {
        return things.size();
    }
}
