package com.app.despoliation.thief;

import com.app.despoliation.Thing;

import java.util.ArrayList;
import java.util.List;

public class Backpack  {
    private List<Thing> thingsFromBackpack = new ArrayList<>();
    private int weightLimit;
    private int thisWeight = 0;

    public Backpack(int weightLimit) {
        this.weightLimit = (weightLimit<=0) ? 0 : weightLimit;
    }

    public List<Thing> getThings() {
        return thingsFromBackpack;
    }

    public int getWeightLimit() {
        return weightLimit;
    }

    public boolean tryAdd(Thing thing) {
        if( thing.getWeight() <= weightLimit) {
            thingsFromBackpack.add(thing);
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
            thingsFromBackpack.addAll(things);
            thisWeight += weightNewThings;
            return true;
        }
        else
            return false;
    }

    public void removeAll() {
        thingsFromBackpack.clear();
        thisWeight = 0;
    }

    public int size() {
        return thingsFromBackpack.size();
    }
}
