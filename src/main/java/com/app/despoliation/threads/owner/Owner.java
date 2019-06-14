package com.app.despoliation.threads.owner;

import com.app.despoliation.entities.Flat;
import com.app.despoliation.entities.Thing;
import com.app.despoliation.util.LockUtil;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.concurrent.Callable;

public class Owner implements Callable<Object> {
    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    private List<Thing> things;

    public Owner(List<Thing> things) {
        this.things = things;
    }

    public List<Thing> getThings() {
        return things;
    }

    @Override
    public Object call() throws Exception {
        try{
            LockUtil.getLock4owner().lock();
            logger.log(Level.INFO,"+in Owner with name=" + Thread.currentThread().getName());

            while ( ! things.isEmpty()) {
                Flat.getInstance().add(
                        things.remove(things.size()-1)
                );
            }
        }catch (Exception e) {
            e.printStackTrace(System.err);
        } finally {
            logger.log(Level.INFO,"-out Owner with name=" + Thread.currentThread().getName());
            LockUtil.getLock4owner().unlock();
        }
        return null;
    }
}
