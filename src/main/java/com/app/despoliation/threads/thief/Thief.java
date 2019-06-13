package com.app.despoliation.threads.thief;

import com.app.despoliation.Flat;
import com.app.despoliation.Thing;

import java.util.List;
import java.util.concurrent.Callable;

import static com.app.despoliation.Flat.getApartment;

public class Thief implements Callable<Object> {
    private Backpack backpack;

    public Thief(Backpack backpack) {
        this.backpack = backpack;
    }

    public Backpack getBackpack() {
        return backpack;
    }

    private List<Thing> findOptimalThings4Backpack(List<Thing> things) {
        return SelectionThing4Backpack.select(backpack.getWeightLimit(), things);
    }

    @Override
    public Object call() throws Exception {
        try{
            Flat.getLock4thief().lock();
            System.out.println("--> Thief with name="+Thread.currentThread().getName());
            //System.out.println("    "+"Max Limit Backpack: "+ backpack.getWeightLimit());

            List<Thing> finded = findOptimalThings4Backpack( getApartment().getAll() );

            if( finded.isEmpty()) {
                //System.out.println("    "+"do not findED thing, which place in backpack");
            }
            else {
                //System.out.println();
                //System.out.println("Thief stole next things: ");
                //for (Thing th : finded) { System.out.println("    "+th);}

                boolean addedThings = backpack.tryAddAll(finded);
                assert ( ! addedThings ) : "logic separated in  backpack and thief";

                //System.out.println(addedThings);
                //System.out.println(backpack.size());

                getApartment().removeAll(finded);
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
        } finally {
            System.out.println("<-- Thief with name="+Thread.currentThread().getName());
            Flat.getLock4thief().unlock();
        }

        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}

