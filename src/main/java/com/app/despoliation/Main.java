package com.app.despoliation;

import com.app.despoliation.entities.Flat;
import com.app.despoliation.entities.Thing;
import com.app.despoliation.threads.thief.Backpack;
import com.app.despoliation.threads.thief.Thief;
import com.app.despoliation.threads.owner.Owner;
import com.app.despoliation.threads.SynchronousStartWrapper;

//import org.apache.log4j.Logger;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.io.*;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.*;

public class Main {
    //private static final Logger log = Logger.getLogger(MethodHandles.lookup().lookupClass()); //String Full-Name
    private static final Logger log = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    public static final String PATH_TO_PROPERTIES = "src/main/resources/config.properties";

    static int NUMBER_THINGS_ON_ONE_OWNER; // for test =Integer.MAX_VALUE

    static int NUMBER_OWNERS;
    static int NUMBER_THIEFS;

    static int RANGE_PRICE_THING;
    static int RANGE_PRICE_WEIGHT;
    static int RANGE_BACKPACK_LIMIT_WEIGHT;

    static {
        try {
            initConstFromProperties();

        } catch (IOException e) {
            log.log(Level.ERROR, e.getMessage());
            throw new RuntimeException(e);
        }
    }
    static final int NUMBER_THREADS = NUMBER_OWNERS + NUMBER_THIEFS;
    static final long TOTAL_THINGS_IN_APP = NUMBER_OWNERS * NUMBER_THINGS_ON_ONE_OWNER;

    private static void initConstFromProperties() throws IOException {
        Properties props = new Properties();
        try (InputStream is = new FileInputStream(new File(PATH_TO_PROPERTIES))) {
            props.load(is);

            NUMBER_THINGS_ON_ONE_OWNER = Integer.parseInt(props.getProperty("NUMBER_THINGS_ON_ONE_OWNER")); // for test =Integer.MAX_VALUE

            NUMBER_OWNERS = Integer.parseInt(props.getProperty("NUMBER_OWNERS"));
            NUMBER_THIEFS = Integer.parseInt(props.getProperty("NUMBER_THIEFS"));

            RANGE_PRICE_THING = Integer.parseInt(props.getProperty("RANGE_PRICE_THING"));
            RANGE_PRICE_WEIGHT = Integer.parseInt(props.getProperty("RANGE_PRICE_WEIGHT"));
            RANGE_BACKPACK_LIMIT_WEIGHT = Integer.parseInt(props.getProperty("RANGE_BACKPACK_LIMIT_WEIGHT"));
        }
    }

    static List<Thing> listThings_onStartApp = new ArrayList<>((int)TOTAL_THINGS_IN_APP);
    static List<Thing> listThings_onEndApp = new ArrayList<>((int)TOTAL_THINGS_IN_APP);

    private static List<Owner> listOwner = new ArrayList<>(NUMBER_OWNERS);
    private static List<Thief> listThief = new ArrayList<>(NUMBER_THIEFS);
    public static CountDownLatch countDownLatch = new CountDownLatch(NUMBER_THREADS);

    public static void main(String[] args) throws InterruptedException {
        initLists();

        List<Callable<Object>> listCallable = getWrapperList(); // getShuffledList()

        ExecutorService service = Executors.newCachedThreadPool(); //.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        List<Future<Object>> futures = service.invokeAll(listCallable);
        //todo take Exception Threads
        service.shutdown();

        log.log(Level.INFO,"++++++++++++++++++++++++++++++++++++++++++++++");
        printTotalThingsAfterRun(); //util.statistics
        checkEqualsAllThing();
    }

    private static List<Callable<Object>> getShuffledList() {
        List<Callable<Object>> listCallable = new ArrayList<>(NUMBER_THREADS);
        listCallable.addAll(listThief);
        listCallable.addAll(listOwner);
        Collections.shuffle(listCallable);
        return listCallable;
    }

    private static List<Callable<Object>> getWrapperList() {
        List<Callable<Object>> listWrappers = new ArrayList<>(NUMBER_THREADS);
        for (Owner owner: listOwner) {
            listWrappers.add(new SynchronousStartWrapper(owner));
        }
        for (Thief thief: listThief) {
            listWrappers.add(new SynchronousStartWrapper(thief));
        }
        return listWrappers;
    }

