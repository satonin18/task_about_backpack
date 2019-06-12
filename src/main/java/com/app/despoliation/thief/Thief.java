package com.app.despoliation.thief;

import com.app.despoliation.Flat;
import com.app.despoliation.Main;
import com.app.despoliation.Thing;

import java.util.Collections;
import java.util.List;

import static com.app.despoliation.Flat.getApartment;

public class Thief implements Runnable {
    private Backpack backpack;

    public Thief(Backpack backpack) {
        this.backpack = backpack;
    }

    public Backpack getBackpack() {
        return backpack;
    }

    public void run() {
        try{
            Flat.getLock4thief().lock();
            System.out.print("-->");System.out.println(Thread.currentThread().getName());
            System.out.println("    "+"Max Limit Backpack: "+ backpack.getWeightLimit());

            List<Thing> finded = findOptimalThings4Backpack( getApartment().getAll() );

            if( finded.isEmpty()) {
                System.out.println("    "+"do not findED thing, which place in backpack");
            }
            else {
                System.out.println();
                System.out.println("Thief stole next things: ");
                for (Thing th : finded) { System.out.println("    "+th);}

                boolean addedThings = backpack.tryAddAll(finded);
                assert ( ! addedThings ) : "logic separated in  backpack and thief";

                System.out.println(addedThings);
                System.out.println(backpack.size());

                getApartment().removeAll(finded);
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
        } finally {
            System.out.print("<--");System.out.println(Thread.currentThread().getName());
            Flat.getLock4thief().unlock();
        }

        Main.totalThingsAfterRun += backpack.size();
    }

    private List<Thing> findOptimalThings4Backpack(List<Thing> things) {
        return SelectionThing4Backpack.select(backpack.getWeightLimit(), things);
    }
}

