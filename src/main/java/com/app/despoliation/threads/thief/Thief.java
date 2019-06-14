package com.app.despoliation.threads.thief;

import com.app.despoliation.Flat;
import com.app.despoliation.Thing;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.concurrent.Callable;

import static com.app.despoliation.Flat.getApartment;

public class Thief implements Callable<Object> {
    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());

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
            logger.info("--> Thief with name="+Thread.currentThread().getName());
            logger.debug("    "+"Max Limit Backpack: "+ backpack.getWeightLimit());

            List<Thing> finded = findOptimalThings4Backpack( getApartment().getAll() );

            if( finded.isEmpty()) {
                logger.debug("    "+"do not findED thing, which place in backpack");
            }
            else {
                logger.debug("Thief stole next things: ");
                logger.debug("    "+finded);

                boolean addedThings = backpack.tryAddAll(finded);
                assert ( ! addedThings ) : "logic separated in  backpack and thief";

                logger.debug(addedThings);
                logger.debug(backpack.size());

                getApartment().removeAll(finded);
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
        } finally {
            logger.info("<-- Thief with name="+Thread.currentThread().getName());
            Flat.getLock4thief().unlock();
        }

        return null;
    }
}