    private static void initLists() {
        for (int i = 0; i< NUMBER_OWNERS; i++) {
            String threadName = "owner#"+i;
            List<Thing> tempListThings = new ArrayList<>(NUMBER_THINGS_ON_ONE_OWNER);
            for (int iThing = 0; iThing<NUMBER_THINGS_ON_ONE_OWNER; iThing++) {
                Thing thing = new Thing.Builder()
                        .title("thing#"+iThing+", which owns: "+threadName)
                        .price( (int)(Math.random()*RANGE_PRICE_THING ))
                        .weight((int)(Math.random()*RANGE_PRICE_WEIGHT))
                        .build();
                tempListThings.add(
                        thing
                );
            }
            Owner owner = new Owner(tempListThings);
            listOwner.add(owner);

            listThings_onStartApp.addAll(tempListThings);
        }

        for (int i = 0; i < NUMBER_THIEFS; i++) {
            Thief thief = new Thief(
                    new Backpack( (int) (Math.random() * RANGE_BACKPACK_LIMIT_WEIGHT) )
            );
            listThief.add(thief);
        }
    }

    private static void printTotalThingsAfterRun() {
        int totalThingsAfterRun = 0;
        int tempCount =0;

        log.log(Level.INFO,"RESULT APP:");
        log.log(Level.INFO,"----------------------------------------------------------");
        log.log(Level.INFO,"Owner things :");

        for (Owner owner: listOwner) {
            tempCount += owner.getThings().size();
            log.log(Level.INFO,owner.getThings().size()
                    +" "+owner.getThings());
        }
        log.log(Level.INFO,"=>Total owner things: "+tempCount);

        totalThingsAfterRun += tempCount;
        tempCount=0;

        log.log(Level.INFO,"----------------------------------------------------------");
        log.log(Level.INFO,"Thing in Backpack Thiefs:");
        for (Thief thief: listThief) {
            listThings_onEndApp.addAll(thief.getBackpack().getThings());
            tempCount += thief.getBackpack().getThings().size();
            log.log(Level.INFO,thief.getBackpack().getThings().size()
                    +" "+thief.getBackpack().getThings());
        }
        log.log(Level.INFO,"=>Total in Backpack Thiefs: "+tempCount);

        totalThingsAfterRun += tempCount;
        //tempCount=0;

        log.log(Level.INFO,"----------------------------------------------------------");
        log.log(Level.INFO,"=>Thing in Flat: "+ Flat.getInstance().size());

        listThings_onEndApp.addAll(Flat.getInstance().getAll());
        totalThingsAfterRun += Flat.getInstance().size();

        assert (totalThingsAfterRun != TOTAL_THINGS_IN_APP) : "no eguals thing after and before";

        log.log(Level.INFO,"----------------------------------------------------------");
        log.log(Level.INFO,"Things were: "+TOTAL_THINGS_IN_APP);
        log.log(Level.INFO,"New, total: "+totalThingsAfterRun);
    }

    private static void checkEqualsAllThing() {
        List<Thing> tempListThings = new ArrayList<Thing>(listThings_onEndApp);

        if( listThings_onStartApp.size() != listThings_onEndApp.size() ) {
            log.log(Level.ERROR,"NO EQUALS SIZE");
            throw new AssertionError("NO EQUALS SIZE");
        }

        for (Thing oldThing : listThings_onStartApp) {
            boolean oldThingFinded = false;

            for (int i = 0; i<tempListThings.size(); i++) {
                Thing thingAny = tempListThings.get(i);

                if( oldThing.equals(thingAny) ) {
                    oldThingFinded = true;
                    tempListThings.remove(i);
                    break;
                }
            }
            if( ! oldThingFinded) {
                log.log(Level.ERROR,"NO EQUALS THING");
                throw new AssertionError("NO EQUALS THING");
            }
        }
        log.log(Level.INFO,"THINGS on Start and on End - equally");
    }
}






















