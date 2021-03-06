package com.app.despoliation.threads.thief;

import com.app.despoliation.entities.Flat;
import com.app.despoliation.entities.Thing;
import com.app.despoliation.util.LockUtil;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.concurrent.Callable;


public class Thief implements Callable<Object> {
    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    private Backpack backpack;

    public Thief(Backpack backpack) {
        this.backpack = backpack;
    }//{this.value=Objects.requireNonNull(value);}

    public Backpack getBackpack() {
        return backpack;
    }

    @Override
    public Object call() throws Exception {
        try{
            LockUtil.getLock4thief().lock();
            logger.log(Level.INFO,"--> Thief with name="+Thread.currentThread().getName());
            logger.log(Level.DEBUG,"    "+"Max Limit Backpack: "+ backpack.getWeightLimit());

            List<Thing> findedThings = findOptimalThings4Backpack( Flat.getInstance().getAll() );
            moveThingFromFlatToBackback(findedThings);

        } catch (Exception e) {
            e.printStackTrace(System.err);
            logger.log(Level.ERROR, e.getMessage());
        } finally {
            logger.log(Level.INFO,"<-- Thief with name="+Thread.currentThread().getName());
            LockUtil.getLock4thief().unlock();
        }
        return null;
    }

    private List<Thing> findOptimalThings4Backpack(List<Thing> things) {
        return SelectionThing4Backpack.select(backpack.getWeightLimit(), things);
    }

    private void moveThingFromFlatToBackback(List<Thing> things) throws Exception {
        if( things.isEmpty()) {
            logger.log(Level.DEBUG,"    "+"do not findED thing, which place in backpack");
        }
        else {
            logger.log(Level.DEBUG,"Thief stole next things: ");
            logger.log(Level.DEBUG,"    "+things);
        }
        backpack.addAll(things);
        Flat.getInstance().removeAll(things);
    }
}

