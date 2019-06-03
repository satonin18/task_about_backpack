package com.app.despoliation;

import java.util.Collection;
import java.util.List;

public class Backpack  {
    private List<Thing> things;
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
            things.add(thing);
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
            things.addAll(things);
            thisWeight += weightNewThings;
            return true;
        }
        else return false;
    }

    public void removeAll() {
        things = null;
        thisWeight = 0;
    }
}
