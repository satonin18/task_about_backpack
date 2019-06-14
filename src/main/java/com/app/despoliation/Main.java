package com.app.despoliation;

import com.app.despoliation.threads.thief.Backpack;
import com.app.despoliation.threads.thief.Thief;
import com.app.despoliation.threads.owner.Owner;
import com.app.despoliation.threads.SynchronousStartWrapper;

//import org.apache.log4j.Logger;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;

public class Main {
    //private static final Logger logger = Logger.getLogger(MethodHandles.lookup().lookupClass()); //String Full-Name
    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    static final int NUMBER_THINGS_ON_ONE_OWNER = 10;

    static final int NUMBER_OWNERS = 10;
    static final int NUMBER_THIEFS = 20;
    static final int NUMBER_THREADS = NUMBER_OWNERS + NUMBER_THIEFS;

    //can be small type data
    static final long TOTAL_THINGS_IN_APP = NUMBER_OWNERS * NUMBER_THINGS_ON_ONE_OWNER;

    private static List<Owner> listOwner = new ArrayList<>(NUMBER_OWNERS);
    private static List<Thief> listThief = new ArrayList<>(NUMBER_THIEFS);
    public static CountDownLatch countDownLatch = new CountDownLatch(NUMBER_THREADS);

    public static void main(String[] args) throws InterruptedException {
        setListOwners();
        setListThiefs();

//        // "threads start (artificial) random"
//        List<Callable<Object>> listCallable = new ArrayList<>(NUMBER_THREADS);
//        listCallable.addAll(listThief);
//        listCallable.addAll(listOwner);
//        Collections.shuffle(listCallable);

        ArrayList<SynchronousStartWrapper> listWrappers = new ArrayList<>(NUMBER_THREADS);
        for (Owner owner: listOwner) {
            listWrappers.add(new SynchronousStartWrapper(owner));
        }
        for (Thief thief: listThief) {
            listWrappers.add(new SynchronousStartWrapper(thief));
        }
        Collections.shuffle(listWrappers);

        ExecutorService service = Executors.newCachedThreadPool();
        //ExecutorService service = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        List<Future<Object>> futures = service.invokeAll(listWrappers);
        service.shutdown();

        logger.info("++++++++++++++++++++++++++++++++++++++++++++++");
        printTotalThingsAfterRun();
    }

    private static void setListOwners() {
        for (int i = 0; i< NUMBER_OWNERS; i++) {
            String threadName = "owner#"+i;
            ArrayList<Thing> listThings = new ArrayList<Thing>(NUMBER_THINGS_ON_ONE_OWNER);
            for (int iTh = 0; iTh<NUMBER_THINGS_ON_ONE_OWNER; iTh++) {
                listThings.add(
                        new Thing("thing#"+iTh+", which owns: "+threadName, (int)(Math.random()*1_000), (int)(Math.random()*1_000) )
                );
            }
            Owner owner = new Owner(listThings);
            listOwner.add(owner);
        }
    }

    private static void setListThiefs() {
        for (int i = 0; i< NUMBER_THIEFS; i++) {
            Thief thief = new Thief(new Backpack((int) (Math.random() * 10_000)));
            listThief.add(thief);
        }
    }

    private static void printTotalThingsAfterRun() {
        int totalThingsAfterRun = 0;
        int tempCount =0;

        logger.info("RESULT APP:");
        logger.info("----------------------------------------------------------");
        logger.info("Owner things :");

        for (Owner owner: listOwner) {
            tempCount += owner.getThings().size();
            logger.info(owner.getThings().size()
                    +" "+owner.getThings());
        }
        logger.info("=>Total owner things: "+tempCount);

        totalThingsAfterRun += tempCount;
        tempCount=0;

        logger.info("----------------------------------------------------------");
        logger.info("Thing in Backpack Thiefs:");
        for (Thief thief: listThief) {
            tempCount += thief.getBackpack().getThings().size();
            logger.info(thief.getBackpack().getThings().size()
                    +" "+thief.getBackpack().getThings());
        }
        logger.info("=>Total in Backpack Thiefs: "+tempCount);

        totalThingsAfterRun += tempCount;
        //tempCount=0;

        logger.info("----------------------------------------------------------");
        logger.info("=>Thing in Flat: "+ Flat.getApartment().size());

        totalThingsAfterRun += Flat.getApartment().size();

        assert (totalThingsAfterRun != TOTAL_THINGS_IN_APP) : "no eguals thing after and before";

        logger.info("----------------------------------------------------------");
        logger.info("Things were: "+TOTAL_THINGS_IN_APP);
        logger.info("New, total: "+totalThingsAfterRun);
    }

}






















